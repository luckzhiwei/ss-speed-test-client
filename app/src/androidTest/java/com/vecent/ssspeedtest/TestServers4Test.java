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

public class TestServers4Test {

    private Context appContext;

    @Before
    public void before() {
        this.appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testReadGfwListFromAssert() {
        Servers4Test test = new Servers4Test(this.appContext, "gfwlistOutputForTest.json");
        List<Server> ret = test.getServers();
        Assert.assertNotEquals(null, ret);
        Assert.assertEquals(221, ret.size());
        Assert.assertEquals("http://www.2dbook.com", ret.get(0).getWeb());
        Assert.assertEquals("b", ret.get(0).getType());
        Assert.assertEquals("http://www.yakbutterblues.com", ret.get(ret.size() - 3).getWeb());
        Assert.assertEquals("b", ret.get(ret.size() - 3).getType());
    }

    @Test
    public void testReadAlexaListFromAssert() {
        Servers4Test test = new Servers4Test(this.appContext, "alexaForTest.json");
        List<Server> ret = test.getServers();
        Assert.assertNotEquals(null, ret);
        Assert.assertEquals(116, ret.size());
        Assert.assertEquals("http://google.com", ret.get(0).getWeb());
        Assert.assertEquals("b", ret.get(0).getType());
        Assert.assertEquals("http://zhihu.com", ret.get(ret.size() - 1).getWeb());
        Assert.assertEquals("w", ret.get(ret.size() - 1).getType());
    }

}
