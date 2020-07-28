const {BrowserWindow, app} = require('electron');
const {ipcMain} = require('electron');
const fs  = require('fs');
const { default: Axios } = require('axios');

/**
 * 创建窗口
 */
function createWindow(){
    let win = new BrowserWindow({
        width: 1066,
        height: 675,
        frame: false,
        resizable: false,
        webPreferences: {
            nodeIntegration: true
        }
    });

    win.loadFile("login.html");
    //win.webContents.openDevTools();
}

/**
 * 创建下载文件的保存路径
 * @param {*} path 
 */
function createPath(path){
    paths = path.split('/');
    paths[0] = paths[0] + '//';

    for(i=2;i<paths.length;i++){
        fs.mkdirSync(paths[0] + paths[i]);
        if(i != paths.length -1){
            paths[0] = paths[0] + paths[i] + '/';
        }
    }
}

/**
 * 删除登陆状态
 */
function logout(){
    // backend not finished yet
}

app.whenReady().then(createWindow);


// 用户关闭窗口，保存用户设置，删除登陆状态
app.on('quit', (event, arg)=>{
    console.log("closing app");
    console.log("end login status");
});

// 下载文件保存请求
ipcMain.on("savefile-request", (event, args)=>{
    fs.exists(args.path, (exist)=>{
        if(exist){
            console.log("[Main]--info: Save file, " + args.file.filename);
            fs.writeFileSync(args.path + '/'+ args.file.filename, args.data);
            event.sender.send("save-file-reply", "success");
        }
        else{
            console.log("[Main]--warning: Path doesn't exist, path=" + args.path);
            createPath(args.path);
            fs.writeFileSync(args.path + '/' + args.file.filename, args.data);
            event.sender.send("save-file-reply", "success");
        }
    })
})


