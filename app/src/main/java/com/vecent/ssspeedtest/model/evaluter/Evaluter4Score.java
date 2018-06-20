package com.vecent.ssspeedtest.model.evaluter;

import com.vecent.ssspeedtest.model.bean.SpeedTestResult;

public class Evaluter4Score implements Evaluter {
    @Override
    public float evaluate(SpeedTestResult oneResult, float whiteSpeedDownLoadAvg, float blackSpeedDownLoadAvg) {
        return oneResult.isExceptionOccured() ? 0 : 1;
    }
}
