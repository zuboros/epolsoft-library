export const convertBooksToTable = (books) => books?.map(book => ({ key: book.id, ...book }));


const downloadFile = (url) => {
   const fileName = url.split("/").pop();
   const aTag = document.createElement("a");
   aTag.href = url;
   aTag.setAttribute("download", fileName);
   document.body.appendChild(aTag);
   aTag.click();
   aTag.remove();
}