package com.vecent.ssspeedtest;

import android.os.Handler;

import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.Server;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;

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
    private static List<Server> mockServer = new ArrayList<>();

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



}
