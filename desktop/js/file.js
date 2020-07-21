var appVM = new Vue({
    el: '#app',
    data: {
        used_space: '',  // 已使用空间大小
        files: [],       // 当前路径所有文件
        displaymode: 1,  // 显示方式
        currentPath: 1,  // 当前路径
        path_stack: [1], // 路径栈
    },
    // 页面加载
    mounted: function () {
        // 验证登陆状态
        if (window.sessionStorage.getItem('token') == null) {
            alert("你还没有登陆");
            window.location = "login.html";
        }
        // 初始化当前路径
        this.currentPath = window.sessionStorage.getItem("currentPath");
        // 加载根目录文件
        this.getFilesAtCurrentPath();
    },
    methods: {
        // 获取当前路径下的所有文件
        getFilesAtCurrentPath: function () {
            var that = this;
            // 发送文件获取请求
            request('/user_files', 'GET', {token: window.sessionStorage.getItem("token")}, {path: this.currentPath}, 2000)
            .then(resp=>{
                that.files = resp.data;
            });
        },
        // 进入文件夹
        enterFolder: function (path) {
            this.currentPath = path; // 记录当前路径
            window.sessionStorage.setItem("currentPath", path);

            this.path_stack.push(path);// 当前路径入栈
            this.getFilesAtCurrentPath();
        },

        toUpperPath: function () {
            // 当前路径出栈
            this.path_stack.pop();
            // 现在的栈顶元素即为上级路径
            this.currentPath = this.path_stack[this.path_stack.length - 1];
            window.sessionStorage.setItem("currentPath", this.currentPath);

            // 加载文件夹内容
            this.getFilesAtCurrentPath();
        },
        toRootPath: function () {
            // 清空路径栈
            this.path_stack = [1];
            // 更改当前路径
            this.currentPath = 1;
            window.sessionStorage.setItem("currentPath", 1);

            // 加载路径内容
            this.getFilesAtCurrentPath();
        }
    }
});

var createFolderVM = new Vue({
    el: "#createFolder",
    data: {
        nameValid: true,
        nameDuplicate: false,
        name: ''
    },
    methods: {
        checkName: function () {
            this.nameValid = true;
            this.nameDuplicate = false;
            // 检查名称长度
            if (this.name == '' || this.name.length > 20) {
                this.nameDuplicate = false;
                this.nameValid = false;
            }
            // 检查重名
            else {
                this.nameValid = true;
                var that = this;
                // 发送重名检查请求
                request("/folder/check/name", "GET", {token:window.sessionStorage.getItem("token")}, 
                {name:this.name, path:window.sessionStorage.getItem("currentPath")}, 2000)
                .then(data=>{
                    console.log(data);
                    if(data.data == 1){
                        that.nameDuplicate = true;
                    }
                    else{
                        that.nameDuplicate = false;
                        this.createFolder();
                    }
                });
            }
        },
        createFolder: function(){
            var that = this;
            // 发送创建文件夹请求
            request("/folder", "POST", {token:window.sessionStorage.getItem('token')}, 
            {foldername: this.name, path: window.sessionStorage.getItem("currentPath")}, 2000)
            .then(response=>{
                // 请求完成，重新加载当前目录所有文件
                appVM.getFilesAtCurrentPath();
                // 隐藏模态框
                $("#createFolder").modal('hide');
            });
        }
    }
})