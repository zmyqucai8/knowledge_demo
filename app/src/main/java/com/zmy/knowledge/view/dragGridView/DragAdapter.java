package com.zmy.knowledge.view.dragGridView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmy.knowledge.R;
import com.zmy.knowledge.main.bean.AppInfoBean;
import com.zmy.knowledge.main.bean.PagerBean;
import com.zmy.knowledge.utlis.AUtils;

/**
 * @author liuyongkui
 * @blog http://blog.csdn.net/sk719887916
 */
public class DragAdapter extends BaseAdapter implements DragGridListener {
    private Context mContext;
    private LayoutInflater mInflater;
    private int mHidePosition = -1;
    private List<AppInfoBean> listData;

    public DragAdapter(Context context, PagerBean list) {
        this.mContext = context;
        this.listData = list.list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 由于复用convertView导致某些item消失了，所以这里不复用item，
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.grid_item, null);
            holder.item_img = (ImageView) convertView.findViewById(R.id.item_image);
            holder.item_text = (TextView) convertView.findViewById(R.id.item_text);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.item_img.setImageDrawable(listData.get(position).getAppIcon());
        holder.item_text.setText(listData.get(position).getAppLabel());

        if (position == mHidePosition) {
            convertView.setVisibility(View.INVISIBLE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AUtils.startAppByPackagName(mContext, listData.get(position).getPkgName());
            }
        });


        return convertView;
    }

    class Holder {
        TextView item_text;

        ImageView item_img;

    }

    @Override
    public void reorderItems(int oldPosition, int newPosition) {
        AppInfoBean appInfoBean = listData.get(oldPosition);
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(listData, i, i + 1);
            }
        } else if (oldPosition > newPosition) {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(listData, i, i - 1);
            }
        }

        listData.set(newPosition, appInfoBean);
    }

    @Override
    public void setHideItem(int hidePosition) {
        this.mHidePosition = hidePosition;
        notifyDataSetChanged();
    }

    @Override
    public void removeItem(int deletePosition) {

        listData.remove(deletePosition);
        notifyDataSetChanged();
    }


}
