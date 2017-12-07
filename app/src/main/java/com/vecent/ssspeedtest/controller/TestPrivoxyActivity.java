package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.model.guradprocess.PrivoxyGuradProcess;
import com.vecent.ssspeedtest.model.guradprocess.SSProxyGuradProcess;
import com.vecent.ssspeedtest.util.Constant;
import com.vecent.ssspeedtest.util.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by zhiwei on 2017/12/5.
 */

public class TestPrivoxyActivity extends Activity {

    private static final int REQUEST_WRITE_STORAGE = 112;
    private PrivoxyGuradProcess privoxyGuradProcess;
    private SSProxyGuradProcess ssProxyGuradProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        SSServer ssServer = new SSServer();
        ssServer.setServerAddr("118.184.57.38");
        ssServer.setPassword("esecb1307");
        ssServer.setServerPort(47066);
        ssServer.setMethod("aes-256-cfb");
        ssProxyGuradProcess = new SSProxyGuradProcess(ssServer, this, Constant.SOCKS_SERVER_LOCAL_PORT_FONT);
        ssProxyGuradProcess.start();
        privoxyGuradProcess = new PrivoxyGuradProcess(this, Constant.FRONT_PRIVOXY_CONFIG_FILE_NAME);
        privoxyGuradProcess.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SocketAddress addr = InetSocketAddress.createUnresolved("127.0.0.1", Constant.PRIVOXY_LOCAL_PORT_FONT);
                    Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
                    URL url = new URL("https://www.google.com.hk/?hl=zh-cn");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String buf = "";
                    final StringBuffer stringBuffer = new StringBuffer();
                    while ((buf = bufferedReader.readLine()) != null) {
                        stringBuffer.append(buf);
                    }
                    bufferedReader.close();
                    LogUtil.logDebug(getClass().getName(), stringBuffer.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    LogUtil.logDebug(getClass().getName(), e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.logDebug(getClass().getName(), e.getMessage() + "io exception");
                }
            }
        }).start();
    }

    private File checkFolderIsExist() {
        File root = Environment.getExternalStorageDirectory();
        File etcPrioxyFolder = new File(root, "/etc/privoxy");
        if (!etcPrioxyFolder.exists()) {
            etcPrioxyFolder.mkdirs();
        }
        return etcPrioxyFolder;
    }


    private void copyAssertFolderToTarget(String src, File des) {
        AssetManager manager = getAssets();
        try {
            String[] files = manager.list(src);
            if (files != null) {
                for (String file : files) {
                    File targetFile = new File(des, file);
                    if (targetFile.exists()) {
                        continue;
                    }
                    InputStream in = null;
                    OutputStream out = null;
                    in = manager.open(src + "/" + file);
                    out = new FileOutputStream(targetFile);
                    copy(in, out);
                    out.close();
                    in.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                    WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                ActivityCompat.requestPermissions(this,
                        new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
            } else {
                copyAssertFolderToTarget("privoxy/etc", checkFolderIsExist());
                copyAssertFolderToTarget("privoxy/bin", getFilesDir());
            }
        } else {
            copyAssertFolderToTarget("privoxy/etc", checkFolderIsExist());
            copyAssertFolderToTarget("privoxy/bin", getFilesDir());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    copyAssertFolderToTarget("privoxy/etc", checkFolderIsExist());
                    copyAssertFolderToTarget("privoxy/bin", getFilesDir());
                } else {
                    Toast.makeText(this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        privoxyGuradProcess.destory();
        ssProxyGuradProcess.destory();
    }


}
