package com.vecent.ssspeedtest;

import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.model.evaluter.Evaluter4Grade;

import org.junit.Assert;
import org.junit.Test;

public class Evaluter4GradeTest {

    @Test
    public void evaluter4GradeTestHasException() {
        SpeedTestResult mockResult = new SpeedTestResult();
        Evaluter4Grade test = new Evaluter4Grade();
        mockResult.setExceptionOccured(true);
        Assert.assertEquals(0, test.evaluate(mockResult, 0.0f, 0.0f));
    }

    @Test
    public void evaluter4GradeTestWhenUrlHasProity() {
        SpeedTestResult mockResult = new SpeedTestResult();
        Evaluter4Grade test = new Evaluter4Grade();
        mockResult.setRequestServer("http://google.com");
        mockResult.setExceptionOccured(true);
        Assert.assertEquals(0, test.evaluate(mockResult, 0.0f, 0.0f));
        mockResult.setExceptionOccured(false);
        Assert.assertEquals(2, test.evaluate(mockResult, 0.0f, 0.0f));
    }
}
