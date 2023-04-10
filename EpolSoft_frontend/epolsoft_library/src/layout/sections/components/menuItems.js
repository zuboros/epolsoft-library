import React from "react";
import { Link } from "react-router-dom";
import { Button, Icon, Menu, Dropdown, Avatar, Space } from "antd";
import { UserOutlined } from '@ant-design/icons'

const items = [
   {
      label: <Link to="/lohin">
         Host
      </Link>,
      key: '0',
   },
   {
      label: <Link to="/login">
         <Button type="primary">Sign In</Button>
      </Link>,
      key: '1',
   },
   {
      type: 'divider',
   },
   {
      label: '3rd menu item',
      key: '3',
   },
];

export const MenuItems = () => {
   return (
      <Dropdown
         menu={
            { items, }
         }
         trigger={["click"]}
      >
         <a onClick={(e) => e.preventDefault()}>
            <Avatar size="large" icon={<UserOutlined />} />
         </a>

      </Dropdown>
   );
};