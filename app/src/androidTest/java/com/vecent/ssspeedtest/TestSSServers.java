package com.vecent.ssspeedtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.model.SSServers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhiwei on 2018/5/18.
 */

public class TestSSServers {

    private Context appContext = InstrumentationRegistry.getTargetContext();
    SSServers test = new SSServers(this.appContext);
    private ReentrantLock lock = new ReentrantLock(true);

    private SSServers.OnDataChangedListener listener = new SSServers.OnDataChangedListener() {
        @Override
        public void onDataInit() {

        }

        @Override
        public void onDataRemove(int pos) {

        }

        @Override
        public void onDataAdd() {
            lock.unlock();
            Assert.assertEquals(1, test.getData().size());
        }

        @Override
        public void onDataUpdate(int pos, SSServer server) {

        }

        @Override
        public void onAllDataUpdate() {

        }
    };

    @Before
    public void before() {
        test.setOnDataChangedListener(listener);
    }

    @Test
    public void testSSServerAdd() {
        SSServer serverAdd = new SSServer();
        serverAdd.setMethod("aes-256-cfb");
        serverAdd.setPassword("esecb1307");
        serverAdd.setServerAddr("118.184.57.38");
        serverAdd.setServerPort(47066);
        test.add(serverAdd);
        lock.lock();
    }
}
