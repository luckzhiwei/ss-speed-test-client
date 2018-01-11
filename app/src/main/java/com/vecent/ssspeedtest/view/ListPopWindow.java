package com.vecent.ssspeedtest.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.vecent.ssspeedtest.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by zhiwei on 2018/1/9.
 */

public class ListPopWindow {

    protected PopupWindow mPopUpWindow;
    private List<String> mContent;
    private PopWindowAdapter mAdapter;
    private ListView mListView;
    private OnPopItemClick mOnPopItemClickListener;
    private Context mContext;

    public ListPopWindow(Context context, List<String> content) {
        this.mContent = content;
        this.mContext = context;
        this.mAdapter = new PopWindowAdapter(context, R.layout.popup_window_item, content);
        this.mPopUpWindow = new PopupWindow(context);
        View view = LayoutInflater.from(context).inflate(R.layout.popup_window_layout, null);
        mListView = view.findViewById(R.id.common_list_view);
        mListView.setAdapter(this.mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mOnPopItemClickListener != null)
                    mOnPopItemClickListener.onItemSelected(mContent.get(i));
            }
        });
        this.mPopUpWindow.setContentView(view);
        this.mPopUpWindow.setOutsideTouchable(true);
        this.mPopUpWindow.setBackgroundDrawable(new ColorDrawable(0xFFFFFF));
    }


    private class PopWindowAdapter extends CommonAdapter<String> {

        public PopWindowAdapter(Context context, int layoutId, List<String> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder viewHolder, String item, int position) {
            TextView textView = viewHolder.getView(R.id.textview_content);
            textView.setText(item);
        }
    }

    public interface OnPopItemClick {
        void onItemSelected(String content);
    }

    public void setOnPopItemClickListener(OnPopItemClick clickListener) {
        this.mOnPopItemClickListener = clickListener;
    }

    public void show(View view, float widthDp, float heightDp) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int width = (int) (metrics.density * widthDp);
        int height = (int) (metrics.density * heightDp);
        this.mPopUpWindow.setWidth(width);
        this.mPopUpWindow.setHeight(height);
        this.mPopUpWindow.showAsDropDown(view);
    }

    public void dismiss() {
        this.mPopUpWindow.dismiss();
    }
}
