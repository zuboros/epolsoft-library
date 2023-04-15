export const postBookDto = (book, file) => {

   delete book['topic'];

   return ({
      ...book,
      fileName: file.name,
      filePath: file.path
   })
}

export const putBookDto = (book, file, isExist) => {
   console.log('book');


   const bookDto = isExist ? {
      ...book,
      userId: book.authorId,
      fileName: file.name,
      filePath: file.path,
   } : {
      ...book,
      fileName: null,
      filePath: null,
   }

   delete bookDto['topic'];
   delete bookDto['author'];
   delete bookDto['authorId'];
   delete bookDto['uploadFiles'];
   delete bookDto['createdAt'];
   delete bookDto['updatedAt'];

   console.log(bookDto);
   return bookDto;
}