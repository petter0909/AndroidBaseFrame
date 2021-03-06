package com.zhangyong.demo.common;

/**
 * Created by ZY on 2017/6/14.
 */

public interface AppGlobal {

    /** 测试环境 false
     * 	正式环境 true
     * 	**/
    boolean RELEASE_VERSION = false ;

    interface BaseConfigs{
        //加载数据页数
        int pagesize = 10;
        //端口号
        String PORT = "";
        String http = "http://";
        //测试正式切换地址
        String serverIP = RELEASE_VERSION?"www.51chawujia.com":"www.51chawujia.com";
        //接口服务器地址
        String baseurlhttp = http+serverIP+PORT+"/dnf/";
    }

    interface BaseApis{

    }

    interface BaseUrls{

    }

}
