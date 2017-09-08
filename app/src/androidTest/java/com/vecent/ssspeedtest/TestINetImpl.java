package com.vecent.ssspeedtest;

import com.vecent.ssspeedtest.model.INet;
import com.vecent.ssspeedtest.model.INetImpl;
import com.vecent.ssspeedtest.model.bean.PingResult;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhiwei on 2017/9/6.
 */

public class TestINetImpl {

    @Test
    public void testINetImplPingWhiteListAddr() {
        INet iNetForTest = new INetImpl();
        PingResult result = iNetForTest.ping("taobao.com");
        Assert.assertEquals(0, result.getExecRet());
        Assert.assertNotNull(result.getPingRet());
    }

    @Test
    public void testINetImplPingBlackListAddr() {
        INet iNetForTest = new INetImpl();
        PingResult result = iNetForTest.ping("www.google.com.hk");
        Assert.assertEquals(1, result.getExecRet());
        Assert.assertNull(result.getPingRet());
    }
}
