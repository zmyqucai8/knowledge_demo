package com.zmy.knowledge.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.main.adapter.DemoAdapter;
import com.zmy.knowledge.utlis.AUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by win7 on 2017/5/24.
 * 会话列表页面
 */

public class ChatListActivity extends BaseActivity {
    ChatListAdapter mAdapter;
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.chat_list_activity;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
//        EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();
//        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
//            @Override
//            public void onListItemClicked(EMConversation conversation) {
//                startActivity(new Intent(ChatListActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
//            }
//        });
//通过getSupportFragmentManager启动fragment即可
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment, conversationListFragment).commit();
        recyclerView = holder.get(R.id.recyclerView);

        mAdapter = new ChatListAdapter(this, getData());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //刷新
        sx();
    }

    public void sx() {
        if (mAdapter != null) {
            mAdapter.setNewData(getData());
        }
    }

    public List<MyChatListBean> getData() {
        //获取聊天记录消息
        List<EMConversation> emConversations = loadConversationList();

        List<MyChatListBean> mList = new ArrayList<>();

        for (EMConversation e : emConversations) {

            MyChatListBean bean = new MyChatListBean();

            EaseUser userInfo = EaseUserUtils.getUserInfo(e.conversationId());
            if (userInfo == null) {
                continue;
            }
            bean.name = userInfo.getUsername();
            bean.tx = userInfo.getAvatar();
            bean.nick = userInfo.getNick();
            if (e.getAllMsgCount() != 0) {
                EMMessage lastMessage = e.getLastMessage();
                bean.msg = EaseSmileUtils.getSmiledText(this, EaseCommonUtils.getMessageDigest(lastMessage, this)).toString();
            }
//            bean.msg = e.getLastMessage().getBody().();
            if (e.getType() == EMConversation.EMConversationType.Chat) {
                bean.type = EaseConstant.CHATTYPE_SINGLE;
            } else {
                bean.type = EaseConstant.CHATTYPE_GROUP;
            }
            bean.wdCount = e.getUnreadMsgCount();
            bean.date = e.getLastMessage().getMsgTime();
            mList.add(bean);
        }

        //添加自己的消息
        for (int o = 0; o < 8; o++) {
            MyChatListBean bean = new MyChatListBean();
            bean.name = "" + o;
            bean.tx = "0" + 1;
            bean.nick = "姓名" + 1;
            bean.msg = "我是信息";
            bean.wdCount = 99;
            bean.date = System.currentTimeMillis();
            bean.type = EaseConstant.CHATTYPE_SINGLE;
            mList.add(bean);
        }


//        sortConversationByLastChatTime();

        for (MyChatListBean b : mList) {
            AUtils.log("会话列表=" + b.toString());
        }
        return mList;
    }

    /**
     * 加载对话列表
     *
     * @return +
     */
    protected List<EMConversation> loadConversationList() {
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         如果在排序期间有新消息，则lastMsgTime将会更改
         因此，使用同步来确保最后消息的时间戳不会改变。
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }


    /**
     * 根据最后消息的时间戳进行排序
     *
     * @param conversationList
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }

}
