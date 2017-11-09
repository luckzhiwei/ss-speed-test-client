package com.vecent.ssspeedtest.adpater;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.controller.InputSSServerSettingActivity;
import com.vecent.ssspeedtest.dao.SSServer;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by zhiwei on 2017/11/7.
 */

public class SSServerAdapter extends CommonAdapter<SSServer> {


    public SSServerAdapter(Context context, final int layoutId, List<SSServer> data) {
        super(context, layoutId, data);
    }

    @Override
    public void convert(ViewHolder holder, final SSServer server, int post) {
        TextView serverNameTextView = holder.getView(R.id.ss_server_address);
        TextView serverPortTextView = holder.getView(R.id.ss_server_port);
        TextView serverMethodTextView = holder.getView(R.id.ss_encrpyted_method);
        ImageView editImageView = holder.getView(R.id.edit_setting_imgview);
        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEdit(server);
            }
        });
        serverMethodTextView.setText(server.getMethod());
        serverPortTextView.setText(server.getServerPort() + "");
        serverNameTextView.setText(server.getServerAddr() + ":");
    }

    private void goToEdit(SSServer server) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("serverAddrName", server.getServerAddr());
        bundle.putInt("serverPort", server.getServerPort());
        bundle.putString("serverMethod", server.getMethod());
        bundle.putString("serverPassword", server.getPassword());
        bundle.putLong("ssserverId", server.getId());
        intent.putExtras(bundle);
        intent.setClass(mContext, InputSSServerSettingActivity.class);
        mContext.startActivity(intent);
    }

}
