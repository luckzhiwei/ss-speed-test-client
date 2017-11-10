package com.vecent.ssspeedtest.model.net;

import com.vecent.ssspeedtest.util.Constant;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;

/**
 * Created by zhiwei on 2017/11/2.
 */

public class INetImplWithProxy extends INetImplDefault {

    @Override
    protected HttpURLConnection getConnection(String server) throws
            MalformedURLException, ProtocolException, IOException {
        SocketAddress addr = InetSocketAddress.createUnresolved(Constant.SOCKS_SERVER_ADDR, Constant.SOCKS_SERVER_PORT);
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr);
        URL url = new URL(server);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setConnectTimeout(Constant.CONNECTION_TIME_OUT);
        conn.setReadTimeout(Constant.READ_TIME_OUT);
        return conn;
    }
}
