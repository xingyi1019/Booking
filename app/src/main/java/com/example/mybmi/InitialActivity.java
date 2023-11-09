package com.example.mybmi;


        import android.content.Intent;
        import android.os.LocaleList;
        import androidx.appcompat.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.DisplayMetrics;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.text.DecimalFormat;
        import java.util.Locale;

public class InitialActivity extends AppCompatActivity {
    private static final int ACTIVITY_REPORT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        initViews();
        setListenser();
        setListensers();
    }

    private Button button_calc;
    private Spinner num_checkinmon;
    private Spinner num_checkoutmon;
    private Spinner num_checkind;
    private Spinner num_checkoutd;
    private EditText num_highprice;
    private EditText num_lowprice;
    private Spinner str_county;
    private Spinner num_person;
    private Spinner str_discount;
    private TextView show_result;
    private TextView show_suggest;
    private ImageView button_list;
    String Accountstr;

    private void initViews()
    {
        button_list = (ImageView)findViewById(R.id.imageView5);
        button_calc = (Button)findViewById(R.id.buttonsent);
        num_checkinmon = (Spinner)findViewById(R.id.checkinmon);
        num_checkind = (Spinner)findViewById(R.id.checkinday);
        num_checkoutmon = (Spinner)findViewById(R.id.checkoutmon);
        num_checkoutd = (Spinner)findViewById(R.id.checkoutday);
        num_highprice = (EditText)findViewById(R.id.highprice);
        num_lowprice = (EditText)findViewById(R.id.lowprice);
        str_county = (Spinner)findViewById(R.id.county);
        num_person = (Spinner)findViewById(R.id.people);
        str_discount = (Spinner)findViewById(R.id.studiscount);
        Bundle bundle = this.getIntent().getExtras();
        Accountstr = bundle.getString("KEY_userinfo");

    }

    private void setListenser()
    {
        button_list.setOnClickListener(seehistory);
    }

    private View.OnClickListener seehistory = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(InitialActivity.this, PersonalBookingViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("KEY_userinfo", Accountstr);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    private void setListensers()
    {
        button_calc.setOnClickListener(calcday);
    }

    private View.OnClickListener calcday = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(InitialActivity.this, ReportActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("KEY_checkinmon", num_checkinmon.getSelectedItem().toString());
            bundle.putString("KEY_checkind", num_checkind.getSelectedItem().toString());
            bundle.putString("KEY_checkoutmon", num_checkoutmon.getSelectedItem().toString());
            bundle.putString("KEY_checkoutd", num_checkoutd.getSelectedItem().toString());
            bundle.putString("KEY_highprice", num_highprice.getText().toString());
            bundle.putString("KEY_lowprice", num_lowprice.getText().toString());
            bundle.putString("signal_string", str_county.getSelectedItem().toString());
            bundle.putString("KEY_person", num_person.getSelectedItem().toString());
            bundle.putString("signal_stringyn", str_discount.getSelectedItem().toString());
            bundle.putString("KEY_userinfo", Accountstr);
            intent.putExtras(bundle);
            startActivityForResult(intent, ACTIVITY_REPORT);
        }
    };



    private void openOptionsDialog()
    {
        Toast popup = Toast.makeText(this, "訂房網站", Toast.LENGTH_SHORT);
        popup.show();
    }
}



