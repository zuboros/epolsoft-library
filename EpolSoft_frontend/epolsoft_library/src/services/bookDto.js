export const postBookDto = (book, file) => ({
   name: book.name,
   shortDescription: book.shortDescription,
   description: book.description,
   fileName: file.name,
   filePath: file.path,
   topicId: book.topicId,
   userId: book.userId
})


export const putBookDto = (book, file, isExist) => {
   console.log('book');


   const bookDto = isExist ? {
      id: book.id,
      name: book.name,
      shortDescription: book.shortDescription,
      description: book.description,
      userId: book.authorId,
      fileName: file.name,
      filePath: file.path,
      topicId: book.topicId,
      userId: book.userId
   } : {
      id: book.id,
      name: book.name,
      shortDescription: book.shortDescription,
      description: book.description,
      fileName: null,
      filePath: null,
      topicId: book.topicId,
      userId: book.userId
   }
   console.log(bookDto);
   return bookDto;
}