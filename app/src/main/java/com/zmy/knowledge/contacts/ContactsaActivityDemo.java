package com.zmy.knowledge.contacts;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.widget.ListView;


import com.zmy.knowledge.R;
import com.zmy.knowledge.utlis.AUtils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 联系人列表
 *
 * @author zmy
 */
public class ContactsaActivityDemo extends Activity {

    private ContactListAdapter adapter;
    private ListView contactList;
    private List<ContactBean> list;
    private List<ContactBean> list2;//2个方法做下区分
    private AsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象
    private QuickAlphabeticBar alphabeticBar; // 快速索引条

    private Map<Integer, ContactBean> contactIdMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list_view);
        contactList = (ListView) findViewById(R.id.contact_list);
        alphabeticBar = (QuickAlphabeticBar) findViewById(R.id.fast_scroller);
        // 实例化
        asyncQueryHandler = new MyAsyncQueryHandler(getContentResolver());
//        init(); //  耗时=12022、 9657  、 1200 、1152 、1352 、2430 、1228 、1163
        initData(); // 耗时=1088 、1036 、 1042、
        //经过测试还是 initData快
    }


    /**
     * 初始化数据的第二种方式 开启线程
     * 11892 s
     */
    private void initData() {
        new getContactTask().execute();
    }


    /**
     * 获取通讯录联系人
     */
    public class getContactTask extends AsyncTask<Boolean, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Boolean... params) {
            list2 = AUtils.getContact(ContactsaActivityDemo.this);
            return null != list2 && list2.size() > 0;
        }

        @Override
        protected void onPostExecute(Boolean o) {
            timeEnd();
            if (o) {
                list2 = ContactBean.sort(list2);
                setAdapter(list2);
            } else {
                AUtils.showToast("没有联系人");
                //没有读取联系人权限 或者没有联系人
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            timeStart();
        }
    }


    public long startTime = 0;
    public long endTime = 0;

    /**
     * 初始化数据库查询参数
     */
    private void init() {
        timeStart();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri；
        // 查询的字段
        String[] projection = {ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER, "sort_key",
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY,
                ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER

        };

        // 按照sort_key升序查詢
        asyncQueryHandler.startQuery(0, null, uri, projection, null, null,
                "sort_key COLLATE LOCALIZED asc");

    }

    /**
     * @author Administrator
     */
    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                contactIdMap = new HashMap<Integer, ContactBean>();
                list = new ArrayList<ContactBean>();
                cursor.moveToFirst(); // 游标移动到第一项
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(2);
                    String sortKey = cursor.getString(3);
                    int contactId = cursor.getInt(4);
                    Long photoId = cursor.getLong(5);
                    String lookUpKey = cursor.getString(6);
                    int phoneCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    if (contactIdMap.containsKey(contactId)) {
                        // 无操作
                    } else {
//                        AUtils.log(name + "的电话数量=" + phoneCount);
                        number = AUtils.getPhone(ContactsaActivityDemo.this, contactId, phoneCount, number);
                        if (!AUtils.isMobileNO(number)) {
                            //电话里没有手机号 直接跳过
                            continue;
                        }
                        // 创建联系人对象
                        ContactBean contact = new ContactBean();
                        contact.setDesplayName(name);
                        contact.setPhoneNum(number);
                        contact.setSortKey(sortKey);
                        contact.setPhotoId(photoId);
                        contact.setLookUpKey(lookUpKey);
                        contact.setContactId(contactId);
                        list.add(contact);
                        contactIdMap.put(contactId, contact);
                    }
                }
                cursor.close();
                timeEnd();

                if (list.size() > 0) {
                    setAdapter(list);
                }

            }
            super.onQueryComplete(token, cookie, cursor);

        }
    }

    //计时开始
    public void timeStart() {
        startTime = System.currentTimeMillis();
        AUtils.log("方法开始时间=" + startTime);
    }

    //计时结束
    public void timeEnd() {
        endTime = System.currentTimeMillis();
        AUtils.log("方法结束时间=" + endTime);
        AUtils.log("用时=" + (endTime - startTime));
        endTime = 0;
        startTime = 0;
        if (list != null) {
            AUtils.log("数据长度=" + list.size());
        }
        if (list2 != null) {
            AUtils.log("数据长度=" + list2.size());
        }
    }

    private void setAdapter(List<ContactBean> list) {
        for (ContactBean b : list) {
            AUtils.log(b.toString());
        }
        adapter = new ContactListAdapter(this, list, alphabeticBar);
        contactList.setAdapter(adapter);
        alphabeticBar.init(ContactsaActivityDemo.this);
        alphabeticBar.setListView(contactList);
        alphabeticBar.setHight(alphabeticBar.getHeight());
        alphabeticBar.setVisibility(View.VISIBLE);
    }
}
