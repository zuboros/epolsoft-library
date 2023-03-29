import { Button, Modal, Form, Input, AutoComplete, Upload } from 'antd';
import { useState } from 'react';

const topics = [
   {
      id: 1,
      name: "Gachimuchi"
   },
   {
      id: 2,
      name: "BoringWorld"
   },
]

const AddBook = () => {
   const [open, setOpen] = useState(false);
   const [confirmLoading, setConfirmLoading] = useState(false);
   const showModal = () => {
      setOpen(true);
   };
   const handleSubmit = (values) => {
      console.log(values);

      if (finish) {
         setConfirmLoading(true);
         //////////////////////////////////
         setTimeout(() => {
            setOpen(false);
            setConfirmLoading(false);
         }, 2000);
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
            Open Modal with async logic
         </Button>
         <Modal
            title="Title"
            open={open}
            onOk={handleSubmit}
            confirmLoading={confirmLoading}
            onCancel={handleCancel}
            footer={null}
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
                     placeholder="try not to cum"
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
                     { max: 250 }
                  ]}
               >
                  <Input.TextArea placeholder='enter the description'
                     autoSize={{ minRows: 3, maxRows: 4 }}
                  />
               </Form.Item>

               <Form.Item name="uploadFile" label="Your File"
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
export default AddBook;