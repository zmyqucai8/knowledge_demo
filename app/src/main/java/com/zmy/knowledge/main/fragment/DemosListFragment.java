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
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseFragment;
import com.zmy.knowledge.chat.ChatListActivity;
import com.zmy.knowledge.chat.ContactListActivity;
import com.zmy.knowledge.chat.view.ChatTabActivity;
import com.zmy.knowledge.contacts.ContactBean;
import com.zmy.knowledge.contacts.ContactsaActivityDemo;
import com.zmy.knowledge.main.MainActivity;
import com.zmy.knowledge.main.activity.JsTestActivity;
import com.zmy.knowledge.main.activity.SDTestActivity;
import com.zmy.knowledge.main.activity.TransparentActivityDemo;
import com.zmy.knowledge.main.activity.DrawingBoardActivityDemo;
import com.zmy.knowledge.main.adapter.DemoAdapter;
import com.zmy.knowledge.upload.UploadActivity;
import com.zmy.knowledge.utlis.AUtils;
import com.zmy.knowledge.utlis.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 首页 topic 列表
 */
public class DemosListFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static DemosListFragment newInstance() {
        Bundle args = new Bundle();
        DemosListFragment fragment = new DemosListFragment();
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
    //请求相机权限的回调
    public PermissionUtils.PermissionGrant grant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            if (requestCode == PermissionUtils.CODE_CAMERA)
                startActivity(new Intent(getContext(), TransparentActivityDemo.class));
            else if (requestCode == PermissionUtils.CODE_GET_ACCOUNTS)
                startActivity(new Intent(getContext(), ContactsaActivityDemo.class));

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void initData() {
        List<String> mDemo = new ArrayList<>();
        mDemo.add("你画我猜");
        mDemo.add("透明屏幕");
        mDemo.add("手机通讯录");
        mDemo.add("打开手机系统通讯录选择一个电话");
        mDemo.add("查询通讯录");
        mDemo.add("上传文件");
        mDemo.add("聊天功能");
        mDemo.add("JavaScript测试");
        mDemo.add("小测试");
        mAdapter = new DemoAdapter(mDemo);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0: //你画我猜
                        startActivity(new Intent(getContext(), DrawingBoardActivityDemo.class));
                        break;
                    case 1: //透明屏幕
                        //获取相机权限
                        PermissionUtils.requestPermission(getActivity(), PermissionUtils.CODE_CAMERA, grant);
                        break;
                    case 2:
                        //手机通讯录
                        PermissionUtils.requestPermission(getActivity(), PermissionUtils.CODE_GET_ACCOUNTS, grant);
                        break;
                    case 3:
                        //手机系统通讯录
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                ContactsContract.Contacts.CONTENT_URI);
                        getActivity().startActivityForResult(intent, 1);
                        break;
                    case 4:
                        //查询通讯录
                        AUtils.log("方法开始时间=" + System.currentTimeMillis());
                        long l = System.currentTimeMillis();
                        List<ContactBean> contact = AUtils.getContact(getContext());
                        for (ContactBean c : contact) {
                            AUtils.log(c.toString());
                        }
                        AUtils.log("方法结束时间=" + System.currentTimeMillis());
                        long l1 = System.currentTimeMillis();
                        AUtils.log("方法用时" + (l1 - l));
                        break;
                    case 5://上传
                        startActivity(new Intent(getContext(), UploadActivity.class));
                        break;
                    case 6://聊天功能
                        startActivity(new Intent(getContext(), ChatTabActivity.class));
                        break;
                    case 7://:h5测试
                        startActivity(new Intent(getContext(), JsTestActivity.class));
                        break;       case 8://:sd卡测试
                        startActivity(new Intent(getContext(), SDTestActivity.class));
                        break;
                }
            }
        });
    }

    /**
     * 返回顶部
     */
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void quickToTop() {
        recyclerView.scrollToPosition(0);
    }


}