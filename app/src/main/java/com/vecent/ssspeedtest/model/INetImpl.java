package com.vecent.ssspeedtest.model;

import com.vecent.ssspeedtest.model.bean.PingResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lzw on 17-9-5.
 */

public class INetImpl implements INet {

    public PingResult ping(String server) {
        try {
            String cmd = "/system/bin/ping -c 4 -W 5 ";
            Process p = Runtime.getRuntime().exec(cmd + server);
            int status = p.waitFor();
            PingResult ret = new PingResult();
            ret.setExecRet(status);
            ret.setServerToTest(server);
            if (status == 0) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = "";
                int i = 0;
                StringBuilder tmp = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    i++;
                    if (i > 7) {
                        tmp.append(line + "\n");
                    }
                }
                ret.setPingRet(tmp.toString());
            }
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
