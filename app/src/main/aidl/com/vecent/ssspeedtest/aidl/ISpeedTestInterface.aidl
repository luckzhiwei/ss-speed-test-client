// ISpeedTestInterface.aidl
package com.vecent.ssspeedtest.aidl;

import com.vecent.ssspeedtest.aidl.ITestFinishListener;

interface ISpeedTestInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void startTest();
     void stopTest();
     boolean isTestRuning();
     void setOnTestFinishListener(in ITestFinishListener listener);
}
