package com.zmy.knowledge.chat.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.PathUtil;
import com.zmy.knowledge.R;
import com.zmy.knowledge.chat.Constant;
import com.zmy.knowledge.chat.ContextMenuActivity;
import com.zmy.knowledge.chat.DemoHelper;
import com.zmy.knowledge.chat.EmojiconExampleGroupData;
import com.zmy.knowledge.chat.view.ChatRowVoiceCall;
import com.zmy.knowledge.main.MainActivity;
import com.zmy.knowledge.utlis.AUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by win7 on 2017/5/24.
 */

public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {

    // constant start from 11 to avoid conflict with constant in base class
    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;




    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;
    private static final int REQUEST_CODE_SELECT_AT_USER = 15;


    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void setUpView() {
        setChatFragmentHelper(this);
        if (chatType == Constant.CHATTYPE_SINGLE) {
            //机器人？
//            Map<String,RobotUser> robotMap = DemoHelper.getInstance().getRobotList();
//            if(robotMap!=null && robotMap.containsKey(toChatUsername)){
//                isRobot = true;
//            }
        }
        super.setUpView();
        // set click listener
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (EasyUtils.isSingleActivity(getActivity())) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                onBackPressed();
            }
        });
        ((EaseEmojiconMenu) inputMenu.getEmojiconMenu()).addEmojiconGroup(EmojiconExampleGroupData.getData());
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            inputMenu.getPrimaryMenu().getEditText().addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 1 && "@".equals(String.valueOf(s.charAt(start)))) {
                        AUtils.showToast("选择用户界面?");
//                        startActivityForResult(new Intent(getActivity(), PickAtUserActivity.class).
//                                putExtra("groupId", toChatUsername), REQUEST_CODE_SELECT_AT_USER);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @Override
    protected void registerExtendMenuItem() {
        //use the menu in base class
        super.registerExtendMenuItem();
        //extend menu items
        inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, ITEM_VIDEO, extendMenuItemClickListener);
        inputMenu.registerExtendMenuItem(R.string.attach_file, R.drawable.em_chat_file_selector, ITEM_FILE, extendMenuItemClickListener);
//        if (chatType == Constant.CHATTYPE_SINGLE) {
//            inputMenu.registerExtendMenuItem(R.string.attach_voice_call, R.drawable.em_chat_voice_call_selector, ITEM_VOICE_CALL, extendMenuItemClickListener);
//            inputMenu.registerExtendMenuItem(R.string.attach_video_call, R.drawable.em_chat_video_call_selector, ITEM_VIDEO_CALL, extendMenuItemClickListener);
//        }

        //聊天室暂时不支持红包功能 todo:
        //red packet code : 注册红包菜单选项
        if (chatType != Constant.CHATTYPE_CHATROOM) {
//            inputMenu.registerExtendMenuItem(R.string.attach_red_packet, R.drawable.em_chat_red_packet_selector, ITEM_RED_PACKET, extendMenuItemClickListener);
        }
        //end of red packet code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
                case ContextMenuActivity.RESULT_CODE_COPY: // copy
                    clipboard.setPrimaryClip(ClipData.newPlainText(null,
                            ((EMTextMessageBody) contextMenuMessage.getBody()).getMessage()));
                    break;
                case ContextMenuActivity.RESULT_CODE_DELETE: // delete
                    conversation.removeMessage(contextMenuMessage.getMsgId());
                    messageList.refresh();
                    break;

                case ContextMenuActivity.RESULT_CODE_FORWARD: // forward
                    AUtils.showToast("历史消息？");
//                    Intent intent = new Intent(getActivity(), ForwardMessageActivity.class);
//                    intent.putExtra("forward_msg_id", contextMenuMessage.getMsgId());
//                    startActivity(intent);

                    break;

                default:
                    break;
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_VIDEO: //send the video
                    if (data != null) {
                        int duration = data.getIntExtra("dur", 0);
                        String videoPath = data.getStringExtra("path");
                        File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                            ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.close();
                            sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_FILE: //send the file
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            sendFileByUri(uri);
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_AT_USER:
                    if (data != null) {
                        String username = data.getStringExtra("username");
                        inputAtUsername(username, false);
                    }
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return new CustomChatRowProvider();
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
//        if(isRobot){
//            //set message extension
//            message.setAttribute("em_robot_message", isRobot);
//        }
    }

    @Override
    public void onEnterToChatDetails() {
        if (chatType == Constant.CHATTYPE_GROUP) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
            if (group == null) {
                Toast.makeText(getActivity(), R.string.gorup_not_found, Toast.LENGTH_SHORT).show();
                return;
            }
            AUtils.showToast("群组");
//            startActivityForResult((new Intent(getActivity(), GroupDetailsActivity.class).putExtra("groupId", toChatUsername)),
//                    REQUEST_CODE_GROUP_DETAIL);
        } else if (chatType == Constant.CHATTYPE_CHATROOM) {
//            startActivityForResult(new Intent(getActivity(), ChatRoomDetailsActivity.class).putExtra("roomId", toChatUsername), REQUEST_CODE_GROUP_DETAIL);
            AUtils.showToast("聊天室");
        }

    }

    @Override
    public void onAvatarClick(String username) {
        AUtils.showToast("头像点击");
//        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
//        intent.putExtra("username", username);
//        startActivity(intent);
    }

    @Override
    public void onAvatarLongClick(String username) {
        inputAtUsername(username);
        AUtils.showToast("头像长按");
    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //消息框点击事件，demo这里不做覆盖，如需覆盖，return true
        //red packet code : 拆红包页面
//        if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)){
//            RedPacketUtil.openRedPacket(getActivity(), chatType, message, toChatUsername, messageList);
//            return true;
//        }
        //end of red packet code
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
        // no message forward when in chat room
        startActivityForResult((new Intent(getActivity(), ContextMenuActivity.class)).putExtra("message", message)
                        .putExtra("ischatroom", chatType == EaseConstant.CHATTYPE_CHATROOM),
                REQUEST_CODE_CONTEXT_MENU);
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_VIDEO:
                AUtils.showToast("视频文件");
//                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
                break;
            case ITEM_FILE: //file
//                selectFileFromLocal();
                AUtils.showToast("文件");
                break;
//            case ITEM_VOICE_CALL:
//                AUtils.showToast("语音通话");
////                startVoiceCall();
//                break;
//            case ITEM_VIDEO_CALL:
//                AUtils.showToast("视频通话");
////                startVideoCall();
//                break;

            //red packet code : 进入发红包页面 todo:
//            case ITEM_RED_PACKET:
//                //注意：不再支持原有的startActivityForResult进入红包相关页面
//                int itemType;
//                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
//                    itemType = RPConstant.RP_ITEM_TYPE_SINGLE;
//                    //小额随机红包
//                    //itemType = RPConstant.RP_ITEM_TYPE_RANDOM;
//                } else {
//                    itemType = RPConstant.RP_ITEM_TYPE_GROUP;
//                }
//                RedPacketUtil.startRedPacket(getActivity(), itemType, toChatUsername, new RPSendPacketCallback() {
//                    @Override
//                    public void onGenerateRedPacketId(String redPacketId) {
//
//                    }
//
//                    @Override
//                    public void onSendPacketSuccess(RedPacketInfo redPacketInfo) {
//                        sendMessage(RedPacketUtil.createRPMessage(getActivity(), redPacketInfo, toChatUsername));
//                    }
//                });
//                break;
            //end of red packet code
            default:
                break;
        }

        return false;
    }


    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        //red packet code : 处理红包回执透传消息
        for (EMMessage message : messages) {
            EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
            String action = cmdMsgBody.action();//获取自定义action
//            if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)){
//                RedPacketUtil.receiveRedPacketAckMessage(message);
//                messageList.refresh();
//            }
        }
        //end of red packet code
        super.onCmdMessageReceived(messages);
    }

    /**
     * chat row provider
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //here the number is the message type in EMMessage::Type
            //which is used to count the number of different chat row
            return 10;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if (message.getType() == EMMessage.Type.TXT) {
                //voice call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    //video call
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
                //red packet code : 红包消息、红包回执消息以及转账消息的chatrow type
//                else if (RedPacketUtil.isRandomRedPacket(message)) {
//                    //小额随机红包
//                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_RANDOM : MESSAGE_TYPE_SEND_RANDOM;
//                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {
//                    //发送红包消息
//                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_RED_PACKET : MESSAGE_TYPE_SEND_RED_PACKET;
//                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {
//                    //领取红包消息
//                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_RED_PACKET_ACK : MESSAGE_TYPE_SEND_RED_PACKET_ACK;
//                }
                //end of red packet code
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            if (message.getType() == EMMessage.Type.TXT) {
                // voice call or video call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false) ||
                        message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    return new ChatRowVoiceCall(getActivity(), message, position, adapter);
                }
//                //red packet code : 红包消息、红包回执消息以及转账消息的chat row
//                else if (RedPacketUtil.isRandomRedPacket(message)) {//小额随机红包
//                    return new ChatRowRandomPacket(getActivity(), message, position, adapter);
//                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {//红包消息
//                    return new ChatRowRedPacket(getActivity(), message, position, adapter);
//                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {//红包回执消息
//                    return new ChatRowRedPacketAck(getActivity(), message, position, adapter);
//                }
                //end of red packet code
            }
            return null;
        }

    }

}
