package com.zmy.knowledge.main.activity;

import android.view.View;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.utlis.AUtils;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by win7 on 2017/6/28.
 */

public class PdfActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.pdf;
    }
    public void loadFile(String url) {

        OkGo.<File>get(url)
                .tag(this)
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        AUtils.log("下载完成 " + file.getAbsolutePath());
                        pdfView.fromFile(file)
                                .pages(0, 2, 1, 3, 3, 3)
                                .defaultPage(1)
                                .showMinimap(false)
                                .enableSwipe(true)
//                .onDraw(onDrawListener)
                                .onLoad(new OnLoadCompleteListener() {
                                    @Override
                                    public void loadComplete(int nbPages) {

                                    }
                                })
//                .onPageChange(onPageChangeListener)
                                .load();

                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                        AUtils.log("正在下载 " + currentSize);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        AUtils.log("下载错误");
                    }
                });

    }
    PDFView pdfView;
    @Override
    protected void initViews(ViewHolder holder, View root) {

        pdfView = holder.get(R.id.pdfview);
        String url = "http://192.168.0.129:8080/test.pdf";
        loadFile(url);

    }
}
