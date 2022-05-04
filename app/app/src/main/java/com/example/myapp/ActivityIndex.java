package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityIndex extends AppCompatActivity {
    private TabHost mytabhost;
    private ListView listView,listViewB;
    private  TextView txt,txtUsername,txtSearchMycon,btnAddBlog,txtPhone;
    private  RoundRectImageView imageView,myContent1,MyContent2;
    private Button btnSearch,btnOutLogin;
    private EditText searchTxt;
    private  ImageView touxiang;
    private LinearLayout myview;
    private int[] layRes={R.id.index,R.id.blog,R.id.selfinfo,R.id.about};
    private  LinearLayout mLayout;
    private  List<Article>  jsonListObject=null;
    private  String currentIndex="0";
    private  static String username;
    String uid=null;
    public static  String ip="http://192.168.128.224/";
    public static  String url=ip+"api/Article/get";
    public static  String SearchUrl=ip+"api/Article/GetArticleBykey";
    public static  String  MyConUrl=ip+"api/Article/GetArticleByUID";
    public static  String  GetUserNameUrl=ip+"api/User/GetNickName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //初始化组件
         getComponent();
         myview=findViewById(R.id.myview);
        //个人中心
        //获取个人信息
        Intent intent = getIntent();
        if (intent != null) {
             uid = intent.getStringExtra("UID").toString();
             currentIndex=intent.getStringExtra("index");
          //   username=intent.getStringExtra("Username");
        }
        String getNickname=GetUserNameUrl+"?UID="+uid;
        //获取个人信息
        StringRequest stringRequest=new StringRequest(Request.Method.GET,getNickname ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                txtUsername.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error.printStackTrace();
                //Toast.makeText(ActivityIndex.this,error.toString(),Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue= Volley.newRequestQueue(ActivityIndex.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        requestQueue.add(stringRequest);
        //设置标题

        //获取json数据，显示博客列表
        getArticleInfo1();
      //  getArticleInfo2();
        getSelfArticleInfo3();
        //current index;

        //设置菜单
        setMeun();
        //查询:模糊查询按姓名 文章标题 文章内容
        searchTxt=(EditText)findViewById(R.id.txtSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlSearch=SearchUrl+"?keyword="+searchTxt.getText().toString().trim();
                RequestQueue requestQueue= Volley.newRequestQueue(ActivityIndex.this);
                StringRequest stringRequest=new StringRequest(Request.Method.GET, urlSearch, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson=new Gson();
                        List<Article>  jsonListObject = gson.fromJson(response, new TypeToken<List<Article> >(){}.getType());//把JSON格式的字符串转为List
                        //博客界面
                        RecyclerView secondrecyclerView= (RecyclerView) findViewById(R.id.secondRecycler);
                        //设置layoutManager
                        secondrecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                        //设置adapter
                        MasonryAdapter sencondAdapter=new MasonryAdapter(jsonListObject,getApplicationContext(),R.layout.list);
                        secondrecyclerView.setAdapter(sencondAdapter);
                        sencondAdapter.setOnItemClickListener(new MasonryAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view) {
                                int position = secondrecyclerView.getChildAdapterPosition(view);
                               // Toast.makeText(ActivityIndex.this, "onItemClick : " + position+"aid:"+jsonListObject.get(position).getAID(), Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(ActivityIndex.this,Activity_detail.class);
                                i.putExtra("AID",jsonListObject.get(position).getAID()+"");
                                startActivity(i);
                            }

                            @Override
                            public void onItemLongClick(View view) {

                            }
                        });
                        //设置item之间的间隔
                        SpacesItemDecoration secondDecoration=new SpacesItemDecoration(0);
                        secondrecyclerView.addItemDecoration(secondDecoration);
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
        //查询我的文章、博客
        txtSearchMycon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ActivityIndex.this,Activity_search_mycon.class);
                i.putExtra("UID",uid);
                startActivity(i);
            }
        });
        //退出登录
        btnOutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ActivityIndex.this,ActivityLogin.class);
                Toast.makeText(ActivityIndex.this,"退出成功",Toast.LENGTH_SHORT);
                startActivity(i);
            }
        });
        //添加博客，跳转界面
        btnAddBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ActivityIndex.this,Activity_addblog.class);
                i.putExtra("UID",uid);
                startActivity(i);
            }
        });
        //在线客服拨号
        txtPhone=findViewById(R.id.txtPhone);
        txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加就是执行动作要操作的数据
                Intent Intent_phone=new Intent();
                Intent_phone.setData(Uri.parse("tel:18390123380"));
                //启动
                startActivity(Intent_phone);
            }
        });
    }
    public View composeLayout(String s, int i) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        ImageView iv = new ImageView(this);
        iv.setImageResource(i);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(60,10, 0, 0);
        layout.addView(iv, lp);
        TextView tv = new TextView(this);
        tv.setGravity(Gravity.LEFT);
        tv.setSingleLine(true);
        tv.setText(s);
        tv.setTextColor(Color.GRAY);
        tv.setTextSize(14);
        tv.setPadding(40, 5, 0, 0);;
        layout.addView(tv, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return layout;
    }

    @SuppressLint("WrongViewCast")
    public void getComponent(){
        imageView= (RoundRectImageView) findViewById(R.id.img);
        btnSearch=(Button) findViewById(R.id.btnSearch);
        txtUsername=(TextView) findViewById(R.id.txtUsername);

        touxiang=findViewById(R.id.touxiang);
        txtSearchMycon=findViewById(R.id.searchMyCon);
        btnOutLogin=findViewById(R.id.btnOutLogin);
        btnAddBlog=findViewById(R.id.btnAddBlog);
        mLayout=findViewById(R.id.blog);
//        touxiang.bringToFront();
      //  txt=findViewById(R.id.txt);
       //listView=(ListView)findViewById(R.id.myList);
       // listViewB=(ListView)findViewById(R.id.myListB);
    }
    public  void  getArticleInfo1(){
       RequestQueue requestQueue1= Volley.newRequestQueue(ActivityIndex.this);
        String urlTop=ip+"api/Article/GetTop";
       StringRequest stringRequest1=new StringRequest(Request.Method.GET, urlTop, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               Gson gson=new Gson();
               jsonListObject = gson.fromJson(response, new TypeToken<List<Article> >(){}.getType());//把JSON格式的字符串转为List
//               ArrayList<Map<String,Object>> arrayList=new ArrayList<Map<String,Object>>();

//                for (Article p : jsonListObject) {
//                    Map<String,Object> map=new HashMap<String,Object>();
//                    map.put("title",p.getTitle());
//                    map.put("aid",p.getAID());
//                    map.put("content",p.getContent());
//                    map.put("publishTime",p.getTime());
//                    map.put("author",p.getAuthor());
//                    arrayList.add(map);
//                }
////                ArrayList<Map<String,Object>> list=arrayList;
//                SimpleAdapter simpleAdapter=new SimpleAdapter(ActivityIndex.this,list,R.layout.list,new String[]{"title","content","aid","publishTime","author"},new int[]{R.id.title,R.id.content,R.id.aid,R.id.publishTime,R.id.author});
//                listView.setAdapter(simpleAdapter);
               //主页
               RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler);

               //设置layoutManager
               recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
               //设置adapter
               MasonryAdapter adapter=new MasonryAdapter(jsonListObject,getApplicationContext(),R.layout.list);

               recyclerView.setAdapter(adapter);

               adapter.setOnItemClickListener(new MasonryAdapter.OnItemClickListener() {
                   @Override
                   public void onItemClick(View view) {
                       int position = recyclerView.getChildAdapterPosition(view);
                     //  Toast.makeText(ActivityIndex.this, "onItemClick : " + position+"aid:"+jsonListObject.get(position).getAID(), Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(ActivityIndex.this,Activity_detail.class);
                        i.putExtra("AID",jsonListObject.get(position).getAID()+"");
                        startActivity(i);
                   }

                   @Override
                   public void onItemLongClick(View view) {

                   }
               });
//               recyclerView.setOnTouchListener(new View.OnTouchListener() {
//                   @Override
//                   public boolean onTouch(View v, MotionEvent event) {
//                       if (event.getAction() == MotionEvent.ACTION_UP) {
//                           mLayout.performClick();  //模拟父控件的点击
//                       }
//                       return false;
//                   }
//               });
//               //设置item之间的间隔
               SpacesItemDecoration decoration=new SpacesItemDecoration(0);
               recyclerView.addItemDecoration(decoration);
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
           }
       });
       requestQueue1.add(stringRequest1);
   }
    public  void  getArticleInfo2(){
        RequestQueue requestQueue= Volley.newRequestQueue(ActivityIndex.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                jsonListObject = gson.fromJson(response, new TypeToken<List<Article> >(){}.getType());//把JSON格式的字符串转为List
               //博客界面
                RecyclerView secondrecyclerView= (RecyclerView) findViewById(R.id.secondRecycler);
                //设置layoutManager
                secondrecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                //设置adapter
                MasonryAdapter sencondAdapter=new MasonryAdapter(jsonListObject,getApplicationContext(),R.layout.list);
                secondrecyclerView.setAdapter(sencondAdapter);
                sencondAdapter.setOnItemClickListener(new MasonryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view) {
                        int position = secondrecyclerView.getChildAdapterPosition(view);
                       // Toast.makeText(ActivityIndex.this, "onItemClick : " + position+"aid:"+jsonListObject.get(position).getAID(), Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(ActivityIndex.this,Activity_detail.class);
                        i.putExtra("AID",jsonListObject.get(position).getAID()+"");
                        startActivity(i);
                    }

                    @Override
                    public void onItemLongClick(View view) {

                    }
                });
                //设置item之间的间隔
                SpacesItemDecoration secondDecoration=new SpacesItemDecoration(0);
                secondrecyclerView.addItemDecoration(secondDecoration);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }
    public  void getSelfArticleInfo3(){
        String url=MyConUrl+"?UID="+uid;
        RequestQueue requestQueue= Volley.newRequestQueue(ActivityIndex.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                List<Article>  jsonListObject = gson.fromJson(response, new TypeToken<List<Article> >(){}.getType());//把JSON格式的字符串转为List
                //主页
                RecyclerView recyclerView= (RecyclerView) findViewById(R.id.MyContent1);
                //设置layoutManager
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                //设置adapter
                MasonryAdapter adapter=new MasonryAdapter(jsonListObject,getApplicationContext(),R.layout.activity_mycon);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new MasonryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view) {
                        int position = recyclerView.getChildAdapterPosition(view);
                        Intent i=new Intent(ActivityIndex.this,Activity_detail_edit.class);
                        i.putExtra("AID",jsonListObject.get(position).getAID()+"");
                        i.putExtra("UID",uid);
                        startActivity(i);
                    }

                    @Override
                    public void onItemLongClick(View view) {

                    }
                });
                //设置item之间的间隔
                SpacesItemDecoration decoration=new SpacesItemDecoration(0);
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
    public void setMeun(){
         this.mytabhost=(TabHost) super.findViewById(R.id.tabhost);
         this.mytabhost.setup();
         TabHost.TabSpec mytab1=mytabhost.newTabSpec("主页");
         mytab1.setIndicator("主页");
         mytab1.setContent(this.layRes[0]);
         this.mytabhost.addTab(mytab1);

         TabHost.TabSpec mytab2=mytabhost.newTabSpec("博客");
         mytab2.setIndicator("博客");
         mytab2.setContent(this.layRes[1]);
         this.mytabhost.addTab(mytab2);

         TabHost.TabSpec mytab3=mytabhost.newTabSpec("个人");
         mytab3.setIndicator("个人");
         mytab3.setContent(this.layRes[2]);
         this.mytabhost.addTab(mytab3);

         TabHost.TabSpec mytab4=mytabhost.newTabSpec("关于");
         mytab4.setIndicator("关于");
         mytab4.setContent(this.layRes[3]);
         this.mytabhost.addTab(mytab4);
         this.mytabhost.setCurrentTab(Integer.parseInt(currentIndex));
        mytabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
              if(s=="个人"){
                  getSelfArticleInfo3();
              }
              if(s=="主页"){
                 getArticleInfo1();
              }
              if(s=="博客"){
                  getArticleInfo2();
              }
            }
        });
     }
}