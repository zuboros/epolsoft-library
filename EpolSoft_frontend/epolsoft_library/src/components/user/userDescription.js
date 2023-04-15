import { Descriptions, Tag, Button } from 'antd';
import { USER } from '../../redux/entitiesConst'

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
            <Tag color={color} key={role}>
               {role.toUpperCase()}
            </Tag>
         );
      })}
   </>
)

const mapDescription = (info) =>
   <Descriptions title="My info:" extra={<Button >Edit</Button>}>
      {info?.filter(field => !hiddenFields.find(hiddenField => hiddenField === field[0])).map(field =>
         <Descriptions.Item style={{ width: "100%", display: "block" }} key={field[0]} label={field[0].charAt(0).toUpperCase() + field[0].slice(1)}>{Array.isArray(field[1]) ? arrayRender(field[1]) : field[1]}</Descriptions.Item>
      ).reverse()}
   </Descriptions>

const UserDescription = ({ userInfo }) => mapDescription(Object.entries(userInfo));
export default UserDescription;