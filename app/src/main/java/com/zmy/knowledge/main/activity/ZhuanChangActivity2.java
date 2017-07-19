package com.zmy.knowledge.main.activity;


import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.swipeback.MySwipeBackActivity;

/**
 * Created by win7 on 2017/6/23.
 */

public class ZhuanChangActivity2 extends MySwipeBackActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.zc_activity_2;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {

    }

    public void 缩放(View v) {  //进行一个缩放的动画  test 测试
        finish();
        overridePendingTransition(R.anim.zoom_enter2, R.anim.zoom_exit);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.anim_phone_card_pop_in, R.anim.anim_phone_card_pop_out);
            return false;
        }
        return false;
    }

    /**
     * 这里是一个测试的方法 。 明天转区
     */
    public void test() {

        //和你在一起还是挺开心的，但是怎么说呢，其实我能接受，但是没必要啊， 事情都发展到这一步了
        //我炮也约了，还能怎么办，哈哈哈，要么分手老死不相往来，要么和好双宿双飞。
        //我现在都不想理她 ，爱咋想咋想，拉几把倒
        //静态方法 静态类
        //受不了异地恋
        //也不想去改变/
        //na wo zai zheli ceshi guaji hoao l ..
    }


    public void testGreenMZ() {
        String rbq;
        String cnm;
        String cnxm;
        String lll;
        String 污泥吗的;
        String 如果不是那镜子, 不像你, 不藏秘密;
        String 我嘟的一声切的比你说分手彻底;
        String 泪湿的衣洗干净洗干净;

    }


}
