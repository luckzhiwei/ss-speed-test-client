// ISpeedTestInterface.aidl
package com.vecent.ssspeedtest.aidl;

import com.vecent.ssspeedtest.aidl.ITestFinishListener;

interface ISpeedTestInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void startTest(in ITestFinishListener listener);
     void stopTest();
     boolean isTestRuning();
     void setAllowTestRunning(in boolean flag);
     boolean getAllowTestRuning();
     void setOnlyWifiTest(in boolean flag);
     boolean getOnlyWifiTest();
     void setOnTestFinishListener(in ITestFinishListener listener);
     void setTimeInterval(long time);
     long getTimeInterval();
}
