package com.example.myapp;

import static com.example.myapp.ActivityIndex.ip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.List;

public class Activity_search_mycon extends AppCompatActivity {
private  String uid;
private Button btnSearch;
private TextView btnReturn;
private EditText txtSearch;
private static  String  Url=ip+"api/Article/GetArticleByUidTit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_mycon);
         //获取UID
        Intent intent = getIntent();
        if (intent != null) {
            uid = intent.getStringExtra("UID").toString();
        }
        //获取查询的标题

        //返回个人主页
        btnReturn=findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Activity_search_mycon.this,ActivityIndex.class);
                i.putExtra("index","2");
                i.putExtra("UID",uid);
                startActivity(i);
            }
        });
        //
        txtSearch=findViewById(R.id.txtSearch);
        btnSearch=findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new MyListener());
    }
    class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            //处理逻辑
            if((txtSearch.getText().toString().trim()).equals("")){
                Toast.makeText(Activity_search_mycon.this,"输入不能为空！",Toast.LENGTH_SHORT).show();
                return;
            }
            String url=Url+"?UID="+uid+"&&Title="+txtSearch.getText().toString().trim();
            RequestQueue requestQueue= Volley.newRequestQueue(Activity_search_mycon.this);
            StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson=new Gson();
                    List<Article> jsonListObject = gson.fromJson(response, new TypeToken<List<Article> >(){}.getType());//把JSON格式的字符串转为List
                    //主页
                    RecyclerView recyclerView= (RecyclerView) findViewById(R.id.SearchCon);
                    //设置layoutManager
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                    //设置adapter
                    MasonryAdapter adapter=new MasonryAdapter(jsonListObject,getApplicationContext(),R.layout.activity_mycon);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new MasonryAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view) {
                            int position = recyclerView.getChildAdapterPosition(view);
                           // Toast.makeText(ActivityIndex.this, "onItemClick : " + position+"aid:"+jsonListObject.get(position).getAID(), Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(Activity_search_mycon.this,Activity_detail_edit.class);
                            i.putExtra("AID",jsonListObject.get(position).getAID()+"");
                            i.putExtra("UID",uid);
                            startActivity(i);
                        }

                        @Override
                        public void onItemLongClick(View view) {

                        }
                    });
                    //设置item之间的间隔
                    SpacesItemDecoration decoration=new SpacesItemDecoration(6);
                    recyclerView.addItemDecoration(decoration);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            requestQueue.add(stringRequest);
        }
    }
}