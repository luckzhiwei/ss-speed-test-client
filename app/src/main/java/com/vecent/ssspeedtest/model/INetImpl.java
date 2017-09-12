package com.vecent.ssspeedtest.model;

import com.vecent.ssspeedtest.model.bean.SpeedTestResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lzw on 17-9-5.
 */

public class INetImpl implements INet {

    public SpeedTestResult ping(String server) {
        try {
            SpeedTestResult result = new SpeedTestResult();
            URL url = new URL(server);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
