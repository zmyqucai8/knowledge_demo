package com.zmy.knowledge.chat;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.util.DateUtils;
import com.zmy.knowledge.R;

import java.util.Date;
import java.util.List;

/**
 * Created by win7 on 2017/5/17.
 * 自定义会话列表adapter
 */

public class ChatListAdapter extends BaseQuickAdapter<MyChatListBean, BaseViewHolder> {


    private Context mContext;


    public ChatListAdapter(Context context, List<MyChatListBean> data) {
        super(R.layout.chat_list_item, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyChatListBean item) {

        if (item.type == EaseConstant.CHATTYPE_SINGLE) {

            helper
                    .setText(R.id.tv_nick, item.name)
                    .setText(R.id.tv_text, item.msg)
                    .setText(R.id.tv_date, DateUtils.getTimestampString(new Date(item.date)))
                    .setText(R.id.tv_count, String.valueOf(item.wdCount));
            Glide.with(mContext).load(item.tx).into((ImageView) helper.getView(R.id.iv_tx));

        }

        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, item.name).putExtra(EaseConstant.EXTRA_CHAT_TYPE, item.type));
            }
        });
    }
}
