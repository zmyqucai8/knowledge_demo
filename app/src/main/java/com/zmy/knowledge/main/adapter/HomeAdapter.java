package com.zmy.knowledge.main.adapter;

import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.http.bean.HomeDataBean;
import com.zmy.knowledge.utlis.AUtils;

import java.util.List;

/**
 * Created by win7 on 2017/5/11.
 */

public class HomeAdapter extends BaseQuickAdapter<HomeDataBean.ResultsBean, BaseViewHolder> {
    private Context mContext;

    public HomeAdapter(Context context, List<HomeDataBean.ResultsBean> data) {
        super(R.layout.home_item, data);
        this.mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, HomeDataBean.ResultsBean item) {

        helper
                .setText(R.id.tv_text, item.getDesc());


        AUtils.log(item.toString());
        List<String> images = item.getImages();
        if (null != images && images.size() > 0) {
            helper.setVisible(R.id.iv_img, true);
            Glide.with(mContext).load(images.get(0)).into((ImageView) helper.getView(R.id.iv_img));
        } else {
            helper.setVisible(R.id.iv_img, false);
        }
    }
}
