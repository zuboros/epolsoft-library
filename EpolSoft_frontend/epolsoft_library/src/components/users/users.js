import { Space, Button, Tag, Popover } from 'antd'
import { StopOutlined, ClockCircleOutlined } from '@ant-design/icons'
import { useDispatch, useSelector } from 'react-redux';
import { fetchUsers, blockUser, unblockUser } from '../../redux/reducers/userSlice';
import { USERS, USER } from '../../redux/entitiesConst'
import { useEffect } from 'react';
import UserTable from '../common/table/table'
import * as table from '../common/table/tableConsts'

const Users = () => {
   const { error, loading, [USERS]: users, success, totalUsers, blockLoading } = useSelector(state => state[USERS]);

   const dispatch = useDispatch();

   const getUsers = (pageParams) => {
      dispatch(fetchUsers(pageParams));
   }

   useEffect(() => {
      getUsers(table.pageParams);
      console.log('all users');
      console.log(users);

   }, [dispatch])

   const hiddenColumns = [
      "isBlocked",
      "id"
   ]

   const unsortedColumns = [
      "roles",
   ]

   const blockHandler = async (record) => {
      await dispatch(blockUser({ id: record.id }));
      getUsers(table.pageParams);
   }

   const unblockHandler = async (record) => {
      await dispatch(unblockUser({ id: record.id }));
      getUsers(table.pageParams);
   }

   const actionRender = (_, record) => {
      console.log('record');
      console.log(record);

      return (<Space>
         {!record?.isBlocked ?
            <Popover title="Block the user" trigger="hover"><Button danger type='link' loading={blockLoading} onClick={() => blockHandler(record)} ><StopOutlined /></Button> </Popover> :
            <Popover title="Unlock the user" trigger="hover"><Button type='link' loading={blockLoading} onClick={() => unblockHandler(record)}><ClockCircleOutlined /></Button></Popover>
         }
      </Space>)
   }

   const arrayRender = (_, { roles }) => (
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

   return (
      <>
         <div>
            <h2>Users:</h2>
            {error && <h3>{error}</h3>}
         </div>
         {success &&
            <UserTable
               entities={users}
               totalEntities={totalUsers}
               hiddenColumns={hiddenColumns}
               unsortedColumns={unsortedColumns}
               loading={loading}
               actionColumn={true}
               arrayRender={arrayRender}
               actionRender={actionRender}
               extractEntities={getUsers}
            />}
      </>
   )
}

export default Users;