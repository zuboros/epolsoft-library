import { useState } from 'react'
import { Space, Table, Button } from 'antd'
import { DeleteOutlined } from '@ant-design/icons'

const INITIAL_ENTITIES_TOTAL = 1;
const INITIAL_PAGE_NUM = 1;
const INITIAL_PAGE_SIZE = 5;
const INITIAL_ORDER_FIELD = "id";
const ASC_ORDER = "ASC";
const DESC_ORDER = "DESC";

const UserTable = ({ entities, totalEntities, hiddenColumns, loading, deleteBtnHandler }) => {



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
            <Space>
               {!record?.isActive ?
                  <Button danger onClick={() => deleteBtnHandler(record)} ><DeleteOutlined /></Button> :
                  null
               }
            </Space>
      })
      return cols;
   }

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

export default UserTable;