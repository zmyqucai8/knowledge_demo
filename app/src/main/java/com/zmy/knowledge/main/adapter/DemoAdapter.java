package com.zmy.knowledge.main.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmy.knowledge.R;

import java.util.List;

/**
 * Created by win7 on 2017/5/17.
 */

public class DemoAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public DemoAdapter(List<String> data) {
        super(R.layout.demo_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_text,item);
    }
}
