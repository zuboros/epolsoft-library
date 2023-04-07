import { useState } from 'react'
import { Space, Table, Button } from 'antd'
import { StopOutlined, ClockCircleOutlined } from '@ant-design/icons'

const INITIAL_ENTITIES_TOTAL = 1;
const INITIAL_PAGE_NUM = 1;
const INITIAL_PAGE_SIZE = 5;
const INITIAL_ORDER_FIELD = "id";
const ASC_ORDER = "ASC";
const DESC_ORDER = "DESC";

const UserTableTable = ({ entities, totalEntities, hiddenColumns, loading, editBtnHandler }) => {

   const [pageSize, setPageSize] = useState(INITIAL_PAGE_SIZE);

   const dataSource = entities.map(entity => ({
      key: entity.id,
      ...entity,
   }));

   const columns = (entityEntries) => {
      const cols = entityEntries.map(entityEntry => !hiddenColumns.find(column => column === entityEntry[0]) ?
         ({
            title: entityEntry[0].charAt(0).toUpperCase() + entityEntry[0].slice(1),
            dataIndex: entityEntry[0],
            key: entityEntry[0],
         }) :
         {}
      );
      cols.push({
         title: "Action",
         dataIndex: "action",
         key: "action",
         render: (_, record) =>
            null
      })
      return cols;
   }

   {/* <Space>
      {editable ? (
         <span>
            <Space size="middle">
               <Button type="primary" onClick={(e) => save(record)} ><SaveOutlined /></Button>
               <Popconfirm title="Are you sure to cancel?" onConfirm={cancel}>
                  <Button><StopOutlined /></Button>
               </Popconfirm>
            </Space>
         </span>
      ) : (
         <Space>

            <Button type='primary' onClick={() => { downloadFile("https://community.developers.refinitiv.com/storage/attachments/6024-data-file-download-guide-v26.pdf") }}><DownloadOutlined /></Button>
            {privateItem && <><Button type="primary" onClick={() => edit(record)} ><EditOutlined /></Button>
               <Popconfirm
                  title="Are you sure?"
                  onConfirm={() => deleteButton(record)}
               >
                  <Button danger type="primary" loading={deleteLoading}><DeleteOutlined /></Button>
               </Popconfirm></>}


         </Space>
      )}
   </Space> */}

   return (
      <Table dataSource={dataSource}
         columns={!!entities && columns(Object.entries(entities[0]))}
         loading={loading}
         pagination={{
            pageSize: pageSize,
            total: totalEntities || INITIAL_ENTITIES_TOTAL,
            /* onChange: (page, pageSize) => {
               setPageNum(page);
               setPageSize(pageSize);
               extractData(dispatch, { pageNum: page, pageSize: pageSize, sortField: sortField, sortOrder: sortOrder });
            } */
         }}
      >

      </Table>
   )
}

export default UserTableTable;