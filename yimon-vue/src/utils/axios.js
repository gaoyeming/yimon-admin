import axios from "axios";
import { ElMessage } from 'element-plus'
import { showLoading, closeLoading } from "@/utils/loading";

axios.defaults.baseURL = "http://127.0.0.1:8080";

const request = axios.create({
    timeout: 50000,
    headers: {
        'Content-Type': 'application/json; charset=utf-8'
    },
    // `responseType` 表示服务器响应的数据类型，可以是 'arraybuffer', 'blob', 'document', 'json', 'text', 'stream'
    responseType: 'json', // default
    // `paramsSerializer` 是一个负责 `params` 序列化的函数
    // (e.g. https://www.npmjs.com/package/qs, http://api.jquery.com/jquery.param/)
    // paramsSerializer: function (params) {
    //     return JSON.stringify(params)
    // },
});
//axios请求拦截
request.interceptors.request.use(config => {
    showLoading();//显示loading
    if (!config.data) {
        config.data = {};
    }
    console.log("request url:", config.baseURL + config.url);
    console.log("request data:", config.data);
    return config
});
//axios响应拦截
request.interceptors.response.use(response => {
    let res = response.data;
    console.log("response data:", res)
    closeLoading();//关闭loading
    if (res.resp_code === 200) {//请求成功，data存在
        return JSON.parse(res.data);
    } else if (res.resp_code === 201) {//请求成功，data不存在
        return {};
    } else {//请求失败
        const msg = res.resp_desc ? res.resp_desc : '未知错误！';
        ElMessage.error({
            message: msg,
            duration: 3 * 1000
        })
        return Promise.reject(msg);//抛出错误信息
    }
}, error => {//请求出现异常
    console.error("response exception:", error);
    closeLoading();//关闭loading
    const msg = error.message ? error.message : '请求异常';
    ElMessage.error({ message: msg, duration: 3 * 1000 });
    return Promise.reject(msg);//抛出错误信息
});

export default request