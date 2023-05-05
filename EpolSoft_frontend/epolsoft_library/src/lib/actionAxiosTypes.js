import { serverURL } from './axios'

export const GET = 'get';
export const POST = 'post';
export const DELETE = 'delete';
export const PUT = 'put';

//BOOKS
export const PATH_GET_BOOKS_WITH_PARAMS = ({ pageNum, pageSize, sortField, sortOrder }) =>
   `/api/library?page=${(pageNum - 1) || "0"}&size=${pageSize || "0"}&fieldName=&fieldValue=&sort=${sortField || "id"},${sortOrder || "ASC"}`;
export const PATH_GET_BOOKS = "/library";
export const PATH_POST_BOOK = `/api/book`;
export const PATH_DELETE_BOOK = ({ id }) => `/api/book/delete/${id}`;
export const PATH_PUT_BOOK = `/api/book/update`;
export const PATH_GET_BOOKS_BY_USER_ID = ({ id }, { pageNum, pageSize, sortField, sortOrder }) =>
   `/api/library/${id || ""}/books?page=${(pageNum - 1) || "0"}&size=${pageSize || "0"}&sort=${sortField || "id"},${sortOrder || "ASC"}&fieldName=&fieldValue=`;
export const PATH_SET_STATUS_WAIT_APPROVING = ({ id }) => `/api/book/${id}?status=WAIT_APPROVING`;
export const PATH_SET_STATUS_ARCHIVED = ({ id }) => `/api/book/${id}?status=ARCHIVED`;
export const PATH_SET_STATUS_BLOCKED = ({ id }) => `/api/book/${id}?status=BLOCKED`;
export const PATH_SET_STATUS_ACTIVED = ({ id }) => `/api/book/${id}?status=ACTIVED`;

//STATUSES
export const WAIT_APPROVING = "WAIT_APPROVING";
export const ARCHIVED = "ARCHIVED";
export const BLOCKED = "BLOCKED";
export const ACTIVED = "ACTIVED";
export const CREATED = "CREATED";

//TOPICS
export const PATH_GET_AVAILABLE_TOPICS = `api/topic/get/available`;
export const PATH_GET_ALL_TOPICS = ({ pageNum, pageSize, sortField, sortOrder }) => `api/topic/get/all?page=${(pageNum - 1) || "0"}&size=${pageSize || "0"}&sort=${sortField || "id"},${sortOrder || "ASC"}`;
export const PATH_DELETE_TOPIC = ({ id }) => `api/topic/delete/${id}`;
export const PATH_POST_TOPIC = '/api/topic/create';
export const PATH_PUT_TOPIC = '/api/topic/update';
export const PATH_DISABLE_TOPIC = ({ id }) => `/api/topic/disable/${id}`
export const PATH_ENABLE_TOPIC = ({ id }) => `/api/topic/enable/${id}`

//AUTH
export const PATH_USER_REGISTER = `/api/authors/signup`;
export const PATH_USER_LOGIN = `/api/authors/signin`;

//Users
export const PATH_GET_USERS = ({ pageNum, pageSize, sortField, sortOrder }) => `/api/authors/get?page=${(pageNum - 1) || "0"}&size=${pageSize || "0"}&sort=${sortField || "id"},${sortOrder || "ASC"}`;
export const PATH_BLOCK_USERS = ({ id }) => `/api/authors/block/${id}`;
export const PATH_UNBLOCK_USERS = ({ id }) => `/api/authors/unblock/${id}`;

//File/Avatar
export const PATH_UPLOAD_FILE = "/api/file/upload?type=book";
export const PATH_DELETE_FILE = ({ id }) => `/api/file/delete/${id}`;
export const PATH_EXTRACT_FILE = ({ id }) => `/api/file/download/${id}?type=book`;
export const PATH_EXTRACT_AVATAR = ({ id }) => `${serverURL}/api/file/download/${id}?type=avatar`;
export const PATH_UPLOAD_AVATAR = "/api/file/upload?type=avatar";