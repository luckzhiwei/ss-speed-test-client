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

    }

    @Test
    public void testSpeedTestPingWhiteAddress() {
        ArrayList<String> serversForTest = new ArrayList<>();
        serversForTest.add("taobao.com");
        SpeedTest st = new SpeedTest(serversForTest);
    }

    @Test
    public void testSpeedTestPingBlockedAddr() {
        ArrayList<String> serversForTest = new ArrayList<>();
        serversForTest.add("www.google.com.hk");
        SpeedTest st = new SpeedTest(serversForTest);
        SpeedTestResult ret = st.ping(netMock, serversForTest.get(0));
    }

}
