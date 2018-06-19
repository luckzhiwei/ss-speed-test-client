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
    }

    @Override
    public int evaluate(SpeedTestResult oneResult, float whiteSpeedDownLoadAvg, float blackSpeedDownLoadAvg) {
        if (oneResult.isExceptionOccured())
            return 0;
        else {
            int sum = 1;
            if (set.contains(oneResult.getRequestServer()))
                sum++;
            else {

            }
            return sum;
        }
    }
}
