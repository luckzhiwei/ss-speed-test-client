package com.vecent.ssspeedtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.vecent.ssspeedtest.model.Servers4Test;
import com.vecent.ssspeedtest.model.bean.Server;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        Servers4Test test = new Servers4Test(this.appContext,"gfwlistOutput.txt");
        List<Server> ret = test.getServers();
        Assert.assertNotEquals(null, ret);
        Assert.assertEquals(40, ret.size());
        Assert.assertEquals("http://www.2dbook.com", ret.get(0).getWeb());
        Assert.assertEquals("b", ret.get(0).getType());
        Assert.assertEquals("http://www.youdao.com", ret.get(ret.size() - 3).getWeb());
        Assert.assertEquals("w", ret.get(ret.size() - 3).getType());
    }
}
