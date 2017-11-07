package com.vecent.ssspeedtest.adpater;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.util.LogUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by zhiwei on 2017/11/7.
 */

public class SSServerAdapter extends CommonAdapter<SSServer> {

    private Context context;

    public SSServerAdapter(Context context, final int layoutId, List<SSServer> data) {
        super(context, layoutId, data);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, SSServer result, int post) {
        TextView serverNameTextView = holder.getView(R.id.ss_server_address);
        TextView serverPortTextView = holder.getView(R.id.ss_server_port);
        TextView serverMethodTextView = holder.getView(R.id.ss_encrpyted_method);
        ImageView editImageView = holder.getView(R.id.edit_setting_imgview);
        editImageView.setOnClickListener(imgClickListener);
        serverMethodTextView.setText(result.getMethod());
        serverPortTextView.setText(result.getServerSort() + "");
        serverNameTextView.setText(result.getServerAddr() + ":");
    }

    private View.OnClickListener imgClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            context.startActivity(new Intent);
        }
    };
}
