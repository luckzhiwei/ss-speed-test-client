package com.vecent.ssspeedtest

import com.vecent.ssspeedtest.util.ScannerUtil
import junit.framework.Assert.assertEquals
import org.junit.Test

class TestScannerUtil {

    @Test
    fun testparseSSinfoWithEmptyStr() {
        assertEquals("", ScannerUtil.parseSSinfoFromScanCodeResult(""))
    }


}