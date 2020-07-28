var fs = require("fs");

export function saveFile(path, fileContent){
    fs.writeFile(path, fileContent);
}