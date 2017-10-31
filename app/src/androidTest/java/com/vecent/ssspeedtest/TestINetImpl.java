package com.vecent.ssspeedtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.vecent.ssspeedtest.model.INet;
import com.vecent.ssspeedtest.model.INetImpl;
import com.vecent.ssspeedtest.model.bean.Server;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by zhiwei on 2017/9/6.
 */

public class TestINetImpl {

    private Context appContext;

    @Before
    public void before() {
        this.appContext = InstrumentationRegistry.getTargetContext();
    }


    @Test
    public void testINetImplHttpRequestWhiteListAddr() {
        INet iNetForTest = new INetImpl();
        Server test = new Server();
        test.setType("w");
        test.setWeb("http://taobao.com/");
        SpeedTestResult result = iNetForTest.getHttpTestResult(test);

        Assert.assertEquals(false, result.isExceptionOccured());
        Assert.assertEquals(true, result.isRedirect());
        Assert.assertEquals(false, result.isTimedOut());
        Assert.assertEquals(false, result.isUrlWrong());
        Assert.assertEquals(200, result.getStatusCode());
        Assert.assertEquals(true, result.getTotalSize() > 0);
        Assert.assertEquals(true, result.getTimeUsed() > 0);
        Assert.assertEquals(true, result.getDownLoadSpeed() > 0);
        Assert.assertEquals(false, result.is2ManyTimeRelocation());

    }

    @Test
    public void testINetImplPingBlackListAddr() {
        INet iNetForTest = new INetImpl();
        Server test = new Server();
        test.setType("w");
        test.setWeb("https://www.google.com.hk");
        SpeedTestResult result = iNetForTest.getHttpTestResult(test);

        Assert.assertEquals(true, result.isExceptionOccured());
        Assert.assertEquals(false, result.isRedirect());
        Assert.assertEquals(true, result.isTimedOut());
        Assert.assertEquals(false, result.isUrlWrong());

        Assert.assertEquals(0, result.getTotalSize());
        Assert.assertEquals(0, result.getTimeUsed());
        Assert.assertEquals(0, result.getDownLoadSpeed(), 0.00f);
        Assert.assertEquals(0, result.getStatusCode());
    }


    @Test
    public void testINetImplHtpRequestRedirectAddr() {
        INet iNetForTest = new INetImpl();
        Server test = new Server();
        test.setType("w");
        test.setWeb("http://www.baidu.com");
        SpeedTestResult result = iNetForTest.getHttpTestResult(test);

        Assert.assertEquals(false, result.isExceptionOccured());
        Assert.assertEquals(true, result.isRedirect());
        Assert.assertEquals(false, result.isTimedOut());
        Assert.assertEquals(false, result.isUrlWrong());

        Assert.assertEquals(200, result.getStatusCode());
        Assert.assertEquals(true, result.getTotalSize() > 0);
        Assert.assertEquals(true, result.getTimeUsed() > 0);
        Assert.assertEquals(true, result.getDownLoadSpeed() > 0);

    }
}
