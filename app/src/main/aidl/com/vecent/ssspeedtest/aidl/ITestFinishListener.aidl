// ITestFinishListener.aidl
package com.vecent.ssspeedtest.aidl;

// Declare any non-default types here with import statements
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;
interface ITestFinishListener {

    void onTestFinish(in List<TotalSpeedTestResult> results);
}
