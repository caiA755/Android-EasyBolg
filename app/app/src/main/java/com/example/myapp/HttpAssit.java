package com.example.myapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

class HttpAssist {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 10000000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    public static final String SUCCESS = "1";
    public static final String FAILURE = "0";

//    public static String uploadFile(File file) {
//        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
//        String PREFIX = "--", LINE_END = "\r\n";
//        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
//        String RequestURL = "http://101.35.54.74/myimg";
//        try {
//            URL url = new URL(RequestURL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(TIME_OUT);
//            conn.setConnectTimeout(TIME_OUT);
//            conn.setDoInput(true); // 允许输入流
//            conn.setDoOutput(true); // 允许输出流
//            conn.setUseCaches(false); // 不允许使用缓存
//            conn.setRequestMethod("POST"); // 请求方式
//            conn.setRequestProperty("Charset", CHARSET); // 设置编码
//            conn.setRequestProperty("connection", "keep-alive");
//            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
//                    + BOUNDARY);
//            if (file != null) {
//                /**
//                 * 当文件不为空，把文件包装并且上传
//                 */
//                OutputStream outputSteam = conn.getOutputStream();
//
//                DataOutputStream dos = new DataOutputStream(outputSteam);
//                StringBuffer sb = new StringBuffer();
//                sb.append(PREFIX);
//                sb.append(BOUNDARY);
//                sb.append(LINE_END);
//                /**
//                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
//                 * filename是文件的名字，包含后缀名的 比如:abc.png
//                 */
//
//                sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""
//                        + file.getName() + "\"" + LINE_END);
//                sb.append("Content-Type: application/octet-stream; charset="
//                        + CHARSET + LINE_END);
//                sb.append(LINE_END);
//                dos.write(sb.toString().getBytes());
//                InputStream is = new FileInputStream(file);
//                byte[] bytes = new byte[1024];
//                int len = 0;
//                while ((len = is.read(bytes)) != -1) {
//                    dos.write(bytes, 0, len);
//                }
//                is.close();
//                dos.write(LINE_END.getBytes());
//                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
//                        .getBytes();
//                dos.write(end_data);
//                dos.flush();
//                /**
//                 * 获取响应码 200=成功 当响应成功，获取响应的流
//                 */
//                int res = conn.getResponseCode();
//                if (res == 200) {
//                    return SUCCESS;
//                }
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return FAILURE;
//    }
    //转uri to file
public static class PermissionUtils {
    //这是要申请的权限
    private static String[] PERMISSIONS_CAMERA_AND_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    /**
     * 解决安卓6.0以上版本不能读取外部存储权限的问题
     *
     * @param activity
     * @param requestCode
     * @return
     */
    public  boolean isGrantExternalRW(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int storagePermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int cameraPermission = activity.checkSelfPermission(Manifest.permission.CAMERA);
            //检测是否有权限，如果没有权限，就需要申请
            if (storagePermission != PackageManager.PERMISSION_GRANTED ||
                    cameraPermission != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                activity.requestPermissions(PERMISSIONS_CAMERA_AND_STORAGE, requestCode);
                //返回false。说明没有授权
                return false;
            }
        }
        //说明已经授权
        return true;
    }
}
}