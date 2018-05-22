package com.vecent.ssspeedtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.model.SSServers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by zhiwei on 2018/5/18.
 */

public class TestSSServers {

    private Context appContext = InstrumentationRegistry.getTargetContext();
    SSServers test = new SSServers(this.appContext);
    private Thread thread = Thread.currentThread();

    private SSServers.OnDataChangedListener listener = new SSServers.OnDataChangedListener() {
        @Override
        public void onDataInit() {

        }

        @Override
        public void onDataRemove(int pos) {

        }

        @Override
        public void onDataAdd() {

        }

        @Override
        public void onDataUpdate(int pos, SSServer server) {

        }

        @Override
        public void onAllDataUpdate() {

        }

        @Override
        public void onFininshForUnitTest() {
            LockSupport.unpark(thread);
        }
    };

    @Before
    public void before() {
        test.setOnDataChangedListener(listener);
    }

    private void addServer() throws InterruptedException {
        SSServer serverAdd = new SSServer();
        serverAdd.setMethod("aes-256-cfb");
        serverAdd.setPassword("esecb1307");
        serverAdd.setServerAddr("118.184.57.38");
        serverAdd.setServerPort(47066);
        test.add(serverAdd);
        LockSupport.park();
        Assert.assertEquals(1, test.getData().size());
    }

    @Test
    public void testSSServerAddAndRemove() throws InterruptedException {
        addServer();
        test.remove(0, test.getData().get(0));
        LockSupport.park();
        Assert.assertEquals(0, test.getData().size());
    }

    @Test
    public void testSSServerAddAndUpdate() throws InterruptedException {
        addServer();
        SSServer server2Update = test.getData().get(0);
        server2Update.setServerPort(47067);
        server2Update.setServerAddr("uestc.edu.cn");
        server2Update.setMethod("aes-128-cfb");
        server2Update.setPassword("123");
        test.update(0, server2Update);
        LockSupport.park();
        Assert.assertEquals(47067, test.getData().get(0).getServerPort());
        Assert.assertEquals("123", test.getData().get(0).getPassword());
        Assert.assertEquals("aes-128-cfb", test.getData().get(0).getMethod());
        Assert.assertEquals("uestc.edu.cn", test.getData().get(0).getServerAddr());
    }

    @Test
    public void testClearAllServerGrade() throws InterruptedException {
        addServer();
        test.clearAllGrade();
        LockSupport.park();
        Assert.assertEquals(-1, test.getData().get(0).getGrade());
    }

    @Test
    public void testUpdateGrade() throws InterruptedException {
        addServer();
        test.updateServerGrade(test.getData().get(0).getId(), 92);
        LockSupport.park();
        Assert.assertEquals(92, test.getData().get(0).getGrade());
    }

    @Test
    public void testUpdateScore() throws InterruptedException {
        addServer();
        test.updateServerScore(0, 211);
        LockSupport.park();
        Assert.assertEquals(211, test.getData().get(0).getScore());
    }


}
