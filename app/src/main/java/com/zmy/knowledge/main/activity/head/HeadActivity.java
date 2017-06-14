/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-04-08 23:15:33
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package com.zmy.knowledge.main.activity.head;


import android.content.Intent;
import android.graphics.Color;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.main.adapter.HeadAdapter;
import com.zmy.knowledge.utlis.AUtils;
import com.zmy.knowledge.view.WfViewRefreshFrameLayout;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * 侧滑菜单 加头部 recycleview
 */
public class HeadActivity extends BaseActivity {

    RecyclerView recyclerView;
    private WfViewRefreshFrameLayout mXdaRefreshView;
    HeadAdapter mAdapter;
    List<String> testData;

    public void initData() {
        testData = AUtils.getTestData(20);
        mAdapter = new HeadAdapter(testData);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //添加头部
        TextView head = new TextView(this);
        head.setText("头部哦");
        head.setPadding(50, 50, 50, 50);
        head.setBackgroundColor(Color.RED);  //
        mAdapter.addHeaderView(head);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AUtils.showToast("我是头部");
            }
        });
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AUtils.showToast(testData.get(position) + "\n position=" + position);
            }
        });

        mXdaRefreshView.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return !recyclerView.canScrollVertically(-1);
            }
        });

    }


    private void refreshData() {
        testData.clear();
        testData = AUtils.getTestData(20);
        mAdapter.setNewData(testData);
        //刷新完成
        mXdaRefreshView.refreshComplete();

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_hean;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {

//        recyclerView = holder.get(R.id.recyclerView);
//        mXdaRefreshView = holder.get(R.id.xda_refresh_view);
//        initData();
        findViewById(R.id.btn_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HeadActivity.this, PullToZoomListActivity.class));
            }
        });

        findViewById(R.id.btn_scroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HeadActivity.this, PullToZoomScrollActivity.class));
            }
        });

        findViewById(R.id.btn_recycler_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HeadActivity.this, PullToZoomRecyclerActivity.class));
            }
        });

    }




}