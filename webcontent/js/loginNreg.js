var servicePath = "http://127.0.0.1:8001";

function login(){
    var username = appVM.username;
    var password = appVM.password;

    axios.get(servicePath + "/username", {
        params:{
            "username": username
        }
    }).then(function(response){
        if(response.data != null){ // 用户存在
            if(response.data.password == password){      // 密码正确
                // 将用户保存在页面session中
                var session = window.sessionStorage;
                session.setItem("user", JSON.stringify(response.data));
                // 设置当前目录为根目录
                session.setItem("currentPath", '1');
                // 跳转到用户主页
                window.location="index.html";
            }
            else{                   // 密码错误
                alert("密码错误");
            }
        }
        else{                         // 用户不存在
            alert("用户不存在");
        }
    }).catch(function(response){

    })
}
/**
 * md5加密输入的密码，然后与数据库读取的密码进行匹配
 * @param {*} password 
 */
function encodePassword(password){
    return password;
}