import { Button, Modal, Form, Input, AutoComplete, Upload } from 'antd';
import { FileAddOutlined } from '@ant-design/icons'
import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { postData, postFile } from '../../../redux/reducers/bookSlice';
import { fetchLocalTopics } from '../../../redux/reducers/topicSlice';

const CreateBook = () => {
   const dispatch = useDispatch();
   const { status, error } = useSelector(state => state.books)
   const topics = useSelector(state => state.topics.topics)

   const [open, setOpen] = useState(false);
   const [confirmLoading, setConfirmLoading] = useState(false);

   useEffect(() => {
      dispatch(fetchLocalTopics({}));
   }, [])

   const showModal = () => {
      setOpen(true);
   };

   const handleSubmit = (values) => {

      if (finish) {
         setConfirmLoading(true);
         ///api function:
         postData(dispatch, values);
         //dispatch(postBook({ ...values, topicId: (topics.find((topic) => topic.name === values.topic)).id }));
         //setOpen(false);
         setConfirmLoading(false);
         //////////////////////////////////
         /* setTimeout(() => {

            ///local function:
            dispatch(addBook(temporaryConvertToBook(values)));

            setOpen(false);
            setConfirmLoading(false);
         }, 1000); */
         ////////////////////////////////////
      }
   };
   const handleCancel = () => {
      console.log('Clicked cancel button');
      setOpen(false);
   };

   const [fileList, setFileList] = useState([]);
   const [finish, setFinish] = useState(false);

   return (
      <>
         <Button type="primary" onClick={showModal}>
            <FileAddOutlined />
         </Button>
         <Modal
            title="Title"
            open={open}
            onOk={handleSubmit}
            confirmLoading={confirmLoading}
            onCancel={handleCancel}
            footer={
               <>
                  {status === 'loading' && <h3>Loading...</h3>}
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
                     { min: 3 }
                  ]}
               >
                  <Input placeholder="enter the book's name" />
               </Form.Item>

               <Form.Item name="author" label="Author"
                  rules={[
                     {
                        required: true,
                        message: "Please enter the author"
                     },
                     { whitespace: true },
                     { min: 3 }
                  ]}
               >
                  <Input placeholder="enter the author's name" />
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
                     { min: 3 },
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
                     { min: 3 },
                     { max: 500 }
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
                           if (fileList && fileList[0]?.size < 2) {
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
                     accept=".txt,.pdf,.azw,.azw3,.mobi,.epub"
                  >
                     <Button>Upload</Button>
                     {fileList[0]?.name}
                  </Upload>
               </Form.Item>
               <Form.Item>
                  <Button type="primary" htmlType='submit' loading={confirmLoading}>Submit</Button>
               </Form.Item>
            </Form>
         </Modal>
      </>
   );
};
export default CreateBook;