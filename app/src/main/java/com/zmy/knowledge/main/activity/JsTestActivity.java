package com.zmy.knowledge.main.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.utlis.AUtils;
import com.zmy.knowledge.utlis.SPUtils;

import java.io.File;

/**
 * Created by win7 on 2017/5/24.
 * 这里是一个JavaScript测试页面 用来测试JavaScript方法
 */

public class JsTestActivity extends BaseActivity {
    WebView mWebView;
    TextView tv_title;

//    public String url = "http://192.168.0.129:8080/cloud/Test.html";

    public String url = "http://192.168.0.129:8080/MyServer";

    @Override
    protected int getLayoutId() {
        return R.layout.web_activity;
    }

    protected WebSettings webSettings;

    @Override
    protected void initViews(ViewHolder holder, View root) {
        tv_title = holder.get(R.id.tv_title);
        mWebView = holder.get(R.id.webview);
        webSettings = mWebView.getSettings();
        // webSettings.setLoadsImagesAutomatically(true);
        mWebView.addJavascriptInterface(this, "webactivity");
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
//		mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowContentAccess(true);
        webSettings.setBuiltInZoomControls(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSaveFormData(false);
        mWebView.getSettings().setSavePassword(false);
        mWebView.getSettings().setLightTouchEnabled(true);

        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                AUtils.log("url=" + url);
                view.loadUrl(url);
                return true;
            }
        });


        mWebView.setWebChromeClient(client);

    }
    /**
     * 处理浏览器事件
     */
    private WebChromeClient client = new WebChromeClient() {
        @Override
        public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean dialog,
                                      boolean userGesture, Message resultMsg) {
            return super.onCreateWindow(view, dialog, userGesture, resultMsg);
        }

        public boolean onJsBeforeUnload(WebView view, String url,
                                        String message, JsResult result) {
            return super.onJsBeforeUnload(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   final JsResult result) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(
                    view.getContext());

            builder.setTitle("提示")
                    .setMessage(message)
                    .setPositiveButton(
                            "确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    result.confirm();
                                    dialog.dismiss();
                                }
                            })
                    .setNeutralButton(
                            "取消",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    result.cancel();
                                    dialog.dismiss();
                                }
                            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    result.cancel();
                }
            });

            // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    return true;
                }
            });
            // 禁止响应按back键的事件
            // builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
            // return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, final JsPromptResult result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            builder.setTitle("提示").setMessage(message);

            final EditText et = new EditText(view.getContext());
            et.setSingleLine();
            et.setText(defaultValue);
            builder.setView(et)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm(et.getText().toString());
                            dialog.dismiss();
                        }

                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                            dialog.dismiss();
                        }
                    });

            // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return true;
                }
            });

            // 禁止响应按back键的事件
            // builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
            // return super.onJsPrompt(view, url, message, defaultValue,
            // result);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            /**
             * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
             */
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            builder.setTitle("提示")
                    .setMessage((message + "").trim())
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                            dialog.dismiss();
                        }
                    });

            // 不需要绑定按键事件
            // 屏蔽keycode等于84之类的按键
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return true;
                }
            });
            // 禁止响应按back键的事件
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
//            result.confirm();
//            result.cancel();
            // 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
            return true;
            // return super.onJsAlert(view, url, message, result);
        }

        @Override
        public void onProgressChanged(WebView view, int progress) {
//            if (centerTextview != null) {
//                centerTextview.setText("页面加载中，请稍候..." + progress + "%");
//                setProgress(progress * 100);
//                if (progress == 100) {
//                    centerTextview.setText(mWebView.getTitle());
//                }
//            }
        }


        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {

            AUtils.log("选择文件3.0");
//            if (mUploadMessage != null)
//                return;
//            mUploadMessage = uploadMsg;
//            startActivityForResult(createDefaultOpenableIntent(),
//                    FILECHOOSER_RESULTCODE);
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "");
            AUtils.log("选择文件3.0");
        }

        // For Android > 4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileChooser(uploadMsg, acceptType);

        }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            AUtils.log("选择文件5.0");
//            if (mUploadMessage != null) {
//                mUploadMessage.onReceiveValue(null);
//            }
//            mUploadMessageArray = filePathCallback;
//            if (Build.VERSION.SDK_INT >= 23) { //检测权限
//                PermissionUtils.requestPermission(BaseWebActivity.this, PermissionUtils.CODE_READ_EXTERNAL_STORAGE, permissionGrant);
//            } else {
//                startActivityForResult(createDefaultOpenableIntent(), FILECHOOSER_RESULTCODE);
//            }


            return true;
        }

        private Intent createDefaultOpenableIntent() {

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");

            Intent chooser = createChooserIntent(createCameraIntent());
            chooser.putExtra(Intent.EXTRA_INTENT, i);
            return chooser;
        }

        private Intent createChooserIntent(Intent... intents) {
            Intent chooser = new Intent(Intent.ACTION_CHOOSER);
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
            chooser.putExtra(Intent.EXTRA_TITLE, "选择文件");
            return chooser;
        }

        private Intent createCameraIntent() {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File externalDataDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File cameraDataDir = new File(externalDataDir.getAbsolutePath()
                    + File.separator + "browser-photos");
            cameraDataDir.mkdirs();
//            mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator
//                    + System.currentTimeMillis() + ".jpg";
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                    Uri.fromFile(new File(mCameraFilePath)));
            Log.e("TEST", "DATA:->" + cameraIntent.getData());
            return cameraIntent;
        }

        private Intent createCamcorderIntent() {
            return new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        }

        private Intent createSoundRecorderIntent() {
            return new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        }


        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

        }
    };


    @JavascriptInterface
    public void getPhoneNumber() {


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String userPhone = SPUtils.getUserPhone(JsTestActivity.this);
                mWebView.loadUrl("javascript:getPhoneNumberResult('" + userPhone + "');");
            }
        });


    }

    /**
     * 震动1次
     */
    @JavascriptInterface
    public void vibrator() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 400, 100, 400};   // 停止 开启 停止 开启
        vibrator.vibrate(pattern, -1);           //震动一次，index设为-1
    }

    @JavascriptInterface
    public void toast() {
        AUtils.showToast("js接收到了电话，并且回调了方法");
    }

}
