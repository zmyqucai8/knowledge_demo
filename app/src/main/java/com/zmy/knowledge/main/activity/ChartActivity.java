package com.zmy.knowledge.main.activity;

import android.support.v7.widget.RecyclerView;
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

    RecyclerView recyclerView;
    @Override
    protected int getLayoutId() {
        return R.layout.chart;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {

        recyclerView = holder.get(R.id.recyclerView);




    }
}
