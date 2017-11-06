package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.vecent.ssspeedtest.R;

/**
 * Created by zhiwei on 2017/11/6.
 */

public class InputSSServerSettingActivity extends Activity {

    private EditText ssServerAddrEditText;
    private EditText ssServerRemotePortEditText;
    private EditText ssServerLocalPortEditText;
    private EditText ssServerPasswordEditText;
    private EditText ssEncryptMethodEditText;

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
    }



}
