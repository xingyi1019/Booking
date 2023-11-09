package com.example.mybmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {
    private Button button_toinit;
    private TextView thankuser;
    String Accountstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        initViews();
        setListensers();

    }
    private void initViews() {
        thankuser = (TextView) findViewById(R.id.textViewThank);
        button_toinit = (Button) findViewById(R.id.buttontoinit);
        Bundle bundle = this.getIntent().getExtras();
        Accountstr = bundle.getString("KEY_userinfo");
    }
    private void setListensers()
    {
        button_toinit.setOnClickListener(gotolog);
    }
    private View.OnClickListener gotolog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(FinishActivity.this,InitialActivity .class);
            Bundle bundle = new Bundle();
            bundle.putString("KEY_userinfo", Accountstr);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

}