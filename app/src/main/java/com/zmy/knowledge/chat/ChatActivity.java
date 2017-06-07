package com.zmy.knowledge.chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.chat.fragment.ChatFragment;
import com.zmy.knowledge.chat.runtimepermissions.PermissionsManager;
import com.zmy.knowledge.main.MainActivity;

/**
 * Created by win7 on 2017/5/24.
 * 会话页面
 */

public class ChatActivity extends BaseActivity {
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;

    @Override
    protected int getLayoutId() {
        return R.layout.chat_list_activity;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        activityInstance = this;
        toChatUsername = getIntent().getExtras().getString("userId");
//new出EaseChatFragment或其子类的实例
        chatFragment = new ChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, chatFragment).commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public String getToChatUsername() {
        return toChatUsername;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
