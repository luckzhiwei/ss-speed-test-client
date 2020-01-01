package com.vecent.ssspeedtest

import android.content.Context
import android.support.test.InstrumentationRegistry
import com.vecent.ssspeedtest.util.ScannerUtil
import junit.framework.Assert
import org.junit.Before
import org.junit.Test

class TestScannerUtil {


    private var appContext: Context? = null

    @Before
    fun before() {
        this.appContext = InstrumentationRegistry.getTargetContext()
    }

    @Test
    fun testparseSSinfoWithEmptyStr() {
        Assert.assertEquals("", ScannerUtil.parseSSinfo(""))
    }

    @Test
    fun testparseSSinfFromMacOSClient() {
        val content = "ss://YWVzLTI1Ni1jZmI6ZXNlY2I=@118.184.57.38:443/?#Hong%2520Kong%25201,%2520%25E6%25B5%2581%25E9%2587%258F%25E5%2580%258D%25E7%258E%2587:1.00"
        Assert.assertEquals("aes-256-cfb:esecb@118.184.57.38:443", ScannerUtil.parseSSinfo(content))
    }

    @Test
    fun testparseSSinfFromShaowsockscom() {
        val content = "ss://YWVzLTI1Ni1jZmI6ZXNlY2JAMTE4LjE4NC41Ny4zODo0NDM=#Australia%201,%20%E6%B5%81%E9%87%8F%E5%80%8D%E7%8E%87:1.00"
        Assert.assertEquals("aes-256-cfb:esecb@118.184.57.38:443", ScannerUtil.parseSSinfo(content))
    }

    @Test
    fun testparseSSinfFromOtherSSFromat() {
        val content = "ss://YWVzLTI1Ni1jZmI6ZXNlY2JAMTE4LjE4NC41Ny4zODo0NDM=?tfo=1"
        Assert.assertEquals("aes-256-cfb:esecb@118.184.57.38:443", ScannerUtil.parseSSinfo(content))
    }

    @Test
    fun testparseSSinfoFromNotSSFormat() {
        var content = "eGNoYWNoYTIwLWlldGYtcG9seTEzMDU6bHh4eHhAdW5yZWUuY2M6NDQz?tfo=1"
        Assert.assertEquals("", ScannerUtil.parseSSinfo(content))
        content = "agaebgauiega"
        Assert.assertEquals("", ScannerUtil.parseSSinfo(content))
        content = "eGNoYWNoYTIwLWlldGYtcG9seTEzMDU6bHh4eHhAdW5yZWUuY2M6"
        Assert.assertEquals("", ScannerUtil.parseSSinfo(content))
    }

    //默认没有后缀不合法
    @Test
    fun testparseSSinfoWithoutPostfix() {
        var content = "ss://YWVzLTI1Ni1jZmI6ZXNlY2JAMTE4LjE4NC41Ny4zODo0NDM="
        Assert.assertEquals("", ScannerUtil.parseSSinfo(content))
    }

    @Test
    fun testGenSSserver() {
        val content = "aes-256-cfb:esecb@118.184.57.38:443"
        val ssServer = ScannerUtil.genSSServer(content)
        Assert.assertEquals("aes-256-cfb", ssServer?.method)
        Assert.assertEquals(443, ssServer?.serverPort)
        Assert.assertEquals("118.184.57.38", ssServer?.serverAddr)
        Assert.assertEquals("esecb", ssServer?.password)
    }

    @Test
    fun testGenSSserverWithEmptyStr() {
        val content = ""
        val ssServer = ScannerUtil.genSSServer(content)
        Assert.assertEquals(null, ssServer)
    }


}