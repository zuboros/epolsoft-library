import { Button, Modal, Form, Input, AutoComplete, Upload } from 'antd';
import { FileAddOutlined, SaveOutlined } from '@ant-design/icons'
import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchAllTopics, postTopic } from '../../../redux/reducers/topicSlice';
import { noWhiteSpace } from '../../common/form/validation'
import { pageParams } from '../../common/table/tableConsts'

const CreateTopic = () => {
   const dispatch = useDispatch();
   const { topics, loading, error } = useSelector(state => state.topics.topics)

   const [open, setOpen] = useState(false);

   const showModal = () => {
      setOpen(true);
   };

   const handleSubmit = async (values) => {
      const newTopic = {
         ...values,
      }

      await dispatch(postTopic(newTopic));
      dispatch(fetchAllTopics(pageParams));
      setOpen(false);
   };
   const handleCancel = () => {
      setOpen(false);
   };

   return (
      <>
         <Button onClick={showModal}>
            <FileAddOutlined /> create a new topic
         </Button>
         <Modal
            title="CreateTopic"
            open={open}
            onOk={handleSubmit}
            confirmLoading={loading}
            onCancel={handleCancel}
            footer={
               <>
                  {loading && <h3>Loading...</h3>}
                  {error && <h3>Server error: {error}</h3>}
               </>
            }
            maskClosable={false}
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
                        message: "Please enter topic name"
                     },
                     { whitespace: true },
                     { min: 2, max: 100 },
                  ]}
               >
                  <Input placeholder="enter the topic's name" />
               </Form.Item>
               <Form.Item style={{ display: "flex", justifyContent: "right" }}>
                  <Button htmlType='submit' loading={loading}><SaveOutlined /></Button>
               </Form.Item>
            </Form>
         </Modal>
      </>
   );
};
export default CreateTopic;