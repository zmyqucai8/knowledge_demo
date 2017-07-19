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


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseFragment;
import com.zmy.knowledge.github.takephoto.TakePhotoActivity;
import com.zmy.knowledge.main.adapter.DemoAdapter;
import com.zmy.knowledge.utlis.AUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * github开源项目
 */
public class GithubListFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static GithubListFragment newInstance() {
        Bundle args = new Bundle();
        GithubListFragment fragment = new GithubListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    DemoAdapter mAdapter;

    @Override
    public void initData() {

        List<String> mlist= new ArrayList<>();
        mlist.add("TakePhoto \n 一款用于在Android设备上获取照片（拍照或从相册、文件中选择）、裁剪图片、压缩图片的开源工具库 ");
        mAdapter = new DemoAdapter(mlist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position)
                {
                    case 0://takePhoto
                        startActivity(new Intent(getActivity(),TakePhotoActivity.class));
                        break;
                }
            }
        });
    }

    /**
     * 返回顶部
     */
    public void quickToTop() {
        recyclerView.scrollToPosition(0);
    }


}