package com.zmy.knowledge.chat;

import android.content.Intent;
import android.view.View;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.chat.fragment.ContactListFragment;

/**
 * Created by win7 on 2017/5/24.
 */

public class ContactListActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.chat_list_activity;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        ContactListFragment contactListFragment= new ContactListFragment();
//设置item点击事件
        contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {

            @Override
            public void onListItemClicked(EaseUser user) {
                startActivity(new Intent(ContactListActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername()));
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, contactListFragment).commit();

    }
}
