package com.vecent.ssspeedtest.model.privoxy;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by zhiwei on 2017/12/8.
 */

public class PrivoxySetting {

    private Context context;

    public PrivoxySetting(Context context) {
        this.context = context;
    }

    private File checkExternalFolderIsExist() {
        File root = Environment.getExternalStorageDirectory();
        File etcPrioxyFolder = new File(root, "/etc/privoxy");
        if (!etcPrioxyFolder.exists()) {
            etcPrioxyFolder.mkdirs();
        }
        return etcPrioxyFolder;
    }


    private void copyAssertFolderToTarget(String src, File des) {
        AssetManager manager = this.context.getAssets();
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


    public void install() {
        copyAssertFolderToTarget("privoxy/etc", checkExternalFolderIsExist());
        copyAssertFolderToTarget("privoxy/bin", this.context.getFilesDir());
    }
}
