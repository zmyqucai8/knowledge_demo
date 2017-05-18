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

package com.zmy.knowledge.main.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MultiAutoCompleteTextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseFragment;
import com.zmy.knowledge.http.HttpApiUtlis;
import com.zmy.knowledge.http.HttpCallback;
import com.zmy.knowledge.http.bean.HomeDataBean;
import com.zmy.knowledge.main.adapter.HomeAdapter;
import com.zmy.knowledge.utlis.AUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 首页 topic 列表
 */
public class HomeListFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static HomeListFragment newInstance() {
        Bundle args = new Bundle();
        HomeListFragment fragment = new HomeListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    private int index = 0;

    @Override
    public void initData() {
        HttpApiUtlis.getHomeData(getContext(), index++, new HttpCallback<HomeDataBean>() {
            @Override
            public void onSuccess(HomeDataBean homeDataBean, Call call, Response response) {
                setData(homeDataBean);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                if (index > 1) {
                    mAdapter.loadMoreFail();
                }
                AUtils.showToast("网络错误");
            }
        });


    }

    /*home列表数据*/
    private List<HomeDataBean.ResultsBean> mListData = new ArrayList<>();
    HomeAdapter mAdapter;

    /**
     * 设置数据
     *
     * @param homeDataBean
     */
    private void setData(HomeDataBean homeDataBean) {
        if (null != homeDataBean) {

            if (index == 1) {
                if (null == mAdapter) {//第一次加载数据
                    mListData.addAll(homeDataBean.getResults());
                    mAdapter = new HomeAdapter(getContext(), homeDataBean.getResults());
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(manager);
                    mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                    mAdapter.setOnLoadMoreListener(this);
                    recyclerView.setAdapter(mAdapter);
                } else {//刷新数据
                    mAdapter.setNewData(homeDataBean.getResults());
                }
                //加载更多
            } else {
                mAdapter.addData(homeDataBean.getResults());
                mAdapter.loadMoreComplete();
            }


        }

    }


    /**
     * 返回顶部
     */
    public void quickToTop() {
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void onLoadMoreRequested() {
        initData();
    }
}