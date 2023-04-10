import { Button, Modal, Form, Input, AutoComplete, Upload, } from 'antd';
import { EditOutlined, SendOutlined } from '@ant-design/icons'
import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { postData, postFile } from '../../../redux/reducers/bookSlice';
import { fetchTopics, fetchAllTopics } from '../../../redux/reducers/topicSlice';
import { noWhiteSpace } from '../../common/form/validation'

const EditBook = ({ record }) => {
   const [form] = Form.useForm();

   const dispatch = useDispatch();
   const { status, error } = useSelector(state => state.books)
   const topics = useSelector(state => state.topics.topics)

   const [open, setOpen] = useState(false);
   const [confirmLoading, setConfirmLoading] = useState(false);

   useEffect(() => {
      form.setFieldsValue(record);
   }, [record]);

   const showModal = () => {
      setOpen(true);
      dispatch(fetchTopics());
   };

   const handleSubmit = (values) => {
      const data = {
         ...record,
         ...values
      }
      delete data.key;

      console.log(data);

      setConfirmLoading(true);
      postData(dispatch, values);
      setConfirmLoading(false);
      setOpen(false);
      dispatch(fetchAllTopics());
   };
   const handleCancel = () => {
      setOpen(false);
      dispatch(fetchAllTopics());
   };

   const [fileList, setFileList] = useState([]);
   const [finish, setFinish] = useState(false);

   return (
      <>
         <Button onClick={showModal}>
            <EditOutlined />
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
            <Form form={form}
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
                     { min: 3 },
                     noWhiteSpace,
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
                     { min: 3 },
                     noWhiteSpace,
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
               <Form.Item>
                  <Button htmlType='submit' loading={confirmLoading}><SendOutlined /></Button>
               </Form.Item>
            </Form>
         </Modal>
      </>
   );
};
export default EditBook;