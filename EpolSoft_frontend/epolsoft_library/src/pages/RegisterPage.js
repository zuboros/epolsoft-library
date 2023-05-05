import { Button, Checkbox, Form, Input, Space } from 'antd';
import { useDispatch, useSelector } from 'react-redux'
import Error from '../components/common/error'
import { registerUser, setSuccess } from '../redux/reducers/authSlice'
import { useNavigate } from 'react-router-dom'
import { useEffect } from 'react'

const RegisterPage = () => {
   const dispatch = useDispatch()
   const { loading, userInfo, error, success } = useSelector(
      (state) => state.auth
   );
   const navigate = useNavigate()


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

   useEffect(() => {

      if (success) { navigate('/login'); dispatch(setSuccess(false)); }

      //if (userInfo) navigate('/user-profile')
   }, [navigate, userInfo, success])


   const onFinish = (data) => {
      data.email = data.email.toLowerCase();
      dispatch(registerUser(data))
   };

   return (
      <Space style={{ justifyContent: "center", alignItems: "center" }}>
         <Form
            labelCol={{
               span: 8,
            }}
            wrapperCol={{
               span: 16,
            }}
            style={{
               minWidth: 600,
               paddingRight: 120,
               paddingTop: 150,
            }}
            onFinish={onFinish}
            autoComplete="off"
         >
            {error && <Error>{error}</Error>}
            <Form.Item
               label="Username"
               name="userName"
               rules={[
                  {
                     message: 'Please input your username!',
                  },
                  commonRules,
                  noWhiteSpace,
               ]}
            >
               <Input />
            </Form.Item>

            <Form.Item
               label="Email"
               name="email"
               rules={[
                  {
                     message: 'Please input your email!',
                     type: "email",
                  },
                  commonRules,
                  noWhiteSpace,
               ]}
            >
               <Input />
            </Form.Item>

            <Form.Item
               label="Password"
               name="password"
               rules={[
                  {
                     message: 'Please input your password!',
                  },
                  commonRules,
                  noWhiteSpace,
               ]}
            >
               <Input.Password />
            </Form.Item>
            <Form.Item
               label="Confirm Password"
               name="confirmPassword"
               rules={[
                  {
                     message: 'Confirm your password',
                  },
                  commonRules,
                  noWhiteSpace,
                  ({ getFieldValue }) => ({
                     validator(_, value) {
                        if (!value || getFieldValue('password') === value) {
                           return Promise.resolve();
                        }
                        return Promise.reject('The two passwords that you entered do not match!');
                     },
                  })
               ]}
            >
               <Input.Password />
            </Form.Item>

            <Form.Item
               wrapperCol={{
                  offset: 8,
                  span: 16,
               }}
            >
               <Button htmlType="submit" loading={loading}>
                  SignUp
               </Button>
            </Form.Item>
         </Form>
      </Space>
   )
};
export default RegisterPage;