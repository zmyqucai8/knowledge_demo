package com.zmy.knowledge.main.activity;

import android.view.View;
import android.view.WindowAnimationFrameStats;

import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.Contacts;
import com.github.tamir7.contacts.PhoneNumber;
import com.github.tamir7.contacts.Query;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.utlis.AUtils;

import java.util.List;

/**
 * Created by win7 on 2017/7/5.
 */

public class Test2Activity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.test2;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {


    }

    public void getAll(View v) {
        List<Contact> contacts = Contacts.getQuery().find();
        AUtils.log("全部联系人数据= " +contacts.size());
        for (Contact c : contacts) {
            AUtils.log("getDisplayName=" + c.getDisplayName());
            List<PhoneNumber> phoneNumbers = c.getPhoneNumbers();
            for (PhoneNumber p : phoneNumbers) {
                AUtils.log("getNormalizedNumber=" + p.getNormalizedNumber());
                AUtils.log("getType=" + p.getType());
                AUtils.log("getNumber=" + p.getNumber());
                AUtils.log("getLabel=" + p.getLabel());
            }
            AUtils.log("===========================================");
        }
    }

    public void getPhone(View v) {
        Query q = Contacts.getQuery();
        q.hasPhoneNumber();
        List<Contact> contacts = q.find();
        AUtils.log("手机号联系人数据= " +contacts.size());
            for (Contact c : contacts) {
                AUtils.log("getDisplayName=" + c.getDisplayName());
                List<PhoneNumber> phoneNumbers = c.getPhoneNumbers();
                for (PhoneNumber p : phoneNumbers) {
                    AUtils.log("getNormalizedNumber=" + p.getNormalizedNumber());
                    AUtils.log("getType=" + p.getType());
                    AUtils.log("getNumber=" + p.getNumber());
                    AUtils.log("getLabel=" + p.getLabel());
                }
                AUtils.log("===========================================");
        }
    }
}
