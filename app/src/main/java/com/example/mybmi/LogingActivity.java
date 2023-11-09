package com.example.mybmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogingActivity extends AppCompatActivity {
    EditText Login_Account;
    EditText Login_Password;
    TextView Login_result;
    Button Login_Sure;
    String result;
    String Passwordstr;
    String Accountstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);
        result=" ";
        Login_Account = (EditText) findViewById(R.id.Login_Account);//可以測試一下R.id.Account
        Login_Password = (EditText) findViewById(R.id.Login_Password);
        Login_result = (TextView) findViewById(R.id.Login_result);
        Login_Sure = (Button) findViewById(R.id.Login_Sure);

        Login_Sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Accountstr = Login_Account.getText().toString();
                Passwordstr = Login_Password.getText().toString();
                String url = "http://140.136.151.144/LoginApi.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result = response.trim();
                        Login_result.setText(result);
                        if(result.equals("success")) {
                            Intent intent = new Intent();
                            intent.setClass(LogingActivity.this, InitialActivity.class);
                            Toast.makeText(LogingActivity.this, "登入成功", Toast.LENGTH_LONG).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("KEY_userinfo", Accountstr);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(LogingActivity.this, "帳號或密碼錯誤", Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LogingActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Login_result.setText(error.toString());
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("LoginName", Accountstr);//和php的參數做連結
                        params.put("LoginPassword", Passwordstr);
                        return params;

                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(LogingActivity.this);
                requestQueue.add(stringRequest);

            }

        });

    }
}