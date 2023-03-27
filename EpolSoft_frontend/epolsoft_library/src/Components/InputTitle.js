const InputBookForm = ({ value, updateTitle, handleAction }) => {
   return (
      <label>
         <input
            placeholer='new book'
            value={value}
            onChange={(e) => updateTitle(e.target.value)}
         />
         <button onClick={handleAction}>Add book</button>
      </label>
   );
};

export default InputBookForm;