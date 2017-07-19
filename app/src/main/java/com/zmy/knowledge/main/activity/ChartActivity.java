package com.zmy.knowledge.main.activity;

import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;

/**
 * Created by win7 on 2017/7/17.
 */

public class ChartActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.chart;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        LineChart chart = holder.get(R.id.chart);

        //这里是一个图表的数据设置
        //在这里设置数据

    }
}
