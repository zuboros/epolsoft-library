import { useDispatch } from 'react-redux';
import { removeBook, deleteBook, editBook } from '../store/bookSlice';
import { useState } from "react"
import { Button, Input } from 'antd';


const BookItem = ({ id, ...fields }) => {
   const dispatch = useDispatch();
   const [edit, setEdit] = useState(false);
   const [name, setName] = useState(fields.name);
   return (
      <li>
         <p><span>id: {id}</span></p>
         {edit ? <p>name: <Input value={name} onChange={(e) => setName(e.target.value)} /></p> : <p><span>name: {fields.name}</span></p>}
         <p><span>author: {fields.author}</span></p>
         <p><span>topic: {fields.topic}</span></p>
         <p><span>shortDescription: {fields.shortDescription}</span></p>
         <p><span>description: {fields.description}</span></p>
         <p><span>fileName: {fields.fileName}</span></p>
         <p><span onClick={() => dispatch(removeBook({ id }))}>&times;</span></p>
         {edit ? <><Button onClick={() => {
            setEdit(edit => !edit);
            console.log({ id, ...fields, name: name });

            dispatch(editBook({ id, ...fields, name: name }));
         }}
         >Confirm</Button>
            <Button onClick={() => setEdit(edit => !edit)}>Cancel</Button></> : <Button onClick={() => { setEdit(edit => !edit); setName(fields.name); }}>Edit</Button>}
      </li>
   );
};

export default BookItem;