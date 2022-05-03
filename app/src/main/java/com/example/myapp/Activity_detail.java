package com.example.myapp;

import static com.example.myapp.ActivityIndex.ip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.List;

public class Activity_detail extends AppCompatActivity {
private  String aid;
private TextView txtTitle,txtAuthor,txtTime,txtContent;
private TextView btnReturn;
private ImageView imageView;
private static String ArtDetailUrl=ip+"api/Article/GetArticleDetail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        txtTitle=findViewById(R.id.title);
        txtAuthor=findViewById(R.id.author);
        txtTime=findViewById(R.id.time);
        txtContent=findViewById(R.id.content);
        imageView=findViewById(R.id.myimg);
        btnReturn=(TextView) findViewById(R.id.btnReturn);
        Intent myintent=this.getIntent();
        if (myintent!=null){
           aid = myintent.getStringExtra("AID");
        }
        String detailUrl=ArtDetailUrl+"?AID="+aid;
        RequestQueue requestQueue= Volley.newRequestQueue(Activity_detail.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, detailUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                Article result = gson.fromJson(response, Article.class);
                txtTitle.setText(result.getTitle());
                txtAuthor.setText(result.getAuthor());
                txtContent.setText(result.getContent());
                txtTime.setText(result.getTime());
                Glide.with(Activity_detail.this).load(result.getPostImg()).into(imageView);
                //Toast.makeText(Activity_detail.this,result.toString() ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });
    }
}