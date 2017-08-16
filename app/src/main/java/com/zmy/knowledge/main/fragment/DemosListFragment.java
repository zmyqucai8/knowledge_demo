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


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowAnimationFrameStats;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.chat.EMClient;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mabeijianxi.smallvideorecord2.Log;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseFragment;
import com.zmy.knowledge.chat.view.ChatTabActivity;
import com.zmy.knowledge.contacts.ContactBean;
import com.zmy.knowledge.contacts.ContactsaActivityDemo;
import com.zmy.knowledge.main.activity.ChartActivity;
import com.zmy.knowledge.main.activity.DaoTest;
import com.zmy.knowledge.main.activity.PdfWordTestActivity;
import com.zmy.knowledge.main.activity.Test2Activity;
import com.zmy.knowledge.main.activity.VideoActivity;
import com.zmy.knowledge.main.activity.ZhuanChangActivity;
import com.zmy.knowledge.main.activity.head.HeadActivity;
import com.zmy.knowledge.main.activity.JsTestActivity;
import com.zmy.knowledge.main.activity.SDTestActivity;
import com.zmy.knowledge.main.activity.TransparentActivityDemo;
import com.zmy.knowledge.main.activity.DrawingBoardActivityDemo;
import com.zmy.knowledge.main.adapter.DemoAdapter;
import com.zmy.knowledge.main.widget.MsgPop;
import com.zmy.knowledge.upload.UploadActivity;
import com.zmy.knowledge.utlis.AUtils;
import com.zmy.knowledge.utlis.PermissionUtils;
import com.zmy.knowledge.video.MainActivity;
import com.zmy.knowledge.video.test.TestVideoActivity;
import com.zmy.knowledge.webx5.ActivityX5;
import com.zmy.knowledge.webx5.X5TestActivity;

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
        mDemo.add("test server ");
        mDemo.add("list zoom head");
        mDemo.add("WebX5内核浏览器");
        mDemo.add("WebX5内核浏览器22222");
        mDemo.add("5.0 之前的转场动画");
        mDemo.add("pdf Excel word");
        mDemo.add("dao test");
        mDemo.add("图表");
        mDemo.add("打开相册选择");
        mDemo.add("拍摄测试");
        mDemo.add("视频压缩测试");


        mAdapter = new DemoAdapter(mDemo);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //添加头部
        TextView head = new TextView(getActivity());
        head.setText("头部哦");
        head.setPadding(50, 50, 50, 50);
        head.setBackgroundColor(Color.RED);
        mAdapter.addHeaderView(head);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                mAdapter.notifyItemChanged(position);

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
                        startActivity(new Intent(getContext(), Test2Activity.class));
                        break;
                    case 5://上传
                        startActivity(new Intent(getContext(), UploadActivity.class));
                        break;
                    case 6://聊天功能
                        startActivity(new Intent(getContext(), ChatTabActivity.class));
                        break;
                    case 7://:h5测试
                        startActivity(new Intent(getContext(), JsTestActivity.class));
                        break;
                    case 8://:sd卡测试
                        startActivity(new Intent(getContext(), SDTestActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(getContext(), HeadActivity.class));
                        break;
                    case 10:
                        startActivity(new Intent(getContext(), ActivityX5.class));
                        break;
                    case 11:
                        startActivity(new Intent(getContext(), X5TestActivity.class));
                        break;
                    case 12:
                        startActivity(new Intent(getContext(), ZhuanChangActivity.class));
                        break;
                    case 13:
                        startActivity(new Intent(getContext(), PdfWordTestActivity.class));
                        break;
                    case 14:
                        startActivity(new Intent(getContext(), DaoTest.class));
                        break;
                    case 15:
                        startActivity(new Intent(getContext(), ChartActivity.class));
                        break;
                    case 16:
                        startActivity(new Intent(getContext(), VideoActivity.class));
                        break;
                    case 17:
                        startActivity(new Intent(getContext(), TestVideoActivity.class));
                        break;
                    case 18:
                        startActivity(new Intent(getContext(), MainActivity.class));
                        break;
                }
            }
        });


        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                msgPop = new MsgPop(getActivity(), new MsgPop.onClickListener() {
                    @Override
                    public void onTopClick() {
                        AUtils.showToast("置顶" + position);
                        AUtils.log("切换的时候这里是有问题的。");
                    }

                    @Override
                    public void onDeleteClick() {
                        AUtils.showToast("删除" + position);
                    }
                });
                msgPop.show(view, position);
                return true;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (null != msgPop) {
                    msgPop.dismiss();
                }
            }
        });


    }


    MsgPop msgPop;

    /**
     * 返回顶部
     */
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void quickToTop() {
        recyclerView.scrollToPosition(0);
    }


    //这个是回到顶部
    public static void main(String args) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回两种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true

                    String path = selectList.get(0).getPath();
                    Log.e("路径", path);
                    break;
            }
        }
    }
}