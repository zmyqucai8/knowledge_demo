package com.zmy.knowledge.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by win7 on 2017/7/11.
 */
@Entity
public class MsgBean {

    @Id
    private Long id; //id 自增长
    private Long mid;//这个就是外键 就是user的id
    @Property(nameInDb = "MSG")
    private String msg;
}
