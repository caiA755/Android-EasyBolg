package com.example.myapp;

import static com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
import static com.android.volley.DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
import static com.example.myapp.ActivityIndex.ip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityLogin extends AppCompatActivity {
    private TextView txtReg;
    private EditText txtUser,txtPwd;
    private Button btnLogin;
    public  static  String LoginUrl=ip+"api/User/UserLogin";
    public  static  String GetUIDUrl=ip+"api/User/GetUID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        super.onRestart();
        setContentView(R.layout.activity_login);
        txtReg=findViewById(R.id.txtRegister);
        btnLogin=findViewById(R.id.btnLogin);
        txtUser=(EditText)findViewById(R.id.editUser);
        txtPwd=(EditText) findViewById(R.id.editPwd);
        txtReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ActivityLogin.this,ActivityRegister.class);
                startActivity(i);
            }
        });
        //登录
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pwd=txtPwd.getText().toString().trim();
                String username=txtUser.getText().toString().trim();
                String loginUrl=LoginUrl+"?username="+username+"&&pwd="+pwd;
                StringRequest stringRequest=new StringRequest(Request.Method.GET,loginUrl ,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            Gson gson=new Gson();
                            Boolean result = gson.fromJson(response, new TypeToken<Boolean>(){}.getType());
                            if(result==Boolean.TRUE){
                                Intent i=new Intent(ActivityLogin.this,ActivityIndex.class);
                                String GetUIDurl=GetUIDUrl+"?username="+username;
                                StringRequest stringRequest=new StringRequest(Request.Method.GET,GetUIDurl ,new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        i.putExtra("UID",response+"");
                                      //  i.putExtra("Username",username+"");
                                        i.putExtra("index","0");
                                        startActivity(i);

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // error.printStackTrace();
                                      //  Toast.makeText(ActivityLogin.this,error.toString(),Toast.LENGTH_SHORT).show();
                                    }

                                });
                                RequestQueue requestQueue= Volley.newRequestQueue(ActivityLogin.this);
                                stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
                                requestQueue.add(stringRequest);


                               // finish();
                            }else{
                                Toast.makeText(ActivityLogin.this,"您输入的账号或密码错误",Toast.LENGTH_SHORT).show();
                            }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         // error.printStackTrace();
                        Toast.makeText(ActivityLogin.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }

                });
//                {
//                    @Override
//                    protected Map<String, String> getParams() throws   AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("username",username);
//                    params.put("pwd",pwd);
//                    return params;
//                }
//                };
               // stringRequest.setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue= Volley.newRequestQueue(ActivityLogin.this);
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
                requestQueue.add(stringRequest);
            }
        });
    }
    public String GetUID(String url){
        final String[] result1 = {null};
        StringRequest stringRequest=new StringRequest(Request.Method.GET,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                result1[0] = gson.fromJson(response, new TypeToken<String>(){}.getType());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error.printStackTrace();
                Toast.makeText(ActivityLogin.this,error.toString(),Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue= Volley.newRequestQueue(ActivityLogin.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        requestQueue.add(stringRequest);
        return  result1[0];
    }
}