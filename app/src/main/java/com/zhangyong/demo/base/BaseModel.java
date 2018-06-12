package com.zhangyong.demo.base;

import com.google.gson.annotations.SerializedName;

/**
 * 数据请求返回数据基类
 */
public class BaseModel<E> {

    @SerializedName("status")
    public int status ;  //  200成功,  110异常，
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private E data;

    public boolean isSuccess() {
        return status == 200;
    }

    public String getMessage() {
        return message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
