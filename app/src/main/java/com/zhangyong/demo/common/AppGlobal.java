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
        String PORT = ":9002";
        String http = "http://";
        String https = "https://";
        //测试正式切换地址
        String serverIP = RELEASE_VERSION?"api.yiqiquxiang.com":"api.yiqiquxiang.com";
        //接口服务器地址
        String baseurlhttp = http+serverIP+PORT+"/api/";
        String baseurlhttps = https+serverIP+PORT+"/api/";
    }

    interface BaseApis{

    }

    interface BaseUrls{

    }

}
