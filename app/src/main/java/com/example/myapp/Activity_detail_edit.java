package com.example.myapp;

import static com.example.myapp.ActivityIndex.ip;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Activity_detail_edit extends AppCompatActivity {
    private  String aid,imgurl,uid;
    private TextView txtTitle,txtAuthor,txtTime,txtContent;
    private TextView btnReturn,btnEdit,btnDel;
    private ImageView imageView;
    private static String ArtDetailUrl=ip+"api/Article/GetArticleDetail";
    private static String DelArtUrl=ip+"api/Article/GetArticleDel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_edit);
        txtTitle=findViewById(R.id.title);
        txtAuthor=findViewById(R.id.author);
        txtTime=findViewById(R.id.time);
        txtContent=findViewById(R.id.content);
        imageView=findViewById(R.id.myimg);
        btnReturn=(TextView) findViewById(R.id.btnReturn);
        btnDel=findViewById(R.id.delCon);
        btnEdit=findViewById(R.id.editCon);
        Intent myintent=this.getIntent();
        if (myintent!=null){
            aid = myintent.getStringExtra("AID");
            uid = myintent.getStringExtra("UID");
        }
        String detailUrl=ArtDetailUrl+"?AID="+aid;
        RequestQueue requestQueue= Volley.newRequestQueue(Activity_detail_edit.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, detailUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                Article result = gson.fromJson(response, Article.class);
                txtTitle.setText(result.getTitle());
                txtAuthor.setText(result.getAuthor());
                txtContent.setText(result.getContent());
                txtTime.setText(result.getTime());
                imgurl=result.getPostImg();
                Glide.with(Activity_detail_edit.this).load(result.getPostImg()).into(imageView);
                //Toast.makeText(Activity_detail.this,result.toString() ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
        //返回界面
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                Intent i=new Intent(Activity_detail_edit.this,ActivityIndex.class);
                i.putExtra("index","2");
                i.putExtra("UID",uid);
                startActivity(i);
            }
        });
        //删除文章
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_detail_edit.this);
                builder.setTitle("提示：");
                builder.setMessage("您确认要删除该文章吗");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String delArtUrl=DelArtUrl+"?AID="+aid;
                        RequestQueue requestQueue= Volley.newRequestQueue(Activity_detail_edit.this);
                        StringRequest stringRequest=new StringRequest(Request.Method.GET, delArtUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Gson gson=new Gson();
                                Boolean result = gson.fromJson(response, new TypeToken<Boolean>(){}.getType());
                                if(result==Boolean.TRUE){
                                    Toast.makeText(Activity_detail_edit.this, "删除成功",Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                    Intent i=new Intent(Activity_detail_edit.this,ActivityIndex.class);
                                    startActivity(i);
                                }else{
                                    Toast.makeText(Activity_detail_edit.this,"删除失败",Toast.LENGTH_SHORT).show();
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
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });
        //编辑文章
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_detail_edit.this,Activity_editpage.class);
                intent.putExtra("AID",aid);
                intent.putExtra("Title",txtTitle.getText());
                intent.putExtra("Content",txtContent.getText());
                intent.putExtra("PostImg",imgurl);
                intent.putExtra("UID",uid);
                startActivity(intent);
            }
        });
    }
}