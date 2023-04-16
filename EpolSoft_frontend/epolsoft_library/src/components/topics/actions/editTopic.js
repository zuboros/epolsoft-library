import { Button, Modal, Form, Input, AutoComplete, Upload, } from 'antd';
import { EditOutlined, SaveOutlined } from '@ant-design/icons'
import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { putTopic } from '../../../redux/reducers/topicSlice';
import { fetchTopics } from '../../../redux/reducers/topicSlice';
import { noWhiteSpace } from '../../common/form/validation'
import { pageParams } from '../../common/table/tableConsts'

const EditTopic = ({ record, getTopics }) => {
   const [form] = Form.useForm();

   const dispatch = useDispatch();
   const { error, loading } = useSelector(state => state.topics)

   const [open, setOpen] = useState(false);

   useEffect(() => {
      form.setFieldsValue(record);
      console.log('record');
      console.log(record);


   }, [record]);

   const showModal = () => {
      setOpen(true);
   };

   const handleSubmit = async (values) => {
      const data = {
         id: record.id,
         name: values.name,
      }
      await dispatch(putTopic(data));
      setOpen(false);
      getTopics(pageParams);
   };
   const handleCancel = () => {
      setOpen(false);
   };


   return (
      <>
         <Button type='link' onClick={showModal}>
            <EditOutlined />
         </Button>
         <Modal
            title="EditBook"
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
                     { min: 5 },
                     { max: 45 },
                  ]}
               >
                  <Input placeholder="enter the topic name" />
               </Form.Item>

               <Form.Item>
                  <Button htmlType='submit' loading={loading}><SaveOutlined /></Button>
               </Form.Item>
            </Form>
         </Modal>
      </>
   );
};
export default EditTopic;