package com.zmy.knowledge.main.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.utlis.AUtils;
import com.zmy.knowledge.wps.WpsModel;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by win7 on 2017/6/28.
 */

public class PdfWordTestActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.pdf_word;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {

    }

    boolean isHttp=false;

    public void openWord(View v) {
        String url = "http://192.168.0.129:8080/test.docx";
        if(isHttp){
            openFileWpsHttp(url);
        }else {
            loadFile(url);
        }


    }

    public void openExcel(View v) {
        String url = "http://192.168.0.129:8080/test.xlsx";
        if(isHttp){
            openFileWpsHttp(url);
        }else {
            loadFile(url);
        }
    }

    public void openPdf(View v) {

        String url = "http://192.168.0.129:8080/test.pdf";
        if(isHttp){
            openFileWpsHttp(url);
        }else {
            loadFile(url);
        }
//        startActivity(new Intent(this, PdfActivity.class));
    }

    public void loadFile(String url) {
        OkGo.<File>get(url)
                .tag(this)
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        AUtils.log("下载完成 " + file.getAbsolutePath());
                        openFile(file);
//                        openFileWps2(file);

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


    /**
     * 打开文件
     *
     * @param file
     */
    String types = "";

    private void openFile(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //获取文件file的MIME类型
        types = getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(Uri.fromFile(file), types);
        //跳转
        startActivity(intent);

    }

    public void openFileWpsHttp(String url){
Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri
                 = Uri.parse(url);
        intent.setData(uri);
        startActivity(intent);
    }


    public boolean openFileWps2(File file){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Bundle bundle = new Bundle();
        bundle.putString("OpenMode", "ReadOnly");//只读模式
        bundle.putBoolean("SendSaveBroad", false);                 //Normal：可编辑
        bundle.putBoolean("SendCloseBroad", false);
//        bundle.putString("ThirdPackage", "com.testwps");
        bundle.putBoolean("ClearBuffer", false);
        bundle.putBoolean("ClearTrace", false);
//        bundle.putFloat("ViewScale", 1.0f);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setClassName("cn.wps.moffice_eng", "cn.wps.moffice.documentmanager.PreStartActivity2");
//        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/KingsoftOffice/file/说明.doc");
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);

        intent.putExtras(bundle);
        startActivity(intent);
        return true;
    }



    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    @SuppressLint("DefaultLocale")
    private String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    private final String[][] MIME_MapTable = {
            //{后缀名， MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };
}
