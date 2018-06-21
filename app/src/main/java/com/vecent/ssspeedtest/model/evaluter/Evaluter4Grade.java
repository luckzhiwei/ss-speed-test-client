package com.vecent.ssspeedtest.model.evaluter;

import com.vecent.ssspeedtest.model.bean.SpeedTestResult;

import java.util.HashSet;

public class Evaluter4Grade implements Evaluter {

    public static HashSet<String> set;

    static {
        set = new HashSet<>();
        set.add("http://google.com");
        set.add("http://taobao.com");
        set.add("http://qq.com");
        set.add("http://baidu.com");
        set.add("http://youtube.com");
        set.add("http://360.cn");
        set.add("http://facebook.com");
        set.add("http://amazon.com");
        set.add("http://yahoo.com");
        set.add("http://wikipedia.org");
        set.add("http://twitter.com");
        set.add("http://ebay.com");
        set.add("http://linkedin.com");
        set.add("http://netflix.com");
    }

    @Override
    public float evaluate(SpeedTestResult oneResult, float whiteSpeedDownLoadAvg, float blackSpeedDownLoadAvg) {
        if (oneResult.isExceptionOccured())
            return 0.0f;
        else {
            float sum = 1.5f;
            if (set.contains(oneResult.getRequestServer()))
                sum += 0.5f;
            else {
                if (oneResult.isWhiteAddr()) {
                    if (oneResult.getDownLoadSpeed() >= whiteSpeedDownLoadAvg)
                        sum += 0.5f;
                } else {
                    if (oneResult.getDownLoadSpeed() >= blackSpeedDownLoadAvg)
                        sum += 0.5f;
                }
            }
            return sum;
        }
    }
}
