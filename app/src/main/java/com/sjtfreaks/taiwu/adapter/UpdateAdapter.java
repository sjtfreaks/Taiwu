package com.sjtfreaks.taiwu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.sjtfreaks.taiwu.R;
import com.sjtfreaks.taiwu.bean.Update;

import java.util.List;

public class UpdateAdapter extends BaseAdapter {
    private Context mContext;
    private List<Update> mList;
    private Update data;
    private LayoutInflater inflater;

    private WindowManager windowManager;
    private int width;

    public UpdateAdapter(Context mContext, List<Update> mList){
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //get系统服务
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕宽度
        width = windowManager.getDefaultDisplay().getWidth();
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.item_update,null);
            viewHolder.tv_title = view.findViewById(R.id.tv_title);
            viewHolder.tv_desc = view.findViewById(R.id.tv_desc);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        data = mList.get(i);
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_desc.setText(data.getContents());

        return view;
    }

    class ViewHolder{
        private TextView tv_title;//标题
        private TextView tv_desc;
    }
}
