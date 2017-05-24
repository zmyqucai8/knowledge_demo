package com.zmy.knowledge.upload;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.utlis.AUtils;
import com.zmy.knowledge.utlis.PermissionUtils;

import java.io.File;
import java.net.URLConnection;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by win7 on 2017/5/22.
 * 上传文件
 */

public class UploadActivity extends BaseActivity implements View.OnClickListener {
    TextView text;
    ProgressBar bar;

    @Override
    protected int getLayoutId() {
        return R.layout.upload_activity;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        holder.get(R.id.btn_select).setOnClickListener(this);
        text = holder.get(R.id.text);
        bar = holder.get(R.id.bar);
        bar.setMax(100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select://选择文件
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_EXTERNAL_STORAGE, grant);
                break;
        }
    }

    public static final int FILE_SELECT_CODE = 10086;

    PermissionUtils.PermissionGrant grant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            showFileChooser();
        }
    };

    /**
     * 显示上传的intent
     */
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择要上传的文件"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "请安装一个文件管理器.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String path = AUtils.getPath(this, uri);
                    AUtils.log("path = " + path);
                    //上传文件
//                    uploadFile(path);
                    uploadFile2(path);
//                    uploadFile3(path);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 上传文件的方法
     *
     * @param path
     */
    private void uploadFile(String path) {
        String url = "http://qn.winfreeinfo.com:2234/weboa/km/kmattach.nsf/FileUploadForm?CreateDocument";
        String name = "%%File.48257f7900293e55.86ec149b6a75b27848257a4700542d89.$Body.0.1E6";
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            String contentType = URLConnection.guessContentTypeFromName(file.getName());
            AUtils.log("文件类型= " + contentType);
            MediaType mediaType = MediaType.parse(contentType);
            AUtils.log("媒体类型=" + mediaType.toString());
            OkGo.post(url)
                    .tag(this)
                    .isMultipart(true)
                    .params(name, file)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            bar.setVisibility(View.GONE);
                            //上传成功
                            text.setText("上传成功");
                            AUtils.showToast("上传成功");
                        }

                        @Override
                        public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                            super.upProgress(currentSize, totalSize, progress, networkSpeed);
                            if (bar.getVisibility() == View.GONE) {
                                bar.setVisibility(View.VISIBLE);
                            }
                            int i = (int) (currentSize / totalSize * 100);
                            bar.setProgress(i);
//                            text.setText(i + "%");
                        }
                    });
        }
    }


    @Override
    public void onBackPressed() {
        OkGo.getInstance().cancelTag(this);
        super.onBackPressed();

    }

    public void uploadFile2(String path) {
        initTask(path);
        asyncTask.execute((Object[]) null);

    }

    private AsyncTask asyncTask;

    private void initTask(final String path) {
        asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                boolean response = UploadFile.uploadFile(new File(path), new UploadFile.UpProgress() {
                    @Override
                    public void onUpProgress(float total, float current) {
                        publishProgress((int) (current / total * 100));
                    }
                });
                return response;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                Toast.makeText(HttpMultiPartTestActivity.this, "开始上传", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                AUtils.log((boolean) o ? "上传成功" : "上传失败");
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
//                AUtils.log("上传的进度=" + (int) values[0]);
                if (bar.getVisibility() == View.GONE) {
                    bar.setVisibility(View.VISIBLE);
                }
                text.setText((int) values[0] + "%");

            }
        };
    }


    public void uploadFile3(String path) {
//        String url = "http://qn.winfreeinfo.com:2234/weboa/km/kmattach.nsf/FileUploadForm?CreateDocument";
        String url = "http://app.xindongai.com/v7/Personal/UploadMemberPhoto";
        String name = "%%File.48257f7900293e55.86ec149b6a75b27848257a4700542d89.$Body.0.1E6";
        OkGo.post(url)
                .isMultipart(true)
                .headers("appToken", "7444b9b85b5364022ed5895495c2df2cc530fa17bf49aa544f493d5d047891ec99178ad36f6ffe79")
                .headers("appID", "1005467")
                .params("MemberID", "1005467")
                .params("dataType", "common")
                .params("myPhoto", new File(path)).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                AUtils.showToast(s);
            }
        });
    }
}
