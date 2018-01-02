// ITestFinishListener.aidl
package com.vecent.ssspeedtest.aidl;

// Declare any non-default types here with import statements
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;
interface ITestFinishListener {

    void onTestFinish(boolean isRealRunning);
    void onOneItemFinish(in long id,in int grade);
    void onTestStart();
}
