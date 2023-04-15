import { useState } from 'react'
import { Table } from 'antd'
import * as table from './tableConsts'
import './tableStyle.css'

const EntitiesTable = ({ entities, totalEntities, addingColumns, actionColumn, hiddenColumns, loading, arrayRender, actionRender, extractEntities, addingExpandable }) => {

   const [pageNum, setPageNum] = useState(table.INITIAL_PAGE_NUM);
   const [pageSize, setPageSize] = useState(table.INITIAL_PAGE_SIZE);
   const [sortField, setSortField] = useState(table.INITIAL_ORDER_FIELD);
   const [sortOrder, setSortOrder] = useState(table.ASC_ORDER);

   const dataSource = entities.map(entity => ({
      key: entity.id,
      ...entity,
   }));

   const columns = (entityEntries) => {
      const cols = entityEntries.map(entityEntry => !hiddenColumns.find(column => column === entityEntry[0]) ?
         ({
            title: <div className='headerCell'> {entityEntry[0].charAt(0).toUpperCase() + entityEntry[0].slice(1)} </div>,
            dataIndex: entityEntry[0],
            key: entityEntry[0],
            align: "center",
            render: Array.isArray(entityEntry[1]) && arrayRender,
            onHeaderCell: (column) => {
               return {
                  onClick: () => {
                     console.log(column);
                     setSortField(column.dataIndex);
                     setSortOrder(sortOrder === table.ASC_ORDER ? table.DESC_ORDER : table.ASC_ORDER);
                     extractEntities({ pageNum: pageNum, pageSize: pageSize, sortField: column.dataIndex, sortOrder: sortOrder === table.ASC_ORDER ? table.DESC_ORDER : table.ASC_ORDER });
                  }
               };
            },
         }) :
         null
      ).filter(entityEntry => !!entityEntry);

      !!addingColumns && cols.push(addingColumns);
      !!actionColumn && cols.push({
         title: "Action",
         dataIndex: "action",
         key: "action",
         align: "center",
         render: actionRender,
      })
      return cols;
   }

   return (
      <Table dataSource={dataSource}
         columns={!!entities && entities?.length != 0 && columns(Object.entries(entities[0]))}
         loading={loading}
         bordered
         pagination={{
            pageSize: pageSize,
            total: totalEntities || table.INITIAL_ENTITIES_TOTAL,
            onChange: (page, pageSize) => {
               setPageNum(page);
               setPageSize(pageSize);
               extractEntities({ pageNum: page, pageSize: pageSize, sortField: sortField, sortOrder: sortOrder });
            }
         }}
         expandable={{
            expandedRowRender: addingExpandable,
         }}
      >
      </Table>
   )
}

export default EntitiesTable;