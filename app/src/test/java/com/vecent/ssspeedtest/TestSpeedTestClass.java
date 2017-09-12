package com.vecent.ssspeedtest;

import com.vecent.ssspeedtest.model.INet;
import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

/**
 * Created by lzw on 17-9-5.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestSpeedTestClass {

    @Mock
    INet netMock;

    @Before
    public void beforeTest() {
        SpeedTestResult pingResultMockTaobao = new SpeedTestResult();
        pingResultMockTaobao.setExecRetCode(0);
        pingResultMockTaobao.setServerToTest("taobao.com");
        pingResultMockTaobao.setPingRet("4 packets transmitted, 4 received, 0% packet loss, time 3005ms\n" +
                "rtt min/avg/max/mdev = 42.885/48.727/55.846/4.635 ms\n");
        SpeedTestResult pingResultGoogle = new SpeedTestResult();
        pingResultGoogle.setExecRetCode(1);
        pingResultGoogle.setServerToTest("www.google.com.hk");
        Mockito.when(netMock.ping("taobao.com")).thenReturn(pingResultMockTaobao);
        Mockito.when(netMock.ping("www.google.com.hk")).thenReturn(pingResultGoogle);
    }

    @Test
    public void testSpeedTestPingWhiteAddress() {
        ArrayList<String> serversForTest = new ArrayList<>();
        serversForTest.add("taobao.com");
        SpeedTest st = new SpeedTest(serversForTest);
        SpeedTestResult ret = st.ping(netMock, serversForTest.get(0));
        Assert.assertEquals(0, ret.getExecRetCode());
        Assert.assertEquals(4, ret.getTotalPackets());
        Assert.assertEquals(4, ret.getReceivedPackets());
        Assert.assertEquals(42.885f, ret.getTimeMin(), 0.002f);
        Assert.assertEquals(55.846f, ret.getTimeMax(), 0.002f);
        Assert.assertEquals(48.727f, ret.getTimeAvg(), 0.002f);
        Assert.assertEquals("taobao.com", ret.getServerToTest());
    }

    @Test
    public void testSpeedTestPingBlockedAddr() {
        ArrayList<String> serversForTest = new ArrayList<>();
        serversForTest.add("www.google.com.hk");
        SpeedTest st = new SpeedTest(serversForTest);
        SpeedTestResult ret = st.ping(netMock, serversForTest.get(0));
        Assert.assertEquals(1, ret.getExecRetCode());
        Assert.assertEquals("www.google.com.hk", ret.getServerToTest());
    }

}
