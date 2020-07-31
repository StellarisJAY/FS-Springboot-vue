package com.jay.fs.common;

import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class CommonResult {
    private int status;     // 返回状态值
    private String message; // 返回信息
    private Map<String, Object> data; // 返回的可选数据

    public CommonResult() {
    }

    public CommonResult(int status, String message) {
        this.status = status;
        this.message = message;
        data = new HashMap<>();
    }

    public CommonResult(int status, String message, Map<String, Object> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static CommonResult success(String message){
        return new CommonResult(ResultCode.SUCCESS, message);
    }

    public static CommonResult error(String message){
        return new CommonResult(ResultCode.ERROR, message);
    }

    public static CommonResult fail(String message){
        return new CommonResult(ResultCode.FAIL, message);
    }

    public CommonResult addDataItem(String name, Object value){
        if(data.containsKey(name)==false){
            data.put(name, value);
        }
        return this;
    }
    public CommonResult addDataItem(String name, Object value, boolean overwrite){
        if(data.containsKey(name)){
            if(overwrite) data.replace(name, value);
        }
        else{
            data.put(name, value);
        }
        return this;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
