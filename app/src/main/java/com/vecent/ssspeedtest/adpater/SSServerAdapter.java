package com.vecent.ssspeedtest.adpater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vecent.ssspeedtest.MainActivity;
import com.vecent.ssspeedtest.R;

import com.vecent.ssspeedtest.controller.SpeedTestActivity;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.util.LogUtil;
import com.vecent.ssspeedtest.view.EditSSServerSettingDialog;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by zhiwei on 2017/11/7.
 */

public class SSServerAdapter extends CommonAdapter<SSServer> {


    private EditSSServerSettingDialog.OnDialogChange mOnDialogChangeListener;

    public SSServerAdapter(Context context, final int layoutId, List<SSServer> data, EditSSServerSettingDialog.OnDialogChange onDialogChangeListener) {
        super(context, layoutId, data);
        this.mOnDialogChangeListener = onDialogChangeListener;
    }

    @Override
    public void convert(ViewHolder holder, final SSServer server, final int pos) {
        setContentData(holder, server, pos);
    }

    public void updatView(View view, SSServer server, int pos) {
        Object viewHolder = view.getTag();
        if (viewHolder instanceof ViewHolder) {
            this.setContentData((ViewHolder) viewHolder, server, pos);
        }
    }

    private void setContentData(ViewHolder holder, final SSServer server, final int pos) {
        TextView serverNameTextView = holder.getView(R.id.texview_server_info);
        ImageView imgViewSpeedText = holder.getView(R.id.img_speed_test);
        imgViewSpeedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSpeedTest(server, pos);
            }
        });
        if (server.isSystemProxy()) {
            serverNameTextView.setText(mContext.getText(R.string.system_proxy));
        } else {
            serverNameTextView.setText(server.getServerAddr() + ":" + server.getServerPort());
            ImageView editImageView = holder.getView(R.id.img_edit);
            editImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToEdit(server, pos);
                }
            });
        }
        setScoreContent(holder, server);
        setGradeViewContent(holder, server);
    }


    private void goSpeedTest(SSServer server, int pos) {
        Intent intent = new Intent();
        if (server != null) {
            intent.putExtra("ssServer", server);
        }
        intent.putExtra("pos", pos);
        intent.setClass(mContext, SpeedTestActivity.class);
        if (mContext instanceof MainActivity) {
            MainActivity activity = (MainActivity) mContext;
            activity.startActivityForResult(intent, MainActivity.REQUEST_CODE);
        }
    }

    private void goToEdit(SSServer server, int pos) {
        EditSSServerSettingDialog dialog = new EditSSServerSettingDialog(mContext, server, pos);
        dialog.setOnDialogChange(mOnDialogChangeListener);
        dialog.show();
        dialog.setWindowAttr(((Activity) mContext).getWindowManager());
    }


    private void setScoreContent(ViewHolder holder, SSServer server) {
        TextView serverScore = holder.getView(R.id.textview_ss_score);
        if (server.getScore() != -1) {
            serverScore.setText(mContext.getResources().getText(R.string.score) + " " + server.getScore());
        } else {
            serverScore.setText(R.string.score);
        }
    }

    private void setGradeViewContent(ViewHolder holder, SSServer server) {
        TextView serverGrade = holder.getView(R.id.textview_ss_grade);
        TextView serverGradeValue = holder.getView(R.id.textview_ss_grade_value);
        TextView serverStatus = holder.getView(R.id.textview_ss_status);
        TextView serverStatusValue = holder.getView(R.id.textview_ss_status_value);
        if (server.getGrade() == -1) {
            serverGrade.setVisibility(View.INVISIBLE);
            serverStatus.setVisibility(View.INVISIBLE);
            serverGradeValue.setVisibility(View.INVISIBLE);
            serverStatusValue.setVisibility(View.INVISIBLE);
        } else {
            serverGrade.setVisibility(View.VISIBLE);
            serverStatus.setVisibility(View.VISIBLE);
            serverGradeValue.setVisibility(View.VISIBLE);
            serverStatusValue.setVisibility(View.VISIBLE);
            serverGradeValue.setText(server.getGrade() + "");
            this.setStatueByGrade(serverStatusValue, server.getGrade());
        }
    }


    private void setStatueByGrade(TextView textView, int grade) {
        if (grade >= 85) {
            textView.setText(R.string.status_good);
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorGreen));
        } else if (grade < 85 && grade >= 60) {
            textView.setText(R.string.status_normal);
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorYellow));
        } else {
            textView.setText(R.string.status_bad);
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
        }

    }


}
