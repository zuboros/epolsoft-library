export const GET = 'get';
export const POST = 'post';
export const DELETE = 'delete';
export const PUT = 'put';

//BOOKS

export const PATH_GET_BOOKS_WITH_PARAMS = ({ pageNum, pageSize, sortField, sortOrder }) =>
   `/api/library?number=${pageNum || "0"}&size=${pageSize || "0"}&sort=${sortField || "id"},${sortOrder || "ASC"}`;

export const PATH_GET_BOOKS = "/library";

export const PATH_POST_BOOK = `/library`;

export const PATH_DELETE_BOOK = ({ id }) => `/library/deleteNote/${id || ""}`;

export const convertToPostRequest = (data) => ({
   bookId: null,
   bookName: data.name,
   bookShortDescription: data.shortDescription,
   bookDescription: data.description,
   booFileName: data.file,
   topicId: data.topicId,
   topicName: data.topicName,
   authorName: data.author,
});

export const PATH_UPLOAD_FILE = "/library/uploadFile";

//TOPICS

export const PATH_GET_TOPICS = `/library/topics`