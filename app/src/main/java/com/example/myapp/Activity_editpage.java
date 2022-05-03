package com.example.myapp;

import static com.example.myapp.ActivityIndex.ip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.bumptech.glide.Glide;

public class Activity_editpage extends AppCompatActivity {
    private  String aid,content,title,img,uid;
    private ImageView imageView;
    private Button btnUpload,btnPublish;
    private EditText txtTitle,txtCon;
    private TextView txtReturn;
    private static String addArtUrl=ip+"api/Article/EditArticle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpage);
        imageView=findViewById(R.id.myimg);
        btnUpload=findViewById(R.id.btnUploadImg);
        btnPublish=findViewById(R.id.btnPedit);
        txtCon=findViewById(R.id.txtCon);
        txtTitle=findViewById(R.id.txtSearch);
        txtReturn=findViewById(R.id.btnReturn);
        //获取UID
        Intent intent = getIntent();
        if (intent != null) {
            aid = intent.getStringExtra("AID").toString();
            content=intent.getStringExtra("Content").toString();
            title=intent.getStringExtra("Title").toString();
            img=intent.getStringExtra("PostImg").toString();
            uid=intent.getStringExtra("UID").toString();
        }
        //显示编辑的内容；
        txtTitle.setText(title);
        txtCon.setText(content);
        Glide.with(Activity_editpage.this).load(img).into(imageView);
        //返回界面
        txtReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Activity_editpage.this,Activity_detail_edit.class);
                startActivity(i);
            }
        });
        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=txtTitle.getText().toString().trim();
                String content=txtCon.getText().toString().trim();
                if(title.equals("")){
                    Toast.makeText(Activity_editpage.this,"标题不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }else if(content.equals("")){
                    Toast.makeText(Activity_editpage.this,"内容不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                //postImg暂未添加
                String Articleurl=addArtUrl+"?Title="+title+"&&Content="+content+"&&AID="+aid;
                RequestQueue requestQueue= Volley.newRequestQueue(Activity_editpage.this);
                StringRequest stringRequest=new StringRequest(Request.Method.GET, Articleurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Gson gson=new Gson();
//                        Boolean result = gson.fromJson(response, new TypeToken<Boolean>(){}.getType());//把JSON格式的字符串转为List
                        if(response.equals("true")){
                            Toast.makeText(Activity_editpage.this,"编辑成功！！",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(Activity_editpage.this,Activity_detail_edit.class);
                            i.putExtra("AID",aid);
                            i.putExtra("UID",uid);
                            i.putExtra("index",2);
                            startActivity(i);
                        }else{
                            Toast.makeText(Activity_editpage.this,"编辑失败！！",Toast.LENGTH_SHORT).show();
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
}