package com.zmy.knowledge.upload;

import android.util.Log;

import com.zmy.knowledge.utlis.AUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class UploadFile {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 1000000; //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
    private static final String BOUNDARY = UUID.randomUUID().toString(); //UUID.randomUUID().toString(); //边界标识 随机生成 String PREFIX = "--" , LINE_END = "\r\n";
    private static final String PREFIX = "--";
    private static final String LINE_END = "\n";
    private static final String CONTENT_TYPE = "multipart/form-data"; //内容类型

    //进度回调接口
    public interface UpProgress {
        void onUpProgress(float total, float current);
    }

    //接口变量
    public static UpProgress mUpProgress;


    /**
     * android上传文件到服务器
     *
     * @param file 需要上传的文件
     * @return 返回响应的内容
     */
    public static boolean uploadFile(File file, UpProgress upProgress) {
        mUpProgress = upProgress;
        String requestURL = "http://qn.winfreeinfo.com:2234/weboa/km/kmattach.nsf/FileUploadForm?CreateDocument";
        String name = "%%File.48257f7900293e55.86ec149b6a75b27848257a4700542d89.$Body.0.1E6";
        String type = URLConnection.guessContentTypeFromName(file.getName());
        AUtils.log("文件类型=" + type);
        try {
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false); //不允许使用缓存
            conn.setRequestMethod("POST"); //请求方式
            conn.setRequestProperty("Charset", CHARSET);//设置编码
            //头信息
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Cookie", AUtils.getCookieValue());
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if (file != null) {
                /** * 当文件不为空，把文件包装并且上传 */
                OutputStream outputSteam = conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputSteam);
//                String[] params = {"\"Content-Type\"", "\"Content-Length\""};
//                String[] values = {type, file.length() + ""};
                //添加docName,docType,sessionKey,sig参数
//                for (int i = 0; i < params.length; i++) {
//                    //添加分割边界
//                    StringBuffer sb = new StringBuffer();
//                    sb.append(PREFIX);
//                    sb.append(BOUNDARY);
//                    sb.append(LINE_END);
//
//                    sb.append("Content-Disposition: form-data; name=" + name + ";filename=" + file.getName() + LINE_END);
//                    sb.append(LINE_END);
//                    sb.append(values[i]);
//                    sb.append(LINE_END);
//                    dos.write(sb.toString().getBytes());
//                }

                //file内容
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                sb.append("Content-Disposition: form-data; name=\"" + name + "\"; filename=" + "\"" + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: " + type + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                //读取文件的内容
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                long i = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                    if (mUpProgress != null) {//进度回调
                        mUpProgress.onUpProgress(file.length() + 0.5f, (i += len) + 0.5f);
                    }
                }
                is.close();
                //写入文件二进制内容
                dos.write(LINE_END.getBytes());
                //写入end data
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功
                 * 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                Log.e(TAG, "response code:" + res);
                if (res == 200) {
                    String oneLine;
                    StringBuffer response = new StringBuffer();
                    BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((oneLine = input.readLine()) != null) {
                        response.append(oneLine);
                    }
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}