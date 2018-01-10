package com.vecent.ssspeedtest.util;


/**
 * Created by zhiwei on 2017/9/4.
 */
public class Constant {

    public static final String LOG_TAG = "speedTest";
    public static final int TIME_TO_KEPP_ALIVE = 1;
    public static final int MAX_REDIRECT_TIMES = 5;
    public static final int CONNECTION_TIME_OUT = 4000;
    public static final int READ_TIME_OUT = 4000;
    public static final int RESLOVE_DNS_TIME_OUT = 10000;
    public static final int TOTAL_TIME_OUT = 8000;
    public static final String LOCAL_HOST = "127.0.0.1";
    public static final int SOCKS_SERVER_LOCAL_PORT_FONT = 1088;
    public static final int PRIVOXY_LOCAL_PORT_FONT = 8118;
    public static final int SOCKS_SERVER_LOCAL_PORT_BACK = 1089;
    public static final int PRIVOXY_LOCAL_PORT_BACK = 8119;
    public static final String BACK_PRIVOXY_CONFIG_FILE_NAME = "configBackGround";
    public static final String FRONT_PRIVOXY_CONFIG_FILE_NAME = "configFrontGround";
    public static final int MIN_THREAD_POOL_SIZE = 50;
    public static final int MAX_THREAD_POOL_SIZE = 100;
    public static final String SYSTEM_PROXY = "SYSTEM_PROXY";
    public static final String ABOUT_URL = "https://github.com/luckzhiwei/ss-speed-test-client/blob/master/.github/aboud.md";
    public static final int WAIT_PROCESS_TIME_OUT = 5 * 1000;
    public static long TWO_MIN = 2 * 60 * 1000;
    public static long FIFEEN_MIN = 15 * 60 * 1000;
    public static long THIRTY_MIN = 30 * 60 * 1000;
    public static long SIXTY_MIN = 60 * 60 * 1000;
    public static long THREE_HOUR = 3 * 60 * 60 * 1000;
    public static long SIX_HOUR = 6 * 60 * 60 * 1000;
    public static long ONE_DAY = 24 * 60 * 60 * 1000;
    public static String[] ENCRYPTED_METHODS = {"rc4-md5", "aes-128-cfb", "aes-192-cfb", "aes-256-cfb", "aes-128-ctr", "aes-192-ctr", "aes-256-ctr",
            "bf-cfb", "camellia-128-cfb", "camellia-192-cfb", "camellia-256-cfb", "salsa20", "chacha20", "chacha20-ietf", "chacha20-ietf-poly1305",
            "xchacha20-ietf-poly1305", "aes-128-gcm", "aes-192-gcm", "aes-256-gcm"};
}
