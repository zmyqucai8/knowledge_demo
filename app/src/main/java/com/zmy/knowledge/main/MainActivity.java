package com.zmy.knowledge.main;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.zmy.knowledge.R;
import com.zmy.knowledge.SettingActivity;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.main.fragment.DemosListFragment;

import com.zmy.knowledge.main.fragment.GithubListFragment;
import com.zmy.knowledge.main.fragment.HomeListFragment;
import com.zmy.knowledge.utlis.AUtils;
import com.zmy.knowledge.utlis.PermissionUtils;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 这是一个集合很多demo的demo
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private int mCurrentPosition = 0;
    private HomeListFragment mFragment1;
    private DemosListFragment mFragment2;
    private GithubListFragment mFragment3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        initMenu(holder);
        initViewPager(holder);
        initPush();
    }


    /*初始化推送*/
    private void initPush() {
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "PbbGBe5pfzNYjkyf3o8eTU4V");
    }


    // 初始化菜单(包括侧边栏菜单和顶部菜单选项)
    private void initMenu(ViewHolder holder) {
        Toolbar toolbar = holder.get(R.id.toolbar);
        toolbar.setLogo(R.mipmap.logo);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo_withe);
        DrawerLayout drawer = holder.get(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 双击 666
        final GestureDetector detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                quickToTop();   // 快速返回头部
                return super.onDoubleTap(e);
            }
        });

        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return false;
            }
        });

        toolbar.setOnClickListener(this);

        holder.setOnClickListener(this, R.id.fab);

        loadMenuData();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_post) {
            AUtils.showToast("我的帖子");
        } else if (id == R.id.nav_collect) {
            AUtils.showToast("我的收藏");
        } else if (id == R.id.nav_about) {
            AUtils.showToast("关于");
        } else if (id == R.id.nav_setting) {
//            AUtils.showToast("设置");
            startActivity(new Intent(this, SettingActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                quickToTop();
                break;
        }

    }

    // 快速返回顶部
    private void quickToTop() {
        switch (mCurrentPosition) {
            case 0:
                mFragment1.quickToTop();
                break;
            case 1:
                mFragment2.quickToTop();
                break;
            case 2:
                mFragment3.quickToTop();
                break;
        }
    }

    // 加载侧边栏菜单数据(与用户相关的)
    private void loadMenuData() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView avatar = (ImageView) headerView.findViewById(R.id.nav_header_image);
        TextView username = (TextView) headerView.findViewById(R.id.nav_header_name);
        TextView tagline = (TextView) headerView.findViewById(R.id.nav_header_tagline);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AUtils.showToast("cloud");
//                    openActivity(LoginActivity.class);
            }
        });
    }


    private void initViewPager(ViewHolder holder) {
        ViewPager mViewPager = holder.get(R.id.view_pager);
        TabLayout mTabLayout = holder.get(R.id.tab_layout);
        mViewPager.setOffscreenPageLimit(3); // 防止滑动到第三个页面时，第一个页面被销毁

        mFragment1 = HomeListFragment.newInstance();
        mFragment2 = DemosListFragment.newInstance();
        mFragment3 = GithubListFragment.newInstance();

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            String[] types = {"Home", "DEMO", "TIPS"};

            @Override
            public Fragment getItem(int position) {
                if (position == 0)
                    return mFragment1;
                if (position == 1)
                    return mFragment2;
                if (position == 2)
                    return mFragment3;
                return mFragment3;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return types[position];
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

//        mCurrentPosition = mConfig.getMainViewPagerPosition();
        mViewPager.setCurrentItem(0);

        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //返回键的监听
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            exitBy2Click();
            return true;
        }
        return false;
    }


    boolean isExit = false;//返回的tag

    /**
     * 再按一次返回桌面
     */
    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            AUtils.showToast("再按一次返回桌面");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {

            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_HOME);
//            Intent intent = new Intent(this, TransparentActivityDemo.class);
//            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mFragment2.grant);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri contactData = data.getData();
            Cursor cursor = managedQuery(contactData, null, null, null,
                    null);
            cursor.moveToFirst();
            String num = this.getContactPhone(cursor);
            AUtils.showNotification(this, "所选手机号为：" + num, R.id.content_main);
        }
    }


    private String getContactPhone(Cursor cursor) {
        // TODO Auto-generated method stub
        int phoneColumn = cursor
                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String result = "";
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人电话的cursor
            Cursor phone = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                            + contactId, null, null);
            if (phone.moveToFirst()) {
                for (; !phone.isAfterLast(); phone.moveToNext()) {
                    int index = phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int typeindex = phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int phone_type = phone.getInt(typeindex);
                    String phoneNumber = phone.getString(index);
                    result = phoneNumber;
//                  switch (phone_type) {//此处请看下方注释
//                  case 2:
//                      result = phoneNumber;
//                      break;
//
//                  default:
//                      break;
//                  }
                }
                if (!phone.isClosed()) {
                    phone.close();
                }
            }
        }
        return result;
    }

    //shibushi juede wo caota henshufu a , name xihuanwoo . kaixin , wo ye hen xihuan ni ya , xiaokeai .
    //ni zhidao ma , wozhende henxihuanni a ,wo gennijiang o , ni jiu shi yige dashabi ,hahah ,xiaosiwole.

    public static void main(String[] args) {
        /**
         前尘往事成云烟 ， 消散在彼此眼前 ，
         前尘往事成云烟， 消散在彼此眼前
         就连说过了再见， 也看不见你有些哀怨
         给我的一切 ， 你不过是在敷衍
         你笑的月无邪，我就会爱你爱的更矿业
         给我的一切你不过是在敷衍
         就连说过了再见 ， 也看不见你有些哀怨 ，
         给我的一切 ， 你不过是在敷衍 ，
         你笑的越无邪 我就会爱你爱得更狂野
         总在刹那间 有一些了解
         说过的话不可能会实现
         就在一转眼 发现你的脸
         已经陌生不会再像从前
        我的世界开始下雪
         冷的让我无法多爱一天
         冷得连隐藏的遗憾 都那么地明显
         我和你吻别 在无人的街
         让风痴笑我不能拒绝
         我和你吻别 在狂乱的夜
         我的心 等着迎接伤悲
         想要给你的思念 就像风筝断了线
         飞不进你的世界 也温暖不了你的视线
         我已经看见 一出悲剧正上演
         剧终没有喜悦 我仍然躲在你的梦里面

         总在刹那间 有一些了解
         说过的话不可能会实现
         就在一转眼 发现你的脸
         已经陌生不会再像从前
         我的世界开始下雪
         冷得让我无法多爱一天
         冷得连隐藏的遗憾 都那么地明显
         我和你吻别 在无人的街
         让风痴笑我不能拒绝
         我和你吻别 在狂乱的夜
         我的心 等着迎接伤悲
         我和你吻别 在无人的街
         让风痴笑我不能拒绝
         我和你吻别 在狂乱的夜
         我的心 等着迎接伤悲[3]
         */
    }
}
