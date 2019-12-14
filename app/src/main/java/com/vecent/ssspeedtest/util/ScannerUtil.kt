package com.vecent.ssspeedtest.util

import android.app.Activity
import android.content.Intent
import android.util.Base64
import android.util.Log
import com.google.zxing.integration.android.IntentIntegrator
import com.vecent.ssspeedtest.dao.SSServer
import com.vecent.ssspeedtest.view.CaptureActivityPortrait

class ScannerUtil {

    companion object {
        fun JumpToScanAcivity(activity: Activity) {
            val integrator = IntentIntegrator(activity)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            integrator.setPrompt("Scan")
            integrator.setCameraId(0)
            integrator.captureActivity = CaptureActivityPortrait::class.java
            integrator.setBeepEnabled(false)
            integrator.setBarcodeImageEnabled(false)
            integrator.initiateScan()
        }

        fun parseScannerResult(requestCode: Int, resultCode: Int, data: Intent?): SSServer? {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            result?.let {
                val content: String? = result.contents
                if (content == null) {
                    LogUtil.logInfo(Constant.LOG_TAG, "contents is null");
                }
                val b = content?.startsWith("ss://")
                when (b) {
                    true -> {
                        val start = 5
                        var end = 0
                        for (i in start until content.length) {
                            if (content[i] == '#') {
                                end = i
                                break
                            }
                        }
                        var info = content.substring(start, end)
                        info = String(Base64.decode(info, Base64.DEFAULT))
                        return genSSServer(info)
                    }
                    else -> {
                        return null
                    }
                }
            }
            return null
        }

        private fun genSSServer(info: String): SSServer? {
            val arr = info.split("@")
            if (arr.size != 2)
                return null
            val ret = SSServer()
            for (i in 0 until arr.size) {
                val tmp = arr[i].split(":")
                if (tmp.size != 2)
                    return null
                if (i == 0) {
                    ret.method = tmp[0]
                    ret.password = tmp[1]
                } else if (i == arr.size - 1) {
                    ret.serverAddr = tmp[0]
                    ret.serverPort = tmp[1].toInt()
                }
            }
            return ret
        }
    }

}