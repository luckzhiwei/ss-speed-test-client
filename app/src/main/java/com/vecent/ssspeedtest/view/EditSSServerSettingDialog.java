package com.vecent.ssspeedtest.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.greendao.DaoSession;

import java.util.regex.Pattern;

/**
 * Created by zhiwei on 2017/12/20.
 */

public class EditSSServerSettingDialog extends Dialog {


    private EditText ssServerAddrEditText;
    private EditText ssServerRemotePortEditText;
    private EditText ssServerPasswordEditText;
    private EditText ssEncryptMethodEditText;
    private TextView textViewConfirm;
    private TextView textViewCacnel;
    private SSServer mSSServer;
    private OnDialogChange mOnDialogChange;
    private Context mContext;

    public EditSSServerSettingDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public EditSSServerSettingDialog(@NonNull Context context, SSServer server) {
        super(context);
        this.mContext = context;
        this.mSSServer = server;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_ss_server_seting_layout);
        this.setCanceledOnTouchOutside(true);
        initView();
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

    private void setContent() {
        if (this.mSSServer != null) {
            this.ssServerRemotePortEditText.setText(this.mSSServer.getServerPort() + "");
            this.ssServerPasswordEditText.setText(this.mSSServer.getPassword());
            this.ssServerAddrEditText.setText(this.mSSServer.getServerAddr());
            this.ssEncryptMethodEditText.setText(this.mSSServer.getMethod());
            this.textViewCacnel.setText(R.string.delete);
        }
    }

    private void initView() {
        this.ssServerAddrEditText = this.findViewById(R.id.ss_server_address_edit_text);
        this.ssServerRemotePortEditText = this.findViewById(R.id.ss_remote_port_edit_text);
        this.ssServerPasswordEditText = this.findViewById(R.id.ss_server_password_edit_text);
        this.ssEncryptMethodEditText = this.findViewById(R.id.ss_server_encrypt_method_edit_text);
        this.textViewConfirm = this.findViewById(R.id.textview_confirm);
        this.textViewCacnel = this.findViewById(R.id.textview_cancel);
        this.textViewConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vaildation()) {
                    DaoSession daoSession = DaoManager.getInstance(mContext).getDaoSession();
                    if (mSSServer != null) {
                        setServerSetting(mSSServer);
                        daoSession.getSSServerDao().update(mSSServer);
                    } else {
                        SSServer ssServer = new SSServer();
                        setServerSetting(ssServer);
                        daoSession.getSSServerDao().insert(ssServer);
                    }

                    if (mOnDialogChange != null) {
                        mOnDialogChange.onConfirm();
                    }
                    dismiss();
                } else {
                    Toast.makeText(mContext, R.string.input_ss_server_setting_invaild, Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.textViewCacnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnDialogChange != null) {
                    mOnDialogChange.onCacnel(mSSServer);
                }
                dismiss();

            }
        });
        this.setContent();
    }


    public interface OnDialogChange {
        void onConfirm();

        void onCacnel(SSServer server);
    }

    public void setWindowAttr(WindowManager manager) {
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (size.x * 0.8);
        this.getWindow().setAttributes(p);
    }

    private void setServerSetting(SSServer ssServer) {
        ssServer.setServerAddr(ssServerAddrEditText.getText().toString());
        ssServer.setServerPort(Integer.parseInt(ssServerRemotePortEditText.getText().toString()));
        ssServer.setPassword(ssServerPasswordEditText.getText().toString());
        ssServer.setMethod(ssEncryptMethodEditText.getText().toString());
    }

    public void setOnDialogChange(OnDialogChange onDialogChange) {
        this.mOnDialogChange = onDialogChange;
    }


}
