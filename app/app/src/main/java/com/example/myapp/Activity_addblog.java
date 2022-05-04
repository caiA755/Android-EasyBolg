package com.example.myapp;

import static com.example.myapp.ActivityIndex.ip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Activity_addblog extends AppCompatActivity {
private  String uid;
private ImageView imageView;
private Button btnUpload,btnPublish;
private EditText txtTitle,txtCon;
private  TextView txtReturn;
private static String addArtUrl=ip+"api/Article/InsertArticle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new HttpAssist.PermissionUtils().isGrantExternalRW(this, 1);
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_addblog);
        imageView=findViewById(R.id.myimg);
        btnUpload=findViewById(R.id.btnUploadImg);
        btnPublish=findViewById(R.id.btnPublish);
        txtCon=findViewById(R.id.txtCon);
        txtTitle=findViewById(R.id.txtSearch);
        txtReturn=findViewById(R.id.btnReturn);
        //获取UID
        Intent intent = getIntent();
        if (intent != null) {
            uid = intent.getStringExtra("UID").toString();
        }
        //返回界面
        txtReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Activity_addblog.this,ActivityIndex.class);
                i.putExtra("UID",uid);
                i.putExtra("index","2");
                startActivity(i);
            }
        });

        //图片上传
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
            }
        });
        //发布文章

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=txtTitle.getText().toString().trim();
                String content=txtCon.getText().toString().trim();
                if(title.equals("")){
                    Toast.makeText(Activity_addblog.this,"标题不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }else if(content.equals("")){
                    Toast.makeText(Activity_addblog.this,"内容不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                //postImg暂未添加
                String Articleurl=addArtUrl+"?Title="+title+"&&Content="+content+"&&Flag="+"1"+"&&PostImg="+"111"+"&&UID="+uid;
                RequestQueue requestQueue= Volley.newRequestQueue(Activity_addblog.this);
                StringRequest stringRequest=new StringRequest(Request.Method.GET, Articleurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Gson gson=new Gson();
//                        Boolean result = gson.fromJson(response, new TypeToken<Boolean>(){}.getType());//把JSON格式的字符串转为List
                        if(response.equals("true")){
                            Toast.makeText(Activity_addblog.this,"发布成功！！",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(Activity_addblog.this,ActivityIndex.class);
                            i.putExtra("UID",uid);
                            i.putExtra("index","2");
                            startActivity(i);
                        }else{
                            Toast.makeText(Activity_addblog.this,"发布失败！！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                requestQueue.add(stringRequest);
            }
        });

    }
    @SuppressLint("MissingSuperCall")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                File file=uriToFile(uri);
             // String result= uploadFile(file.getName().toString(),file.getPath(),file);
              //if (result.equals("1")){
                  imageView.setImageURI(uri);
              //}


            }
        }
    }
    private File uriToFile(Uri uri) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = getContentResolver().query(uri, proj, null,
                null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor
                    .getString(actual_image_column_index);
        }
        File file = new File(img_path);
        return file;
    }
    private String actionUrl = "http://101.35.54.74/myimg";
    @SuppressLint("NewApi")
    public String uploadFile(String newName,String uploadFile,File file) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            URL url = new URL(actionUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);

            // 设置http连接属性
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; "
                    + "name=\"file1\";filename=\"" + newName + "\"" + end);
            ds.writeBytes(end);

            // 取得文件的FileInputStream

             FileInputStream fStream = new FileInputStream(file);;
            /* 设置每次写入1024bytes */
            byte[] bytes = new byte[1024];
            int hasRead = fStream.read(bytes);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1) {
                /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

            fStream.close();
            ds.flush();
            /* 取得Response内容 */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            /* 关闭DataOutputStream */
            ds.close();
            /* 将Response显示于Dialog */
            return "1";
        } catch (Exception e) {
            return "0"+e;
        }
    }
}