package com.zmy.knowledge.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmy.knowledge.R;
import com.zmy.knowledge.utlis.AUtils;

/**
 * Created by win7 on 2017/5/16.
 */

public class MyActionBar extends FrameLayout implements View.OnClickListener {
    TextView tv_title;
    Button btn_ok;
    Button btn_back;
    private Context mContext;
    onActionBarClickListener mListener;

    public MyActionBar(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public MyActionBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public MyActionBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.actionbar, null);
        btn_back = (Button) view.findViewById(R.id.btn_back);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        btn_ok = (Button) view.findViewById(R.id.btn_ok);
        setBackText("返回");
        btn_back.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        addView(view);


    }

    public void setBackText(String text) {
        btn_back.setText(mContext.getString(R.string.ttf_back) + text);
        btn_back.setTypeface(AUtils.getTTF());
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    public void hideBack() {
        btn_back.setVisibility(GONE);
    }

    public void hideOk() {
        btn_ok.setVisibility(GONE);
    }
    public void hideBtn(){
        hideBack();
        hideOk();
    }


    public void setActionBarClickListener(onActionBarClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                if (null != mListener)
                    mListener.onBackClick();
                break;
            case R.id.btn_ok:
                if (null != mListener)
                    mListener.onOkClick();
                break;
        }
    }

    public interface onActionBarClickListener {
        void onOkClick();

        void onBackClick();
    }

}
