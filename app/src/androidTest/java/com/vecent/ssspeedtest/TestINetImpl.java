package com.vecent.ssspeedtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.vecent.ssspeedtest.model.INet;
import com.vecent.ssspeedtest.model.INetImpl;
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

    //todo 有像taobao.com这种跳转多次的情况如何处理？
    @Test
    public void testINetImplHttpRequestWhiteListAddr() {
        INet iNetForTest = new INetImpl();
        SpeedTestResult result = iNetForTest.getHttpTestResult("http://m.taobao.com/?sprefer=sypc00");

        Assert.assertEquals(false, result.isExceptionOccured());
        Assert.assertEquals(true, result.isRedirect());
        Assert.assertEquals(false, result.isTimedOut());
        Assert.assertEquals(false, result.isUrlWrong());
        Assert.assertEquals(200, result.getStatusCode());
        Assert.assertEquals(true, result.getTotalSize() > 0);
        Assert.assertEquals(true, result.getTimeUsed() > 0);
        Assert.assertEquals(true, result.getDownLoadSpeed() > 0);

    }

    @Test
    public void testINetImplPingBlackListAddr() {
        INet iNetForTest = new INetImpl();
        SpeedTestResult result = iNetForTest.getHttpTestResult("https://www.google.com.hk");

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
        SpeedTestResult result = iNetForTest.getHttpTestResult("http://www.baidu.com");

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
