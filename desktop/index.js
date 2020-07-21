const {BrowserWindow, app} = require('electron');

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

app.whenReady().then(createWindow);
