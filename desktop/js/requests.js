// 默认的请求基础地址
const servicePath = "http://******:8001";

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

/**
 * POST 请求
 * @param {*} url 
 * @param {*} data 
 * @param {*} config 
 */
function request_post(url, data, config){
    return new Promise((resolve, reject)=>{
        axios.post(servicePath + url, data, config)
        .then(response=>{
            resolve(response);
        })
        .catch(error=>{
            reject(error);
        })
    })
}

/**
 * 下载文件请求
 * @param {*} url 
 * @param {*} params 
 */
function download_request(url, params, vm){
    return new Promise((resolve, reject)=>{
        axios.get(servicePath + url, {
            headers: {
                // header中带有token
                token: window.sessionStorage.getItem("token")
            },
            params: params,
            responseType: 'arraybuffer', // 以arraybuffer形式接收数据，这样保证了非文本的二进制文件(比如图片)编码格式
            onDownloadProgress: function(progressEvent){
                // 更新进度条
                vm.download_queue[0].progress = Math.round(progressEvent.loaded * 100 / vm.download_queue[0].size);
                // 更新实时下载速度
                //vm.currentDownloadSpeed = vm.getDownloadSpeed(progressEvent.loaded, time=10);
                
                // 下载完成后，清空进度条
                if(vm.download_queue[0].progress == 100){
                    progressEvent.loaded=0;
                }
            }
        }).then(response=>{
            resolve(response);
        }).catch(error=>{
            reject(error);
        });
    })
}