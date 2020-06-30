var servicePath = "http://127.0.0.1:8001";

function getAllFiles(){
    // 获取用户id和当前目录id
    var session = window.sessionStorage;
    var user_id = JSON.parse(session.getItem("user")).user_id;
    var currentPath = session.getItem("currentPath");

    // 设置模板变量currentPath
    appVM.currentPath = currentPath;
    // 向后台发送请求，获取当前目录所有文件
    axios.get(servicePath+"/user_files", {
        params: {
            "user_id": user_id, //用户id
            "parent": currentPath  // 目录id
        }
    })
    .then(function(response){
        appVM.file_list = response.data; // 设置模板变量file——list为response数据
    })
    .catch(function(response){
        alert("获取所有文件出错");
        console.log(response);
    })
    // 设置上级目录
    getUpperPath(currentPath);
}

/**
 * 打开某个文件夹，即点击某个文件夹后更新文件区为文件夹内容
 * @param {*} id 
 */
function openFolder(id){
    // session保存当前目录id
    var session = window.sessionStorage;
    session.setItem("currentPath", id);
    getAllFiles();
}

/**
 * 返回上级目录
 */
function toUpperPath(){
    // 获取上级目录id，更改当前目录id
    var session = window.sessionStorage;
    var upper = session.getItem("upperPath");
    session.setItem("currentPath", upper);

    // 加载上级目录文件
    getAllFiles();
}

/**
 * 返回根目录
 */
function toRootPath(){
    // session设置当前目录为根目录
    var session = window.sessionStorage;
    session.setItem("currentPath", 1);

    // 加载根目录文件
    getAllFiles();
}

/**
 * 查找当前目录的上级目录
 * @param {*} currentPath 
 */
function getUpperPath(currentPath){
    var session = window.sessionStorage;
    var user_id = JSON.parse(session.getItem("user")).user_id;

    if(currentPath != 1){
        axios.get(servicePath + "/upperpath", {
            params:{
                "currentPath": currentPath,
                "user_id": user_id
            }
        })
        .then(function(response){
            session.setItem("upperPath", response.data);
        })
        .catch(function(response){
            console.log(response);
            alert("获取上级目录失败");
        })
    }
    else{
        session.setItem("upperPath", 0);
    }
}

/**
 * 创建文件夹
 */
function createFolder(){
    var foldername = createFolderVM.foldername;
    // 获取用户id和当前目录id
    var session = window.sessionStorage;
    var user_id = JSON.parse(session.getItem("user")).user_id;
    var currentPath = session.getItem("currentPath");

    // 检查文件名是否合格
    checkFolderName(user_id, currentPath, foldername);
    var fnstat = createFolderVM.stat;
    
    //console.log(fnstat);  debug语句
    if(fnstat == 1){
        alert("开始创建");
    }
    else if(fnstat == 0){
        alert("文件夹名称过长，只允许20字符以下");
    }
    else if(fnstat == -1){
        alert("文件夹重名，请更改");
    }
    else{
        alert("创建出错，请重试");
    }
}

/**
 * 检查文件名是否合格
 * @param {*} foldername 
 */
function checkFolderName(user_id, currentPath, foldername){
    // 文件名小于等于20字符
    if(foldername.length > 20){
        createFolderVM.stat = 0;
    }
    else{
        // 检查是否有重名文件夹
        axios.get(servicePath + "/foldername_path", {
            params: {
                'user_id': user_id,
                'parent': currentPath,
                'foldername': foldername
            }
        })
        .then(function(response){
            // 未重名
            if(response.data != ""){
                createFolderVM.stat = -1;
            }
            // 重名
            else{
                createFolderVM.stat = 1;
            }
        })
        .catch(function(response){
            console.log(response);
            createFolderVM.stat = -2;
        });
    }
}