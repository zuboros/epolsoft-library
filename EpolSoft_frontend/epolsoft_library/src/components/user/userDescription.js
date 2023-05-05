import { Descriptions, Tag, Button, Form, Space, Input, Avatar } from 'antd';
import { USER } from '../../redux/entitiesConst'
import { EditOutlined, StopOutlined, SaveOutlined, UserOutlined } from '@ant-design/icons';
import { useEffect, useState } from 'react';
import { DARK_COLOR } from '../../common/designConst'
import { useDispatch } from 'react-redux';
import { avatarDownload, putUser } from '../../redux/reducers/authSlice'
import UploadAvatar from './actions/uploadAvatar'
import { Link } from 'react-router-dom';

const hiddenFields = [
   'id',
   'userToken',
   'isBlocked'
]

const arrayRender = (roles) => (
   <>
      {roles.map((role) => {
         let color = 'green';
         if (role === USER) {
            color = 'volcano';
         }
         return (
            <Tag color={color} key={role} style={{ display: "inline" }}>
               {role.toUpperCase()}
            </Tag>
         );
      })}
   </>
)


const UserDescription = ({ userInfo, avatar }) => {
   const [editable, setEditable] = useState(false);
   const [newAvatar, setNewAvatar] = useState(null);
   const dispatch = useDispatch();
   const [form] = Form.useForm();

   useEffect(() => {
      editable && form.setFieldsValue(userInfo);
   }, [editable]);

   const info = Object.entries(userInfo);

   const customLabel = (field) => {
      const labelsToChange = ["userName"];
      switch (field) {
         case labelsToChange[0]:
            return "Name:"
         default:
            return field.charAt(0).toUpperCase() + field.slice(1);
      }
   }

   const handleSubmit = async (values) => {
      const newUserInfo = {
         id: userInfo.id,
         name: values.userName,
         mail: values.mail,
      }

      await dispatch(putUser({ user: newUserInfo, avatar: newAvatar }));
      dispatch(avatarDownload({ id: userInfo.id }));
      setEditable(false);
   };

   const commonRules = {
      required: true,
      whitespace: true,
      min: 3,
   };

   const noWhiteSpace = {
      validator: (_, value) =>
         !value.includes(" ")
            ? Promise.resolve()
            : Promise.reject(new Error("No spaces allowed"))
   }

   const getRules = (field) => {
      switch (field) {
         case "userName":
            return ([
               {
                  message: 'Please input your username!',
               },
               commonRules,
               noWhiteSpace,
            ]);
         default:
            return ([]);
      }
   }

   const notEditableFields = ["mail"];
   const editableField = (field) => !notEditableFields.find(hiddenField => hiddenField === field);

   return (
      <Space size={50}>
         <div className='avatar' style={{ width: 230, height: 230, display: "flex", justifyContent: "center", alignItems: "center" }}>
            {editable ?
               <UploadAvatar setAvatar={setNewAvatar} />
               :
               <Avatar size={228} icon={<UserOutlined />} src={avatar} style={{ backgroundColor: DARK_COLOR }} />}
         </div>
         <Form form={form}
            style={{ width: "25%", marginTop: 30 }}
            labelCol={{ span: 20 }}
            autoComplete="off"
            onFinish={(values => {
               handleSubmit(values);
            })}
         >
            {info?.filter(field => !hiddenFields.find(hiddenField => hiddenField === field[0])).map(field =>
               <Form.Item
                  key={field[0]}
                  name={field[0]}
                  label={customLabel(field[0])}
                  style={{ margin: 0 }}
                  rules={getRules(field[0])}
               >
                  <span style={{ marginLeft: 20, padding: 0 }}>
                     {editable && !Array.isArray(field[1]) && editableField(field[0]) ?
                        <Input defaultValue={field[1]} style={{ width: 150 }} />
                        :
                        Array.isArray(field[1]) ? arrayRender(field[1]) : field[1]}
                  </span>
               </Form.Item>
            ).reverse()}
            <Form.Item style={{ marginTop: 20 }}>
               {editable &&
                  <Space>
                     <Button htmlType='submit'><SaveOutlined /> save changes</Button>
                     <Button onClick={() => { setEditable(false); setNewAvatar(null) }}><StopOutlined /> cancel</Button>
                  </Space>
               }
            </Form.Item>
            {!editable &&
               <Space>
                  <Button onClick={() => setEditable(true)}><EditOutlined /> edit profile</Button>
                  <Link to="/password"><EditOutlined /> renew password</Link>
               </Space>
            }
         </Form>
      </Space>
   )
};
export default UserDescription;