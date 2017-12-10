package com.vecent.ssspeedtest.model.net;

import com.vecent.ssspeedtest.util.Constant;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

/**
 * Created by zhiwei on 2017/12/8.
 */

public class INetImplWithPrivoxy extends INetImplWithProxy {

    public INetImplWithPrivoxy(int port) {
        super(port);
    }

    @Override
    protected Proxy getProxy() {
        SocketAddress addr = InetSocketAddress.createUnresolved(Constant.LOCAL_HOST, proxyPort);
        return new Proxy(Proxy.Type.HTTP, addr);
    }
}
