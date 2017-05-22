package com.zmy.knowledge.main.bean;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zmy.knowledge.R;
import com.zmy.knowledge.view.dragGridView.DragAdapter;
import com.zmy.knowledge.view.dragGridView.DragGridView;

import java.util.List;

/**
 * Created by win7 on 2017/5/19.
 */

public class AppViewPagerAdapter extends PagerAdapter implements AdapterView.OnItemClickListener {
    private Context mContext;
    private List<PagerBean> mDataList;

    public AppViewPagerAdapter(Context context, List<PagerBean> list) {
        this.mContext = context;
        this.mDataList = list;

    }

    @Override
    public int getCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.app_viewpager_item, null);
        DragGridView mDragGridView = (DragGridView) view.findViewById(R.id.dragGridView);
        DragAdapter mDragAdapter = new DragAdapter(mContext, mDataList.get(position));
        mDragGridView.setAdapter(mDragAdapter);
        //设置需要抖动
        mDragGridView.setNeedShake(false);
        mDragGridView.setOnItemClickListener(this);
        container.addView(view);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
