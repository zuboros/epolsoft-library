import { Descriptions, Tag, Button } from 'antd';
import { USER } from '../../redux/entitiesConst'
import { EditOutlined } from '@ant-design/icons';

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

const customLabel = (field) => {
   const labelsToChange = ["userName"];
   switch (field) {
      case labelsToChange[0]:
         return "Name"
      default:
         return field.charAt(0).toUpperCase() + field.slice(1);
   }
}

const mapDescription = (info) =>
   <Descriptions title="My info:" extra={<Button ><EditOutlined /> edit profile</Button>} style={{ width: "25%" }}>
      {info?.filter(field => !hiddenFields.find(hiddenField => hiddenField === field[0])).map(field =>
         <Descriptions.Item style={{ display: "block" }} key={field[0]} label={customLabel(field[0])}>{Array.isArray(field[1]) ? arrayRender(field[1]) : field[1]}</Descriptions.Item>
      ).reverse()}
   </Descriptions>

const UserDescription = ({ userInfo }) => mapDescription(Object.entries(userInfo));
export default UserDescription;