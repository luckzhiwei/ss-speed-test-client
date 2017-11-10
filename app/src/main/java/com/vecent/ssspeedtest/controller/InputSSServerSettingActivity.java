package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.greendao.DaoSession;
import com.vecent.ssspeedtest.util.LogUtil;

import java.util.regex.Pattern;

/**
 * Created by zhiwei on 2017/11/6.
 */

public class InputSSServerSettingActivity extends Activity {

    private EditText ssServerAddrEditText;
    private EditText ssServerRemotePortEditText;
    private EditText ssServerPasswordEditText;
    private EditText ssEncryptMethodEditText;
    private Button ensureSettingBtn;
    private long serverId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.input_ss_server_seting_layout);
        initView();
        initData();
    }

    private void initView() {
        this.ssServerAddrEditText = this.findViewById(R.id.ss_server_address_edit_text);
        this.ssServerRemotePortEditText = this.findViewById(R.id.ss_remote_port_edit_text);
        this.ssServerPasswordEditText = this.findViewById(R.id.ss_server_password_edit_text);
        this.ssEncryptMethodEditText = this.findViewById(R.id.ss_server_encrypt_method_edit_text);
        this.ensureSettingBtn = this.findViewById(R.id.store_setting_btn);
        this.ensureSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vaildation()) {
                    SSServer ssServer = new SSServer();
                    ssServer.setServerAddr(ssServerAddrEditText.getText().toString());
                    ssServer.setServerPort(Integer.parseInt(ssServerRemotePortEditText.getText().toString()));
                    ssServer.setPassword(ssServerPasswordEditText.getText().toString());
                    ssServer.setMethod(ssEncryptMethodEditText.getText().toString());
                    DaoSession daoSession = DaoManager.getInstance(getApplicationContext()).getDaoSession();
                    if (serverId == -1) {
                        daoSession.getSSServerDao().insert(ssServer);
                    } else {
                        ssServer.setId(serverId);
                        daoSession.getSSServerDao().update(ssServer);
                    }
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.input_ss_server_setting_invaild, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean vaildation() {
        if (ssEncryptMethodEditText.getText().toString() == null) {
            return false;
        }
        if (ssServerPasswordEditText.getText().toString() == null) {
            return false;
        }
        if (ssEncryptMethodEditText.getText().toString() == null) {
            return false;
        }
        String port = ssServerRemotePortEditText.getText().toString();
        if (port == null) {
            return false;
        }
        String regex = "[0-9]+";
        if (!Pattern.matches(regex, port)) {
            return false;
        }
        int num = Integer.parseInt(port);
        if (num < 0 || num > 65535) {
            return false;
        }
        return true;
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.ssServerAddrEditText.setText(bundle.getString("serverAddrName"));
            this.ssServerRemotePortEditText.setText(bundle.getInt("serverPort", 0) + "");
            this.ssEncryptMethodEditText.setText(bundle.getString("serverMethod"));
            this.ssServerPasswordEditText.setText(bundle.getString("serverPassword"));
            this.serverId = bundle.getLong("ssserverId");
        }
    }


}
