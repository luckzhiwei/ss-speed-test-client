package com.vecent.ssspeedtest.model.evaluter;

import com.vecent.ssspeedtest.model.bean.SpeedTestResult;

public interface Evaluter {
    int evaluate(SpeedTestResult oneResult,
                 float whiteSpeedDownLoadAvg, float blackSpeedDownLoadAvg);
}
