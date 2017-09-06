package com.vecent.ssspeedtest.model;

import com.vecent.ssspeedtest.model.bean.PingResult;

/**
 * Created by lzw on 17-9-5.
 */

public interface INet {
    public PingResult ping(String server);
}
