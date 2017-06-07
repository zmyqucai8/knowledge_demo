package com.zmy.knowledge.chat;

/**
 * Created by win7 on 2017/6/1.
 * 会话列表
 */
public class MyChatListBean {
//我的会话列表

    @Override
    public String toString() {
        return "MyChatListBean{" +
                "name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                ", tx='" + tx + '\'' +
                ", msg='" + msg + '\'' +
                ", wdCount=" + wdCount +
                ", date=" + date +
                ", type=" + type +
                '}';
    }

    public String name;//name
    public String nick;
    public String tx;
    public String msg;
    public int wdCount;
    public long date;
    public int type;
}
