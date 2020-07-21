var appVM = new Vue({
    el: '#app',
    data: {
        username: '',
        password: '',
        p_right: true,
        u_exist: true
    },
    mounted: function () {
        p_right = true;
        u_exist = true;
    },
    methods: {
        login: function () {
            var that = this;
            this.p_right = true;
            this.u_exist = true;
            // 发送登录请求，完成后记录token
            request('/login', 'POST', {}, {username: this.username, password: this.password}, 2000)
            .then(response=>{
                if(response.data.status == 1){
                    // 返回状态1，登录成功
                    if (response.data.status == 1) {
                        // 记录 token 值
                        window.sessionStorage.setItem("token", response.data.token);
                        // 设置当前路径
                        window.sessionStorage.setItem("currentPath", 1);
                        window.location = "index.html";
                    }
                    else {
                        // 登陆失败
                        // 用户不存在
                        if (response.data.status == -1) {
                            that.u_exist = false;
                        }
                        // 密码错误
                        else {
                            that.p_right = false;
                        }
                    }
                }
            })

            /*axios.request({
                url: servicePath + '/login',
                method: 'post',
                dataType: 'json',
                params: {
                    'username': this.username,
                    'password': this.password
                }
            })
                .then(function (response) {
                    // 返回状态1，登录成功
                    if (response.data.status == 1) {
                        // 记录 token 值
                        window.sessionStorage.setItem("token", response.data.token);
                        // 设置当前路径
                        window.sessionStorage.setItem("currentPath", 1);
                        window.location = "index.html";
                    }
                    else {
                        // 登陆失败
                        // 用户不存在
                        if (response.data.status == -1) {
                            that.u_exist = false;
                            document.getElementsByName("body").focus();
                        }
                        // 密码错误
                        else {
                            that.p_right = false;
                            document.getElementById("password").focus();
                        }
                    }
                })
                .catch(function (error) {
                    console.log(error);
                })*/
        }
    }
})