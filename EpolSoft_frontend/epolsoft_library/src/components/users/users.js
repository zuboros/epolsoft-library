import { Space } from 'antd';
import { useDispatch, useSelector } from 'react-redux';
import { fetchUsers, blockUser } from '../../redux/reducers/userSlice';
import { USERS, AUTH } from '../../redux/entitiesConst'
import { useEffect } from 'react';
import UserTable from './table/userTable'

const Users = () => {
   const { error, loading, [USERS]: users, success, totalUsers } = useSelector(state => state[USERS]);
   const { userToken } = useSelector(state => state[AUTH])
   const dispatch = useDispatch();

   useEffect(() => {
      dispatch(fetchUsers(userToken));
   }, [dispatch])

   const hiddenColumns = [
      "isBlocked",
      "id"
   ]

   const blockHandler = (record) => {
      dispatch(blockUser({ auth: userToken, id: record.id }));
   }

   return (
      <>
         <div>
            <h2>Users:</h2>
            {error && <h3>{error}</h3>}
         </div>
         {success && <UserTable entities={users} totalEntities={totalUsers} hiddenColumns={hiddenColumns} loading={loading} buttonHandler={blockHandler} />}
      </>
   )
}

export default Users;