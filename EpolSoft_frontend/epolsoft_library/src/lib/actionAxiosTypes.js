import { serverURL } from './axios'

export const GET = 'get';
export const POST = 'post';
export const DELETE = 'delete';
export const PUT = 'put';

//BOOKS
export const PATH_GET_BOOKS_WITH_PARAMS = ({ pageNum, pageSize, sortField, sortOrder }) =>
   `/api/library?page=${(pageNum - 1) || "0"}&size=${pageSize || "0"}&sort=${sortField || "id"},${sortOrder || "ASC"}`;

export const PATH_GET_BOOKS = "/library";

export const PATH_POST_BOOK = `/api/book`;

export const PATH_DELETE_BOOK = ({ id }) => `/api/book/delete/${id}`;
export const PATH_PUT_BOOK = `/api/book/update`;
export const PATH_GET_BOOKS_BY_USER_ID = ({ id }, { pageNum, pageSize, sortField, sortOrder }) =>
   `/api/library/${id || ""}/books?page=${(pageNum - 1) || "0"}&size=${pageSize || "0"}&sort=${sortField || "id"},${sortOrder || "ASC"}`;

//TOPICS
export const PATH_GET_AVAILABLE_TOPICS = `api/topic/get/available`;
export const PATH_GET_ALL_TOPICS = ({ pageNum, pageSize, sortField, sortOrder }) => `api/topic/get/all?page=${(pageNum - 1) || "0"}&size=${pageSize || "0"}&sort=${sortField || "id"},${sortOrder || "ASC"}`;
export const PATH_DELETE_TOPIC = ({ id }) => `api/topic/delete/${id}`;
export const PATH_POST_TOPIC = '/api/topic/create';
export const PATH_PUT_TOPIC = '/api/topic/update';

//AUTH
export const PATH_USER_REGISTER = `/api/authors/signup`;
export const PATH_USER_LOGIN = `/api/authors/signin`;

//Users
export const PATH_GET_USERS = ({ pageNum, pageSize, sortField, sortOrder }) => `/api/authors/get?page=${(pageNum - 1) || "0"}&size=${pageSize || "0"}&sort=${sortField || "id"},${sortOrder || "ASC"}`;
export const PATH_BLOCK_USERS = ({ id }) => `/api/author/block/${id}`;

//File/Avatar
export const PATH_UPLOAD_FILE = "/api/file/upload?type=book";
export const PATH_DELETE_FILE = ({ id }) => `/api/file/delete/${id}`;
export const PATH_EXTRACT_FILE = ({ id }) => `/api/file/download/${id}?type=book`;
export const PATH_EXTRACT_AVATAR = ({ id }) => `${serverURL}/api/file/download/${id}?type=avatar`;