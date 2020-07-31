var appVM = new Vue({
    el: '#app',
    data: {
        username: '',
        password: '',
        p_right: true,
        u_exist: true,
        show_spinner: false
    },
    mounted: function () {
        p_right = true;
        u_exist = true;
    },
    methods: {
        login: function () {
            var that = appVM;
            this.p_right = true;
            this.u_exist = true;
            this.show_spinner = true;
            // 发送登录请求，完成后记录token
            request('/login', 'POST', {}, { username: this.username, password: this.password }, 2000)
                .then(response => {
                    let data = response.data.data;
                    // 返回状态1，登录成功
                    if (data.login_status == 1) {
                        // 记录 token 值
                        window.sessionStorage.setItem("token", data.token);
                        // 设置当前路径
                        window.sessionStorage.setItem("currentPath", 1);
                        window.location = "index.html";
                    }
                    else {
                        // 登陆失败
                        // 用户不存在
                        if (data.login_status == 0) {
                            that.u_exist = false;
                        }
                        // 密码错误
                        else {
                            that.p_right = false;
                        }
                    }
                    that.show_spinner = false;
                })
        }
    }
})