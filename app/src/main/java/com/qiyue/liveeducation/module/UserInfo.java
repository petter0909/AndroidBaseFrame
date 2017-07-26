package com.qiyue.liveeducation.module;

/**
 * Created by ZY on 2017/6/15.
 */

public class UserInfo {
    public String id;
    public String facepath;
    public String username;
    public String nick;

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", facepath='" + facepath + '\'' +
                ", username='" + username + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }
}
