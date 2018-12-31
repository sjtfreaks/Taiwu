package com.sjtfreaks.taiwu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjtfreaks.taiwu.R;
import com.sjtfreaks.taiwu.bean.AttData;
import com.sjtfreaks.taiwu.utils.PicassoUtils;


import java.util.List;

public class AttAdapter extends BaseAdapter {
    private Context mContext;
    private List<AttData> mList;
    private AttData data;
    private LayoutInflater inflater;

    private WindowManager windowManager;
    private int width;
    public AttAdapter(Context mContext, List<AttData> mList){
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
            view = inflater.inflate(R.layout.item_att,null);
            viewHolder.tv_title = view.findViewById(R.id.tv_title);
            viewHolder.tv_desc = view.findViewById(R.id.tv_desc);
            viewHolder.iv_att = (ImageView)view.findViewById(R.id.im_att);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        data = mList.get(i);
        String iv_att = data.getImages();
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_desc.setText(data.getDesc());
        //指定大小
        PicassoUtils.loadImageViewSize(mContext,iv_att,viewHolder.iv_att,width,500);

        return view;
    }

    class ViewHolder{
        private TextView tv_title;//标题
        private TextView tv_desc;
        private ImageView iv_att;
    }
}
