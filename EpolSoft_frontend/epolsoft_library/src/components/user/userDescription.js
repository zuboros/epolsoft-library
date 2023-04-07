import { Descriptions } from 'antd';

const mapDescription = (info) =>
   <Descriptions title="User info:">
      {info?.map(field =>
         <Descriptions.Item key={field[0]} label={field[0]}>{field[1]}</Descriptions.Item>
      )}
   </Descriptions>

const UserDescription = ({ userInfo }) => mapDescription(Object.entries(userInfo));
export default UserDescription;