import axios from 'axios'
import os from "os";

const request = axios.create({
    baseURL: 'http://localhost:80',
    timeout: 5000
})
export default request
// config.js
export const BASE_IP = location.hostname;
export const BASE_PORT = '1234';
export const BASE_URL = `http://${BASE_IP}:${BASE_PORT}`;
export const LOGIN_API = `${BASE_URL}/api/login`;
export const REGISTER_API = `${BASE_URL}/api/register`;
export const EMAIL_API = `${BASE_URL}/api/sendemail`;

export const RESET_PASSWORD_API = `${BASE_URL}/api/reset/password`;
export const GET_QUES_LIST_API = `${BASE_URL}/api/fillin/getQuestionList`;
export const GET_QUES_PAPER_OUTLINE_API = `${BASE_URL}/api/fillin/getQuePaperOutline`;
export const GET_SUBMIT_ANS_API = `${BASE_URL}/api/fillin/submitAnswer`;
export const GET_SAVED_ANS_API = `${BASE_URL}/api/fillin/getAnswerList`;
export const GET_ALL_QUES_PAPERS=`${BASE_URL}/getAllQuePapers`;
export const POST_UPLOAD_AVATAR = `${BASE_URL}/uploadAvatar`;
export const GET_AVATAR = `${BASE_URL}/getAvatar`;

