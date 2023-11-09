package com.example.mybmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("Main", "開始事件");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setListensers();
        setListenser();
    }
    private Button button_log;
    private Button button_reg;
    private void initViews() {
        button_reg = (Button) findViewById(R.id.buttonreg);
        button_log = (Button) findViewById(R.id.buttonlog);
    }
    private void setListensers()
    {
        button_log.setOnClickListener(gotolog);
    }
    private View.OnClickListener gotolog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,LogingActivity .class);
            startActivity(intent);
        }
    };
    private void setListenser()
    {
        button_reg.setOnClickListener(gotoreg);
    }
    private View.OnClickListener gotoreg = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    };
}
