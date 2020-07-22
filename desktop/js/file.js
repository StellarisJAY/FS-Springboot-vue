const { default: Axios } = require("axios");

var appVM = new Vue({
    el: '#app',
    data: {
        used_space: 0,  // 已使用空间大小
        max_space: 0,
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

        this.getMaxSpace();
        this.getUsedSpace();
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

        getUsedSpace: function(){
            var that = this;
            request('/user/used_space', 'GET', {token: window.sessionStorage.getItem("token")}, null, 2000)
            .then(response=>{
                that.used_space = response.data;
            }); 
        },

        getMaxSpace: function(){
            var that = this;
            request('/user/max_space', 'GET', {token: window.sessionStorage.getItem('token')}, null, 2000)
            .then(response=>{
                that.max_space = response.data;
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
        },
        deleteFolder: (folder_id)=>{
            var c = confirm("删除该文件夹会同时删除文件夹内容，是否确定删除该文件夹？");
            if(c == false)return;
            request("/folder", "DELETE", {token: window.sessionStorage.getItem("token")}, {folder_id: folder_id}, 2000)
            .then(response=>{
                appVM.getFilesAtCurrentPath();
            });
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


var uploadVM = new Vue({
    el: "#uploadModal",
    data:{
        fileTooBig: false,
        upload_file: null,
    },
    methods: {
        // 上传文件检查
        checkUploadFile: function(){
            var file = event.target.files[0];
            // 文件大小过大
            if(file.size + appVM.used_space > appVM.max_space){
                this.fileTooBig = true;
            }
            else{
                var formData = new FormData();
                formData.append('file', file); // formdata添加文件
                formData.append('path', window.sessionStorage.getItem('currentPath')); // 添加上传路径
                this.upload_file = formData;
                this.fileTooBig = false;
            }
        },
        // 上传文件
        uploadFile: function(){
            if(this.fileTooBig == false){
                let config = {
                    headers: {
                      'Content-Type': false,   // form-data不需要设置contenttype
                      'token': window.sessionStorage.getItem('token') // token
                    }
                  };
                // 发送上传文件请求
                request_post("/file/upload", this.upload_file, config)
                .then(response=>{
                    if(response.data.status == '1'){
                        appVM.getFilesAtCurrentPath(); // 上传成功，刷新当前目录
                        appVM.getUsedSpace();     // 刷新已用空间
                        $("#uploadModal").modal('hide'); // 隐藏上传modal
                    }
                })
            }
        }
    }
})