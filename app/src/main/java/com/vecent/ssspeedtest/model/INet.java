package com.vecent.ssspeedtest.model;

import com.vecent.ssspeedtest.model.bean.Server;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;

/**
 * Created by lzw on 17-9-5.
 */

public interface INet {
    public SpeedTestResult getHttpTestResult(Server server);
}
