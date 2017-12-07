package com.vecent.ssspeedtest.util;


/**
 * Created by zhiwei on 2017/9/4.
 */
public class Constant {

    public static final String LOG_TAG = "speedTest";
    public static final int TIME_TO_KEPP_ALIVE = 1;
    public static final int MAX_REDIRECT_TIMES = 20;
    public static final int CONNECTION_TIME_OUT = 10000;
    public static final int READ_TIME_OUT = 10000;
    public static final String SOCKS_SERVER_LOCAL_ADDR = "127.0.0.1";
    public static final int SOCKS_SERVER_LOCAL_PORT_FONT = 1088;
    public static final int PRIVOXY_LOCAL_PORT_FONT = 8118;
    public static final int SOCKS_SERVER_LOCAL_PORT_BACK = 1089;
    public static final int PRIVOXY_LOCAL_PORT_BACK = 8119;
    public static final String BACK_PRIVOXY_CONFIG_FILE_NAME = "configBackGround";
    public static final String FRONT_PRIVOXY_CONFIG_FILE_NAME = "configFrontGround";
    public static final int MIN_THREAD_POOL_SIZE = 100;
    public static final int MAX_THREAD_POOL_SIZE = 100;
    public static final int SERVICE_WAIT_INTERNAL = 2 * 60 * 1000;


}
