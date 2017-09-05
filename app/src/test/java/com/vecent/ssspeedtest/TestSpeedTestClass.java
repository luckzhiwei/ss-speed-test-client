package com.vecent.ssspeedtest;

import com.vecent.ssspeedtest.model.INet;
import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.PingResult;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

/**
 * Created by lzw on 17-9-5.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestSpeedTestClass {

    @Mock
    INet netMock;

    @Test
    public void testSpeedTestPing() {
        ArrayList<String> serversForTest = new ArrayList<>();
        serversForTest.add("taobao.com");
        SpeedTest st = new SpeedTest(serversForTest);
        st.ping(netMock);
        ArrayList<PingResult> ret = st.getPingResult();
        Assert.assertEquals(1, ret.size());
        Assert.assertEquals(0, ret.get(0).getExecRet());
        Assert.assertEquals(56.096, ret.get(0).getTimeMin());
        Assert.assertEquals(61.290, ret.get(0).getTimeMax());


    }

}
