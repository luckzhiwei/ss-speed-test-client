package com.vecent.ssspeedtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.vecent.ssspeedtest.model.Servers;
import com.vecent.ssspeedtest.model.bean.Server;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * Created by lzw on 17-9-22.
 */

public class TestServers {

    private Context appContext;

    @Before
    public void before() {
        this.appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testReadFromAssert() {
        Servers test = new Servers(this.appContext);
        List<Server> ret = test.getServers();
        Assert.assertNotEquals(null, ret);
        Assert.assertEquals(585, ret.size());
    }
}
