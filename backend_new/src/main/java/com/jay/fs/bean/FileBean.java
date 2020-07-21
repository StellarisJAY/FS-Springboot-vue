package com.jay.fs.bean;

public class FileBean {
    private int file_id;
    private String filename;
    private String type;
    private int size;
    private FileCreator creator;
    private String createdate;
    private String url;

    public FileBean() {
    }

    public FileBean(int file_id, String filename, String type, int size, FileCreator creator, String createdate, String url) {
        this.file_id = file_id;
        this.filename = filename;
        this.type = type;
        this.size = size;
        this.creator = creator;
        this.createdate = createdate;
        this.url = url;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public FileCreator getCreator() {
        return creator;
    }

    public void setCreator(FileCreator creator) {
        this.creator = creator;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "file_id=" + file_id +
                ", filename='" + filename + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                ", creator=" + creator +
                ", createdate='" + createdate + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
