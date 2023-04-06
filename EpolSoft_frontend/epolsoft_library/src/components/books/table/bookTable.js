import { Table, Space, Form, Input, Button, Popconfirm, AutoComplete } from 'antd';
import { SaveOutlined, StopOutlined, DeleteOutlined, EditOutlined, DownloadOutlined } from '@ant-design/icons'
import { useState, useEffect } from 'react';
import { deleteBook, putBook, sortBook, fetchBooks } from "../../../redux/reducers/bookSlice";
import { useDispatch, useSelector } from 'react-redux';
import { extractData, deleteData } from '../../../redux/reducers/bookSlice';
import { convertBooksToTable, downloadFile } from './features/tablemethods';
import { useLoaderData } from 'react-router-dom';

const INITIAL_BOOKS_TOTAL = 1;
const INITIAL_PAGE_NUM = 1;
const INITIAL_PAGE_SIZE = 5;
const INITIAL_ORDER_FIELD = "id";
const ASC_ORDER = "ASC";
const DESC_ORDER = "DESC";

function BookTable({ loading, deleteLoading }) {
  const as = useLoaderData();

  const dispatch = useDispatch();
  const { books, totalBooks } = useSelector(state => state.books);
  const topics = useSelector(state => state.topics.topics)

  const [pageNum, setPageNum] = useState(INITIAL_PAGE_NUM);
  const [pageSize, setPageSize] = useState(INITIAL_PAGE_SIZE);
  const [sortField, setSortField] = useState(INITIAL_ORDER_FIELD);
  const [sortOrder, setSortOrder] = useState(ASC_ORDER);

  const [editRowKey, setEditRowKey] = useState("");
  const [form] = Form.useForm();

  useEffect(() => {
    //dispatch(fetchBooks({ page: 1, pageSize }));
    extractData(dispatch, { pageNum: pageNum, pageSize: pageSize, sortField: sortField, sortOrder: sortOrder });
    console.log('effect');
    console.log(totalBooks);


  }, [dispatch])

  const handleDelete = (value) => {
    deleteData(dispatch, value.id)
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
    console.log('edit');
    console.log(record);
    setEditRowKey(record.key);


    form.setFieldValue({
      ...record,
      name: record.name
    });
  };


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
      onHeaderCell: (column) => {
        return {
          onClick: () => {
            console.log(column);
            setSortField(column.dataIndex);
            setSortOrder(sortOrder === ASC_ORDER ? DESC_ORDER : ASC_ORDER);
            extractData(dispatch, { pageNum: pageNum, pageSize: pageSize, sortField: column.dataIndex, sortOrder: sortOrder === ASC_ORDER ? DESC_ORDER : ASC_ORDER });
          }
        };
      },
      /* sorter: async () => {
        //CHANGE!!!
        console.log('Clicked sort button');
        toggle();
      } */
    },
    {
      title: "Author",
      dataIndex: "author",
      align: "center",
      editTable: true,
    },
    {
      title: "Topic",
      dataIndex: "topic",
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
      title: "Action",
      dataIndex: "action",
      align: "center",
      render: (_, record) => {
        const editable = isEditing(record);
        return (
          convertBooksToTable.length >= 1 ? (
            <Space>
              {editable ? (
                <span>
                  <Space size="middle">
                    <Button type="primary" onClick={(e) => save(record)} ><SaveOutlined /></Button>
                    <Popconfirm title="Are you sure to cancel?" onConfirm={cancel}>
                      <Button><StopOutlined /></Button>
                    </Popconfirm>
                  </Space>
                </span>
              ) : (
                <Space>

                  <Button type='primary' onClick={() => { downloadFile("https://community.developers.refinitiv.com/storage/attachments/6024-data-file-download-guide-v26.pdf") }}><DownloadOutlined /></Button>
                  <Button type="primary" onClick={() => edit(record)} ><EditOutlined /></Button>
                  <Popconfirm
                    title="Are you sure?"
                    onConfirm={() => handleDelete(record)}
                  >
                    <Button danger type="primary" loading={deleteLoading}><DeleteOutlined /></Button>
                  </Popconfirm>


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
              style={{ margin: 0 }}
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
              <Input />
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
          dataSource={convertBooksToTable(books)}
          bordered
          loading={loading}
          pagination={{
            pageSize: pageSize,
            total: totalBooks || INITIAL_BOOKS_TOTAL,
            onChange: (page, pageSize) => {
              setPageNum(page);
              setPageSize(pageSize);
              extractData(dispatch, { pageNum: page, pageSize: pageSize, sortField: sortField, sortOrder: sortOrder });
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

export { BookTable };
