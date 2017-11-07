package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.greendao.DaoSession;

/**
 * Created by zhiwei on 2017/11/6.
 */

public class InputSSServerSettingActivity extends Activity {

    private EditText ssServerAddrEditText;
    private EditText ssServerRemotePortEditText;
    private EditText ssServerLocalPortEditText;
    private EditText ssServerPasswordEditText;
    private EditText ssEncryptMethodEditText;
    private Button ensureSettingBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.input_ss_server_seting_layout);
        initView();
    }

    private void initView() {
        this.ssServerAddrEditText = this.findViewById(R.id.ss_server_address_edit_text);
        this.ssServerRemotePortEditText = this.findViewById(R.id.ss_remote_port_edit_text);
        this.ssServerLocalPortEditText = this.findViewById(R.id.ss_local_port_edit_text);
        this.ssServerPasswordEditText = this.findViewById(R.id.ss_server_password_edit_text);
        this.ssEncryptMethodEditText = this.findViewById(R.id.ss_server_encrypt_method_edit_text);
        this.ensureSettingBtn = this.findViewById(R.id.store_setting_btn);
        this.ensureSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SSServer ssServer = new SSServer();
                ssServer.setServerAddr(ssServerAddrEditText.getText().toString());
                ssServer.setServerSort(Integer.parseInt(ssServerRemotePortEditText.getText().toString()));
                ssServer.setPassword(ssServerPasswordEditText.getText().toString());
                ssServer.setMethod(ssEncryptMethodEditText.getText().toString());
                DaoSession daoSession = DaoManager.getInstance(getApplicationContext()).getDaoSession();
                daoSession.getSSServerDao().insert(ssServer);
            }
        });

    }


}
