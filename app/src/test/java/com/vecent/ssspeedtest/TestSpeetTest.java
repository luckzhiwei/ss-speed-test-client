package com.vecent.ssspeedtest;

import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.Server;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzw on 17-9-25.
 */

public class TestSpeetTest {

    private static List<SpeedTestResult> mockResult;
    private static List<Server> mockServer=new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        mockResult = new ArrayList<>();
        SpeedTestResult whiteLisAddrForTest = new SpeedTestResult();
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
    public void testWhiteListConnectionRate() {
        SpeedTest test = new SpeedTest(mockServer);
        Assert.assertEquals(0.5f, test.calConnectRateWhiteList(mockResult), 0.002f);
    }

    @Test
    public void testBlackListConnectionRate() {
        SpeedTest test = new SpeedTest(mockServer);
        Assert.assertEquals(1.0f, test.calConnectRateBalckList(mockResult), 0.002f);
    }

    @Test
    public void testCountWhiteListAddr() {
        SpeedTest test = new SpeedTest(mockServer);
        Assert.assertEquals(2, test.countWhiteListAddr(mockResult));
    }

    @Test
    public void testCountBlackListAddr() {
        SpeedTest test = new SpeedTest(mockServer);
        Assert.assertEquals(1, test.countBlackListAddr(mockResult));
    }

    @Test
    public void testCountDownSpeed() {
        SpeedTest test = new SpeedTest(mockServer);
        Assert.assertEquals(20.0f, test.countSpeedAvg(mockResult), 0.002f);
    }
}
