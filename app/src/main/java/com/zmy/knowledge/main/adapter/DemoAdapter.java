package com.zmy.knowledge.main.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunfusheng.glideimageview.GlideImageView;
import com.zmy.knowledge.R;
import com.zmy.knowledge.utlis.AUtils;

import java.util.List;

/**
 * Created by win7 on 2017/5/17.
 */

public class DemoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public DemoAdapter(List<String> data) {
        super(R.layout.demo_item, data);
    }

    String tx1 = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=523024675,1399288021&fm=117&gp=0.jpg";
    String tx2 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2600862994,2565094368&fm=117&gp=0.jpg";
    String tx3 = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2182925329,386985427&fm=117&gp=0.jpg";
    String tx4 = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3664893832,2142990655&fm=117&gp=0.jpg";
    String tx5 = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=36531021,4282767521&fm=117&gp=0.jpg";

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_text, item);

        GlideImageView img = helper.getView(R.id.img);

        if (helper.getAdapterPosition() % 2 == 0) {
            img.loadCircleImage(tx1, R.drawable.ic_launcher);
        } else {
            img.loadCircleImage(tx3, R.drawable.ic_launcher);
        }
        ImageView img2 = helper.getView(R.id.img2);

        if (helper.getAdapterPosition() % 2 == 0) {

            Glide.with(mContext).load(tx1).into(img2);
        } else {

            Glide.with(mContext).load(tx2).into(img2);
        }


    }
}
