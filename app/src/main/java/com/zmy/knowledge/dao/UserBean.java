package com.zmy.knowledge.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

/**
 * Created by win7 on 2017/7/11.
 */
@Entity
public class UserBean  {
    @Property(nameInDb = "NICK")
    private String nick;
    @Property(nameInDb = "USER_ID")
    @Index(unique = true)
    private String userId;

    @ToMany(referencedJoinProperty = "mid")//指定与之关联的其他类的id
    private List<MsgBean> msgBean;
}
