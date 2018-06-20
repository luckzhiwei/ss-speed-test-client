package com.vecent.ssspeedtest;

import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.model.evaluter.Evaluter4Grade;
import com.vecent.ssspeedtest.model.evaluter.Evaluter4Score;

import org.junit.Assert;
import org.junit.Test;

public class Evaluter4GradeTest {

    @Test
    public void evaluter4GradeTestHasException() {
        SpeedTestResult mockResult = new SpeedTestResult();
        Evaluter4Grade test = new Evaluter4Grade();
        mockResult.setExceptionOccured(true);
        Assert.assertEquals(0, test.evaluate(mockResult, 0.0f, 0.0f), 0.002f);
    }

    @Test
    public void evaluter4GradeTestWhenUrlHasProity() {
        SpeedTestResult mockResult = new SpeedTestResult();
        Evaluter4Grade test = new Evaluter4Grade();
        mockResult.setRequestServer("http://google.com");
        mockResult.setExceptionOccured(true);
        Assert.assertEquals(0, test.evaluate(mockResult, 0.0f, 0.0f), 0.002f);
        mockResult.setExceptionOccured(false);
        Assert.assertEquals(2, test.evaluate(mockResult, 0.0f, 0.0f), 0.002f);
    }

    @Test
    public void evaluter4GradeTestWhenWhiteAddrNoException() {
        SpeedTestResult mockResult = new SpeedTestResult();
        Evaluter4Grade test = new Evaluter4Grade();
        mockResult.setRequestServer("http://comcast.net");
        mockResult.setWhiteAddr(true);
        mockResult.setExceptionOccured(false);
        mockResult.setDownLoadSpeed(10.0f);
        Assert.assertEquals(1.5f, test.evaluate(mockResult, 20.0f, 0.0f), 0.002f);
        Assert.assertEquals(1.5f, test.evaluate(mockResult, 20.0f, 30.0f), 0.002f);
        mockResult.setDownLoadSpeed(30.0f);
        Assert.assertEquals(2, test.evaluate(mockResult, 20.0f, 0.0f), 0.002f);
        Assert.assertEquals(2, test.evaluate(mockResult, 20.0f, 30.0f), 0.002f);
        mockResult.setDownLoadSpeed(20.0f);
        Assert.assertEquals(2, test.evaluate(mockResult, 20.0f, 30.0f), 0.002f);

    }

    @Test
    public void evaluter4GradeTestWhenBlackAddrNoException() {
        SpeedTestResult mockResult = new SpeedTestResult();
        Evaluter4Grade test = new Evaluter4Grade();
        mockResult.setRequestServer("http://comcast.net");
        mockResult.setWhiteAddr(false);
        mockResult.setExceptionOccured(false);
        mockResult.setDownLoadSpeed(10.0f);
        Assert.assertEquals(1.5f, test.evaluate(mockResult, 20.0f, 20.0f), 0.002f);
        Assert.assertEquals(1.5f, test.evaluate(mockResult, 0.0f, 20.0f), 0.002f);
        mockResult.setDownLoadSpeed(30.0f);
        Assert.assertEquals(2, test.evaluate(mockResult, 20.0f, 20.0f), 0.002f);
        Assert.assertEquals(2, test.evaluate(mockResult, 0.0f, 20.0f), 0.002f);
        mockResult.setDownLoadSpeed(20.0f);
        Assert.assertEquals(2, test.evaluate(mockResult, 0.0f, 20.0f), 0.002f);
    }
}
