const { default: Axios } = require("axios");

var appVM = new Vue({
    el: '#app',
    data: {
        used_space: 0,   // 已使用空间大小
        max_space: 0,    // 最大空间大小
        percent: '',     // 已用空间百分比
        files: [],       // 当前路径所有文件
        displaymode: 1,  // 显示方式
        currentPath: 1,  // 当前路径
        path_stack: [1], // 路径栈

        download_queue: [], // 下载任务队列
        selected_list: [],  // 选中文件列表
        upload_stack: []    // 上传栈（暂时未使用）
    },
    /**
     * 页面加载过程：
     * 1、验证登陆状态
     * 2、获取基本信息：已用容量、最大可用容量
     * 3、初始化根目录
     * 4、加载根目录文件
     */
    mounted: function () {
        // 验证登陆状态
        if (window.sessionStorage.getItem('token') == null) {
            alert("你还没有登陆");
            window.location = "login.html";
        }
        var that = this;
        that.getMaxSpace();
        that.getUsedSpace();
        // 初始化当前路径
        that.currentPath = window.sessionStorage.getItem("currentPath");    
        // 加载根目录文件
        that.getFilesAtCurrentPath();
    },
    methods: {
        /**
         * 获取当前目录下的所有文件
         */
        getFilesAtCurrentPath: function () {
            var that = this;
            // 发送文件获取请求
            request('/user_files', 'GET', {token: window.sessionStorage.getItem("token")}, {path: this.currentPath}, 2000)
            .then(resp=>{
                that.files = resp.data;
            });
        },

        /**
         * 获取已使用容量
         */
        getUsedSpace: function(){
            var that = this;
            request('/user/used_space', 'GET', {token: window.sessionStorage.getItem("token")}, null, 2000)
            .then(response=>{
                that.used_space = response.data;
            }); 
        },

        /**
         * 获取最大容量
         */
        getMaxSpace: function(){
            var that = this;
            request('/user/max_space', 'GET', {token: window.sessionStorage.getItem('token')}, null, 2000)
            .then(response=>{
                that.max_space = response.data;
            });
        },

        /**
         * 进入文件夹
         * @param {*} path 文件夹id 
         */
        enterFolder: function (path) {
            this.currentPath = path; // 记录当前路径
            window.sessionStorage.setItem("currentPath", path);

            this.path_stack.push(path);// 当前路径入栈
            this.getFilesAtCurrentPath();
        },
        
        /**
         * 返回上级目录：
         * 1、当前页面出栈（路径栈记录了用户进入的路径层级关系）
         * 2、设置当前目录为栈顶路径
         * 3、加载当前目录内容
         */
        toUpperPath: function () {
            // 当前路径出栈
            this.path_stack.pop();
            // 现在的栈顶元素即为上级路径
            this.currentPath = this.path_stack[this.path_stack.length - 1];
            window.sessionStorage.setItem("currentPath", this.currentPath);

            // 加载文件夹内容
            this.getFilesAtCurrentPath();
        },

        /**
         * 回到根目录：
         * 1、清空路径栈
         * 2、设置当前路径为1，保存到sessionStorage
         * 3、加载根目录内容
         */
        toRootPath: function () {
            // 清空路径栈
            this.path_stack = [1];
            // 更改当前路径
            this.currentPath = 1;
            window.sessionStorage.setItem("currentPath", 1);

            // 加载路径内容
            this.getFilesAtCurrentPath();
        },

        /**
         * 删除文件夹：
         * 发送删除请求，请求完成后重新加载当前目录
         */
        deleteFolder: (folder_id)=>{
            var c = confirm("删除该文件夹会同时删除文件夹内容，是否确定删除该文件夹？");
            if(c == false)return;
            request("/folder", "DELETE", {token: window.sessionStorage.getItem("token")}, {folder_id: folder_id}, 2000)
            .then(response=>{
                appVM.getFilesAtCurrentPath();
            });
        },

        /**
         * 更新已选列表：
         * 判断参数文件是否在已选列表，如果在就删除，否则添加
         * @param {} file 
         */
        changeSelectedList: function(file){
            var i = 0;
            // 判断是否已经在选择队列，如果是表示现在是删除操作
            for(i =0; i < this.selected_list.length; i++){
                let f = this.selected_list[i];
                if(f.file_id == file.file_id){
                    this.selected_list.splice(i, 1);
                    return 1;
                }
                i++;
            }
            // 不在队列中，添加
            this.selected_list.push({file_id: file.file_id, filename: file.filename, size: file.size, type: file.type, progress: 0});
        },

        /**
         * 开始下载，初始化下载队列
         * 
         * ！ ！ ！ 下载队列开始下载入口方法 ！ ！ ！
         */
        startMultiDownload: function(){
            // 把选中的文件装入下载队列
            this.download_queue = this.selected_list.concat();
            // 开始递归下载过程
            this.download(this.getNextDownload().file_id);
        },

        /**
         * 获取下一个文件，同时出队已完成文件
         */ 
        getNextDownload: function(){
            return (this.download_queue.shift());
        },

        /**
         * 递归下载队列，逐个完成下载请求：
         * 1、发送下载请求
         * 2、请求完成（接收文件完成），储存文件到本地文件系统（未完工）
         * 3、写入完成，判断下载队列是否还有未下载文件
         * 4、如果有：递归调用download，下载下一个文件
         * @param {*} file_id 
         */ 
        download: function(file_id){
            var that = this;
            // 下载请求，当请求完成后再判断队列是否已完成，未完成就递归调用本身下载下一个
            request("/download/id", "GET", {token: window.sessionStorage.getItem("token")}, {file_id: file_id}, 120000000)
            .then(response=>{
                console.log(response);
                // 获取队列下一个文件
                var next = that.getNextDownload();
                if(next != null){
                    that.download(next.file_id);
                }
            });
        },

        /**
         * 更新使用空间比例
         */ 
        updateUsedSpacePercent: function(){
            this.percent = Math.ceil((this.used_space*100/this.max_space)) + '%';
            return this.percent;
        },

        /**
         * 大小显示单位换算
         * @param {*} value 
         */
        displaySize: function(value){
            if(value < 1024)
                return value + 'byte';
            else if(value < 1024 * 1024)
                return Math.round(value/1024) + 'KB';
            else
                return Math.round(value/(1024*1024)) + 'MB';
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
        /**
         * 检查文件夹是否重名
         */
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
        /**
         * 创建文件夹
         */
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
        /**
         * 检查上传的文件
         */
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
        /**
         *  上传单个文件
         */
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