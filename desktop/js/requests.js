// 默认的请求基础地址
const servicePath = "http://localhost:8001";

/**
 * 请求发送方法，封装axios，返回Promise对象
 * @param {*} url 请求路径
 * @param {*} method 请求方式
 * @param {*} headers 请求头
 * @param {*} params 请求参数
 * @param {*} timeout 超时ms
 */
function request(url, method, headers, params, timeout){
    return new Promise((resolve, reject)=>{
        axios.request({
            url: servicePath + url,
            method: method,
            headers: headers,
            params: params,
            timeout: timeout
        })
        .then(response=>{
            resolve(response);
        })
        .catch(error=>{
            reject(error);
        });
    });
}