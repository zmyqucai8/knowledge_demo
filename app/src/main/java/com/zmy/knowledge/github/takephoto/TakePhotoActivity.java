package com.zmy.knowledge.github.takephoto;

import android.content.Intent;
import android.view.View;

import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;

/**
 * Created by win7 on 2017/7/19.
 */

public class TakePhotoActivity extends BaseActivity  implements View.OnClickListener{
    @Override
    protected int getLayoutId() {
        return R.layout.takephoto;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTakePhotoActivity:
                startActivity(new Intent(this,SimpleActivity.class));
                break;
            case R.id.btnTakePhotoFragment:
                startActivity(new Intent(this,SimpleFragmentActivity.class));
                break;
            default:
        }
    }
}
