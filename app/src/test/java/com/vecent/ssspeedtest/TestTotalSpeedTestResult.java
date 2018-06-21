package com.vecent.ssspeedtest;

import android.os.Handler;

import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.Server;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;
import com.vecent.ssspeedtest.model.evaluter.Evaluter4Score;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzw on 17-9-25.
 */

public class TestTotalSpeedTestResult {

    private static List<SpeedTestResult> mockResult;

    @BeforeClass
    public static void beforeClass() {
        mockResult = new ArrayList<>();
        SpeedTestResult whiteLisAddrForTest = new SpeedTestResult();
        whiteLisAddrForTest.setWhiteAddr(true);
        whiteLisAddrForTest.setExceptionOccured(true);
        whiteLisAddrForTest.setDownLoadSpeed(0.0f);
        mockResult.add(whiteLisAddrForTest);
        whiteLisAddrForTest = new SpeedTestResult();
        whiteLisAddrForTest.setWhiteAddr(true);
        whiteLisAddrForTest.setExceptionOccured(false);
        whiteLisAddrForTest.setDownLoadSpeed(10.0f);
        mockResult.add(whiteLisAddrForTest);
        whiteLisAddrForTest = new SpeedTestResult();
        whiteLisAddrForTest.setWhiteAddr(true);
        whiteLisAddrForTest.setExceptionOccured(true);
        mockResult.add(whiteLisAddrForTest);
        whiteLisAddrForTest = new SpeedTestResult();
        whiteLisAddrForTest.setDownLoadSpeed(30.0f);
        whiteLisAddrForTest.setWhiteAddr(false);
        whiteLisAddrForTest.setExceptionOccured(false);
        mockResult.add(whiteLisAddrForTest);
    }

    @Test
    public void testStatResult() {
        TotalSpeedTestResult resultTotal = new TotalSpeedTestResult(new Evaluter4Score());
        Assert.assertEquals(0, resultTotal.getSpeedWhiteAddrDownLoadAvg(), 0.002);
        Assert.assertEquals(0, resultTotal.getWhiteAddrConnectSuccesRate(), 0.002);
        Assert.assertEquals(0, resultTotal.getBlackAddrConnectSuccesRate(), 0.002f);
        Assert.assertEquals(0, resultTotal.getSpeedBlackAddrDownLoadAvg(), 0.002f);
        Assert.assertEquals(0, resultTotal.getWhiteAddrServerCount());
        Assert.assertEquals(0, resultTotal.getBlackAddrServerCount());
        Assert.assertEquals(0, resultTotal.getCurServerCount());
        Assert.assertEquals(0, resultTotal.getResultScore());
        resultTotal.addResult2List(mockResult.get(0));
        Assert.assertEquals(0, resultTotal.getSpeedWhiteAddrDownLoadAvg(), 0.002);
        Assert.assertEquals(0, resultTotal.getWhiteAddrConnectSuccesRate(), 0.002);
        Assert.assertEquals(0, resultTotal.getBlackAddrConnectSuccesRate(), 0.002f);
        Assert.assertEquals(0, resultTotal.getSpeedBlackAddrDownLoadAvg(), 0.002f);
        Assert.assertEquals(1, resultTotal.getWhiteAddrServerCount());
        Assert.assertEquals(0, resultTotal.getBlackAddrServerCount());
        Assert.assertEquals(1, resultTotal.getCurServerCount());
        Assert.assertEquals(0, resultTotal.getResultScore());
        resultTotal.addResult2List(mockResult.get(1));
        Assert.assertEquals(10.0f, resultTotal.getSpeedWhiteAddrDownLoadAvg(), 0.002);
        Assert.assertEquals(0.5, resultTotal.getWhiteAddrConnectSuccesRate(), 0.002);
        Assert.assertEquals(0, resultTotal.getBlackAddrConnectSuccesRate(), 0.002f);
        Assert.assertEquals(0, resultTotal.getSpeedBlackAddrDownLoadAvg(), 0.002f);
        Assert.assertEquals(2, resultTotal.getWhiteAddrServerCount());
        Assert.assertEquals(0, resultTotal.getBlackAddrServerCount());
        Assert.assertEquals(2, resultTotal.getCurServerCount());
        Assert.assertEquals(1, resultTotal.getResultScore());
        resultTotal.addResult2List(mockResult.get(2));
        Assert.assertEquals(10.0f, resultTotal.getSpeedWhiteAddrDownLoadAvg(), 0.002);
        Assert.assertEquals(0.33333334, resultTotal.getWhiteAddrConnectSuccesRate(), 0.002);
        Assert.assertEquals(0, resultTotal.getBlackAddrConnectSuccesRate(), 0.002f);
        Assert.assertEquals(0, resultTotal.getSpeedBlackAddrDownLoadAvg(), 0.002f);
        Assert.assertEquals(3, resultTotal.getWhiteAddrServerCount());
        Assert.assertEquals(0, resultTotal.getBlackAddrServerCount());
        Assert.assertEquals(3, resultTotal.getCurServerCount());
        Assert.assertEquals(1, resultTotal.getResultScore());
        resultTotal.addResult2List(mockResult.get(3));
        Assert.assertEquals(10.0f, resultTotal.getSpeedWhiteAddrDownLoadAvg(), 0.002);
        Assert.assertEquals(0.33333334, resultTotal.getWhiteAddrConnectSuccesRate(), 0.002);
        Assert.assertEquals(1.0f, resultTotal.getBlackAddrConnectSuccesRate(), 0.002f);
        Assert.assertEquals(30.0f, resultTotal.getSpeedBlackAddrDownLoadAvg(), 0.002f);
        Assert.assertEquals(3, resultTotal.getWhiteAddrServerCount());
        Assert.assertEquals(1, resultTotal.getBlackAddrServerCount());
        Assert.assertEquals(4, resultTotal.getCurServerCount());
        Assert.assertEquals(2, resultTotal.getResultScore());
    }


}
