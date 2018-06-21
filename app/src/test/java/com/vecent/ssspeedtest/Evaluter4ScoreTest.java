package com.vecent.ssspeedtest;

import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.model.evaluter.Evaluter4Score;

import org.junit.Assert;
import org.junit.Test;

//单元测试:  跑分的打分实现类
public class Evaluter4ScoreTest {

    @Test
    public void testOneResultNoException() {
        Evaluter4Score evaluter4Score = new Evaluter4Score();
        SpeedTestResult mockResult = new SpeedTestResult();
        mockResult.setExceptionOccured(false);
        Assert.assertEquals(1, evaluter4Score.evaluate(mockResult, 0.0f, 0.0f), 0.002f);
    }

    @Test
    public void testOneResultHasException() {
        Evaluter4Score evaluter4Score = new Evaluter4Score();
        SpeedTestResult mockResult = new SpeedTestResult();
        mockResult.setExceptionOccured(true);
        Assert.assertEquals(0, evaluter4Score.evaluate(mockResult, 0.0f, 0.0f), 0.002f);
    }
}
