package com.vecent.ssspeedtest;

import com.vecent.ssspeedtest.model.INet;
import com.vecent.ssspeedtest.model.INetImpl;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhiwei on 2017/9/6.
 */

public class TestINetImpl {

    @Test
    public void testINetImplPingWhiteListAddr() {
        INet iNetForTest = new INetImpl();
        SpeedTestResult result = iNetForTest.httpRequest("taobao.com");
    }

    @Test
    public void testINetImplPingBlackListAddr() {
        INet iNetForTest = new INetImpl();
        SpeedTestResult result = iNetForTest.httpRequest("www.google.com.hk");
    }
}
