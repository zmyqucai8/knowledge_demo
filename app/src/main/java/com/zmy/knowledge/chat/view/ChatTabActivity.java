package com.zmy.knowledge.chat.view;

import android.app.NotificationManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.zmy.knowledge.R;
import com.zmy.knowledge.chat.ChatListActivity;
import com.zmy.knowledge.chat.ContactListActivity;
import com.zmy.knowledge.utlis.AUtils;


/**
 * 主界面
 */
public class ChatTabActivity extends TabActivity {
    private TabHost tabHost;//tabHost
    Intent chatListActivity;
    Intent contactListActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chattab);

        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    /**
     * 初始化view
     */
    private void initView() {
        tabHost = this.getTabHost();
        initIntent();
        addSpec();
        tabHost.setCurrentTab(0);
    }



    /**
     * 初始化各个tab标签对应的intent
     */
    private void initIntent() {
        chatListActivity = new Intent(this,ChatListActivity.class);
        contactListActivity = new Intent(this, ContactListActivity.class);

    }

    /**
     * 为tabHost添加各个标签项
     */
    private void addSpec() {
        //待办
        View todoPage = LayoutInflater.from(this).inflate(R.layout.view_tab_button, null);
        TextView todo_tv = (TextView) todoPage.findViewById(R.id.tv_text);
        todo_tv.setText("消息");
        TextView todo_ttf = (TextView) todoPage.findViewById(R.id.tv_icon_ttf);
        todo_ttf.setText(R.string.ttf_logo_icon);
        todo_ttf.setTypeface(AUtils.getTTF());

        //消息
        View msgPage = LayoutInflater.from(this).inflate(R.layout.view_tab_button, null);
        TextView msg_tv = (TextView) msgPage.findViewById(R.id.tv_text);
        msg_tv.setText("联系人");
        TextView msg_ttf = (TextView) msgPage.findViewById(R.id.tv_icon_ttf);
        msg_ttf.setText(R.string.ttf_logo_icon);
        msg_ttf.setTypeface(AUtils.getTTF());


        //添加到tab
        tabHost.addTab(tabHost.newTabSpec("01").setIndicator(todoPage)
                .setContent(chatListActivity));
        tabHost.addTab(tabHost.newTabSpec("02").setIndicator(msgPage)
                .setContent(contactListActivity));

    }
}
