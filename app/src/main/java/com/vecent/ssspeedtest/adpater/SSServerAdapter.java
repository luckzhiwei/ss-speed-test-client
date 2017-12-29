package com.vecent.ssspeedtest.adpater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vecent.ssspeedtest.MainActivity;
import com.vecent.ssspeedtest.R;

import com.vecent.ssspeedtest.controller.SpeedTestActivity;
import com.vecent.ssspeedtest.dao.SSServer;
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
                    goToEdit(server);
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

    private void goToEdit(SSServer server) {
        EditSSServerSettingDialog dialog = new EditSSServerSettingDialog(mContext, server);
        dialog.setOnDialogChange(mOnDialogChangeListener);
        dialog.show();
        dialog.setWindowAttr(((Activity) mContext).getWindowManager());
    }

    public void updateScoreView(View view, SSServer server) {
        Object object = view.getTag();
        if (object instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) object;
            setScoreContent(holder, server);
        }
    }

    private void setScoreContent(ViewHolder holder, SSServer server) {
        TextView serverScore = holder.getView(R.id.textview_ss_score);
        if (server.getScore() != 0) {
            serverScore.setText(mContext.getResources().getText(R.string.score) + " " + server.getScore());
        }
    }

    private void setGradeViewContent(ViewHolder holder, SSServer server) {
        TextView serverGrade = holder.getView(R.id.textview_ss_grade);
        TextView serverStatus = holder.getView(R.id.textview_ss_status);
        if (server.getGrade() == 0) {
            serverGrade.setVisibility(View.GONE);
            serverStatus.setVisibility(View.GONE);
        } else {
            serverGrade.setVisibility(View.VISIBLE);
            serverStatus.setVisibility(View.VISIBLE);
            serverGrade.setText(mContext.getString(R.string.grade) + " : " + server.getGrade());
            serverStatus.setText(mContext.getString(R.string.status) + " : " + getStatueByGrade(server.getGrade()));
        }
    }

    public void updateGradeView(View view, SSServer server) {
        Object object = view.getTag();
        if (object instanceof ViewHolder) {
            setGradeViewContent((ViewHolder) object, server);
        }
    }

    private String getStatueByGrade(int grade) {
        if (grade >= 90)
            return mContext.getResources().getString(R.string.status_good);
        else if (grade < 90 && grade >= 60)
            return mContext.getResources().getString(R.string.status_normal);
        else
            return mContext.getResources().getString(R.string.status_bad);

    }


}
