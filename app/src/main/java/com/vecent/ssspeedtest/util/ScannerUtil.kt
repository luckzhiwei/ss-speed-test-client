package com.vecent.ssspeedtest.util

import android.app.Activity
import android.content.Intent
import android.util.Base64
import com.google.zxing.integration.android.IntentIntegrator
import com.vecent.ssspeedtest.dao.SSServer
import com.vecent.ssspeedtest.view.CaptureActivityPortrait


class ScannerUtil {

    companion object {
        fun jumpToScanAcivity(activity: Activity) {
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
                    LogUtil.logInfo(Constant.LOG_TAG, "contents is null")
                } else {
                    LogUtil.logInfo(Constant.LOG_TAG, "content is $content")
                }
                when (content?.startsWith("ss://")) {
                    true -> {
                        val info = parseSSinfo(content)
                        if (info.isEmpty()) {
                            return null
                        }
                        return genSSServer(info)
                    }
                    else -> {
                        return null
                    }
                }
            }
            return null
        }

        private fun parseSSinfo(content: String): String {
            val start = 5
            var end = 0
            var hasUnwrapper = false
            for (i in start until content.length) {
                if (content[i] == '#' || content[i] == '?' || content[i] == '/') {
                    end = i
                    break
                }
                if (content[i] == '@') hasUnwrapper = true
            }
            if (end == content.length)
                return ""

            var info = content.substring(start, end)
            if (hasUnwrapper) {
                var arr = info.split("@")
                return String(Base64.decode(arr[0], Base64.DEFAULT)) + "@" + arr[1]
            }
            return String(Base64.decode(info, Base64.DEFAULT))
        }

        private fun genSSServer(info: String): SSServer? {
            LogUtil.logInfo(Constant.LOG_TAG, "the info is $info")
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