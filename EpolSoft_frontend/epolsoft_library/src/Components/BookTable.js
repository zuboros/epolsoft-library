import { Table, Space, Form, Input, Button, Popconfirm, AutoComplete } from 'antd';
import { useState, useEffect } from 'react';
import { deleteBook, putBook, sortBook, fetchBooks } from "../store/bookSlice";
import { useDispatch, useSelector } from 'react-redux';

function BookTable() {
  const dispatch = useDispatch();
  const books = useSelector(state => state.books.books);
  const topics = useSelector(state => state.topics.topics)

  const [pageSize, setPageSize] = useState(10);
  const [loading, setLoading] = useState(false);
  const [loadingDelete, setLoadingDelete] = useState(false);
  const [editRowKey, setEditRowKey] = useState("");
  const [form] = Form.useForm();

  const modifiedData = (books) => books?.map((book) => ({
    key: book.id,
    ...book,
  }));

  useEffect(() => {
    setLoading(true);
    dispatch(fetchBooks({ page: 1, pageSize }));
    setLoading(false);
  }, [])

  const handleDelete = (value) => {
    setLoadingDelete(true);
    dispatch(deleteBook({ id: value.id }));
    setLoadingDelete(false);
  }

  const isEditing = (record) => {
    return record.key === editRowKey
  }

  const cancel = () => {
    setEditRowKey("");
  };

  const save = async (record) => {
    try {
      const row = await form.validateFields();
      //console.log({ id: record.id, ...row });

      //dispatch(editBook({ id: record.id, ...row }))

      const newBook = {
        ...(books.find((book) => book.id === record.id)),
        ...row,
        authorId: (topics.find((topic) => topic.name === record.topicName))
      }

      console.log(newBook);


      dispatch(putBook(newBook))

      setEditRowKey("");

    } catch (error) {
      throw new Error(error.message);
    }
  };
  const edit = (record) => {
    form.setFieldValue({
      ...record,
    });
    setEditRowKey(record.key);
  };

  const downloadFile = (url) => {
    const fileName = url.split("/").pop();
    const aTag = document.createElement("a");
    aTag.href = url;
    aTag.setAttribute("download", fileName);
    document.body.appendChild(aTag);
    aTag.click();
    aTag.remove();
  }

  const columns = [
    {
      title: "ID",
      dataIndex: "id",
    },
    {
      title: "Name",
      dataIndex: "name",
      align: "center",
      editTable: true,
      sorter: async () => {
        //CHANGE!!!
        console.log('Clicked sort button');
        await setTimeout(() => {
          dispatch(sortBook({}))

        }, 1000)
      }
    },
    {
      title: "Author",
      dataIndex: "authorName",
      align: "center",
      editTable: true,
    },
    {
      title: "Topic",
      dataIndex: "topicName",
      align: "center",
      editTable: true,
    },
    {
      title: "ShortDescription",
      dataIndex: "shortDescription",
      align: "center",
      editTable: true,
    },
    {
      title: "File",
      dataIndex: "fileName",
      align: "center",
      render: (_, record) => {

        return (
          <Button type='primary' onClick={() => { downloadFile("https://community.developers.refinitiv.com/storage/attachments/6024-data-file-download-guide-v26.pdf") }}>Download</Button>
        )
      }
    },
    {
      title: "Action",
      dataIndex: "action",
      align: "center",
      render: (_, record) => {
        const editable = isEditing(record);
        return (
          modifiedData.length >= 1 ? (
            <Space>
              {editable ? (
                <span>
                  <Space size="middle">
                    <Button type="primary" onClick={(e) => save(record)} >Save</Button>
                    <Popconfirm title="Are you sure to cancel?" onConfirm={cancel}>
                      <Button>Cancel</Button>
                    </Popconfirm>
                  </Space>
                </span>
              ) : (
                <Space>

                  <Popconfirm
                    title="Are you sure?"
                    onConfirm={() => handleDelete(record)}
                  >
                    <Button danger type="primary" loading={loadingDelete}>Delete</Button>
                  </Popconfirm>

                  <Button type="primary" onClick={() => edit(record)} >Edit</Button>

                </Space>
              )}
            </Space>
          ) : null)
      }
    },
  ];

  const mergedColumns = columns.map((col) => {
    if (!col.editTable) {
      return col;
    }

    return {
      ...col,
      onCell: (record) => ({
        record,
        dataIndex: col.dataIndex,
        title: col.title,
        editing: isEditing(record),
      })
    }
  })

  const EditableCell = ({
    editing,
    dataIndex,
    title,
    record,
    children,
    ...restProps
  }) => {


    return (
      <td {...restProps}>
        {editing ? (
          dataIndex === "topicName" ?
            <Form.Item
              name="topic"
              rules={[
                {
                  required: true,
                },
                {
                  validator(_, value) {
                    return new Promise((resolve, reject) => {
                      topics.find((topic) => topic.name === value) ?
                        resolve("Success!") :
                        reject("The topic is not correct");
                    })
                  }
                }
              ]}
            >
              <AutoComplete
                options={topics.map((topic) => ({ value: topic.name }))}
                placeholder="Please enter the topic"
                filterOption={(inputValue, option) =>
                  option.value.toUpperCase().indexOf(inputValue.toUpperCase()) !== -1
                }
              >
              </AutoComplete>
            </Form.Item>
            :
            <Form.Item
              name={dataIndex}
              style={{ margin: 0 }}
              rules={[
                {
                  required: true,
                  message: `Please input some value in ${title} field`,
                },
              ]}
            >
              <Input value={dataIndex} />
            </Form.Item>
        ) : (
          children
        )}
      </td>
    );
  };

  return (
    <div>
      <Form form={form} component={false}>
        <Table
          columns={mergedColumns}
          components={{
            body: {
              cell: EditableCell,
            }
          }}
          dataSource={modifiedData(books)}
          bordered
          loading={loading}
          pagination={{
            pageSize: pageSize,
            total: 100,
            onChange: (page, pageSize) => {

              setPageSize(pageSize);
              dispatch(fetchBooks({ page: page, pageSize: pageSize }));
            }
          }}
          expandable={{
            expandedRowRender: (record) => (
              <p><b>Description: </b>{record.description}</p>
            ),
            rowExpandable: (record) => record.info !== "Not Expandable",
          }}
        />
      </Form>
    </div>
  )
}

export default BookTable;
