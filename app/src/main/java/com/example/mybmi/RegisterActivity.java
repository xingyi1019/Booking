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

public class RegisterActivity extends AppCompatActivity {
    EditText Account;
    EditText Password;
    TextView result_textView;
    Button sure;
    String result;
    String Passwordstr;
    String Accountstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        result = " ";
        Account = (EditText) findViewById(R.id.Account);
        Password = (EditText) findViewById(R.id.Password);
        result_textView = (TextView) findViewById(R.id.result_textView);
        sure = (Button) findViewById(R.id.Regis_Sure);


        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Accountstr = Account.getText().toString();
                Passwordstr = Password.getText().toString();
                String url = "http://140.136.151.144/RegisterApi.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result = response.trim();
                        result_textView.setText(result);//跳出success的字串在textView裡
                        if(result.equals("success")) {
                            Intent intent = new Intent();
                            intent.setClass(RegisterActivity.this, MainActivity.class);
                            Toast.makeText(RegisterActivity.this, "註冊成功", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "帳號密碼已存在", Toast.LENGTH_LONG).show();
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("RegisName", Accountstr);//和php的參數做連結
                        params.put("RegisPassword", Passwordstr);
                        return params;

                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                requestQueue.add(stringRequest);

            }

        });



    }


}