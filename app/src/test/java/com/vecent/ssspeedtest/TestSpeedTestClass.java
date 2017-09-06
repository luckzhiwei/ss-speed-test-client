package com.vecent.ssspeedtest;

import com.vecent.ssspeedtest.model.INet;
import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.PingResult;

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
        PingResult pingResultMock = new PingResult();
        pingResultMock.setExecRet(0);
        pingResultMock.setPingRet("4 packets transmitted, 4 received, 0% packet loss, time 3005ms\n" +
                "rtt min/avg/max/mdev = 42.885/48.727/55.846/4.635 ms\n");
        Mockito.when(netMock.ping("taobao.com")).thenReturn(pingResultMock);
    }

    @Test
    public void testSpeedTestPing() {
        ArrayList<String> serversForTest = new ArrayList<>();
        serversForTest.add("taobao.com");
        SpeedTest st = new SpeedTest(serversForTest);
        ArrayList<PingResult> ret = st.ping(netMock);
        Assert.assertEquals(1, ret.size());
        Assert.assertEquals(0, ret.get(0).getExecRet());
        Assert.assertEquals(42.885f, ret.get(0).getTimeMin(), 0.002f);
        Assert.assertEquals(55.846f, ret.get(0).getTimeMax(), 0.002f);
        Assert.assertEquals(48.727f, ret.get(0).getTimeAvg(), 0.002f);
    }

}
