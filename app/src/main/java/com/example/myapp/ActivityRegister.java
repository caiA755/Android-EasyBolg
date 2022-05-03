package com.example.myapp;

import static com.example.myapp.ActivityIndex.ip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ActivityRegister extends AppCompatActivity {
private TextView txtLogin;
private Button btnRegister;
private EditText txtUsername,txtEmail,txtPwd;
    public  static  String RegisterUrl=ip+"api/User/InsertUser";
    public  static  String UserIsExistUrl=ip+"api/User/UserIsExist";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtLogin=findViewById(R.id.txtLogin);
        txtUsername=findViewById(R.id.editUser);
        txtEmail=findViewById(R.id.editEmail);
        txtPwd=findViewById(R.id.editPwd);
        btnRegister=findViewById(R.id.btnReg);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ActivityRegister.this,ActivityLogin.class);
                startActivity(i);
            }
        });
        //注册
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=txtUsername.getText().toString().trim();
                String email=txtEmail.getText().toString().trim();
                String password=txtPwd.getText().toString().trim();
                if(username.equals("")||username.equals(null)){
                    Toast.makeText(ActivityRegister.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }else if(password.equals("")||password.equals(null)){
                    Toast.makeText(ActivityRegister.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                String registerUrl=RegisterUrl+"?username="+username+"&&password="+password+"&&email="+email;
                StringRequest stringRequest=new StringRequest(Request.Method.GET,registerUrl ,new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Gson gson = new Gson();
                            Boolean result = gson.fromJson(response, new TypeToken<Boolean>() {
                            }.getType());
                            if (result == Boolean.TRUE) {
                                //判断用户是否已经存在
                                String userUrl=UserIsExistUrl+"?username="+username;
                                StringRequest stringRequest=new StringRequest(Request.Method.GET,userUrl ,new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Gson gson = new Gson();
                                        Boolean result = gson.fromJson(response, new TypeToken<Boolean>() {
                                        }.getType());
                                        if (result == Boolean.TRUE) {
                                            Toast.makeText(ActivityRegister.this,"该用户名已经存在！请重新输入".toString(),Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(ActivityRegister.this, "注册成功！请登录", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(ActivityRegister.this, ActivityLogin.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    }}, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                        Toast.makeText(ActivityRegister.this,error.toString(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                                RequestQueue requestQueue= Volley.newRequestQueue(ActivityRegister.this);
                                stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
                                requestQueue.add(stringRequest);
                            } else {
                                Toast.makeText(ActivityRegister.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error.printStackTrace();
                            Toast.makeText(ActivityRegister.this,error.toString(),Toast.LENGTH_SHORT).show();
                        }

                    });
                    RequestQueue requestQueue= Volley.newRequestQueue(ActivityRegister.this);
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
                    requestQueue.add(stringRequest);
                }
        });
    }
}