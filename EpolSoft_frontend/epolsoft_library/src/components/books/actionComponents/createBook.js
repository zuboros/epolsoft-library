import { Button, Modal, Form, Input, AutoComplete, Upload } from 'antd';
import { FileAddOutlined, SaveOutlined } from '@ant-design/icons'
import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchBooksByUserId, postBook, postFile } from '../../../redux/reducers/bookSlice';
import { fetchTopics, fetchAllTopics } from '../../../redux/reducers/topicSlice';
import { noWhiteSpace } from '../../common/form/validation'
import { pageParams } from '../../common/table/tableConsts'

const CreateBook = () => {
   const dispatch = useDispatch();
   const { loading, error } = useSelector(state => state.books)
   const topics = useSelector(state => state.topics.topics)
   const { userInfo } = useSelector((state) => state.auth)

   const [open, setOpen] = useState(false);


   const showModal = () => {
      setOpen(true);
      dispatch(fetchTopics());
   };

   const handleSubmit = async (values) => {
      const newBook = {
         ...values,
         topicId: topics.find(topic => topic.name === values.topic).id,
         userId: userInfo.id,
         fileList: fileList,
      }

      if (finish) {
         await dispatch(postBook(newBook));
         setOpen(false);
         dispatch(fetchBooksByUserId({ userId: userInfo.id, pageParams }));
      }
   };
   const handleCancel = () => {
      setOpen(false);
   };

   const [fileList, setFileList] = useState([]);
   const [finish, setFinish] = useState(false);

   return (
      <>
         <Button onClick={showModal}>
            <FileAddOutlined /> create a new book
         </Button>
         <Modal
            title="CrateBook"
            open={open}
            onOk={handleSubmit}
            confirmLoading={loading}
            onCancel={handleCancel}
            footer={
               <>
                  {error && <h3>Server error: {error}</h3>}
               </>
            }
         >
            <Form
               autoComplete="off"
               labelCol={{ span: 6 }}
               onFinish={(values => {
                  handleSubmit(values);
               })}
            >
               <Form.Item name="name" label="Name"
                  rules={[
                     {
                        required: true,
                        message: "Please enter your name"
                     },
                     { whitespace: true },
                     { min: 2, max: 100 },
                     noWhiteSpace,
                  ]}
               >
                  <Input placeholder="enter the book's name" />
               </Form.Item>

               <Form.Item name="topic" label="Topic"
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
                  />
               </Form.Item>

               <Form.Item name="shortDescription" label="Short Description"
                  rules={[
                     {
                        required: true,
                        message: "Please enter the author"
                     },
                     { whitespace: true },
                     { min: 5 },
                     { max: 45 }
                  ]}>
                  <Input placeholder='enter the description' />
               </Form.Item>

               <Form.Item name="description" label="Description"
                  rules={[
                     {
                        required: true,
                        message: "Please enter the author"
                     },
                     { whitespace: true },
                     { min: 20 },
                     { max: 255 }
                  ]}
               >
                  <Input.TextArea placeholder='enter the description'
                     autoSize={{ minRows: 3, maxRows: 4 }}
                  />
               </Form.Item>

               <Form.Item name="uploadFiles" label="Your File"
                  valuePropName='fileList'
                  getValueFromEvent={(event) => {
                     return event?.fileList
                  }}
                  rules={[{
                     required: true,
                     message: "Please upload the book file.",
                  },
                  {
                     validator(_, fileList) {
                        return new Promise((resolve, reject) => {
                           if (fileList && fileList[0]?.size > 2000000) {
                              reject('The file size exceeded');
                              setFinish(false);
                           }
                           else {
                              setFinish(true);
                              resolve("Success!")
                           }
                        });
                     }
                  }]}
               >
                  <Upload
                     maxCount={1}
                     customRequest={(info) => {
                        setFileList([info.file]);
                     }}
                     showUploadList={false}
                     accept=".txt,.pdf"
                  >
                     <Button>Upload</Button>
                     {fileList[0]?.name}
                  </Upload>
               </Form.Item>
               <Form.Item>
                  <Button htmlType='submit' loading={loading}><SaveOutlined /></Button>
               </Form.Item>
            </Form>
         </Modal>
      </>
   );
};
export default CreateBook;