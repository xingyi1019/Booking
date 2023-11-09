package com.example.mybmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class bookhotel extends AppCompatActivity {
    String nameinfo;
    String priceinfo;
    String roominfo;
    String addressinfo;
    String photoinfo;
    String Accountstr;
    double checkinmon ;
    double checkind ;
    double checkoutmon ;
    double checkoutd ;
    String discount;
    String result;
    EditText editTextTextPersonName2;
    EditText editTextPhone;
    EditText editTextTextEmailAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookhotel);
        initViews();
        showResults();
        setListensers();
        editTextTextPersonName2 = (EditText) findViewById(R.id.editTextTextPersonName2);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextTextEmailAddress = (EditText) findViewById(R.id.editTextTextEmailAddress);
        new  Thread(new Runnable() {
            @Override
            public void run() {
                //ToDo Auto-grnerated method stub
                if(!photoinfo.equals("https://a.travel-assets.com/egds/marks/brands/hotels/loyalty.svg")){
                final Bitmap mBitmap = getBitmapFromUrl(photoinfo);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //ToDo Auto-grnerated method stub
                        ImageView jpg_hotel = (ImageView) findViewById(R.id.hotelpic);
                        jpg_hotel.setImageBitmap(mBitmap);
                    }
                });
                }
            }
        }).start();
    }
    private TextView show_name;
    private TextView show_address;
    private TextView show_price;
    private TextView show_room;
    private Button button_back;
    private ImageView  jpg_hotel;
    private TextView checkin;
    private TextView checkout;
    private TextView unshow_discount;
    private void initViews() {
        show_name = (TextView)findViewById(R.id.textView19);
        show_address = (TextView)findViewById(R.id.textView20);
        show_room = (TextView)findViewById(R.id.textView17);
        show_price = (TextView)findViewById(R.id.textView13);
        button_back = (Button) findViewById(R.id.buttonbook);
        jpg_hotel= (ImageView) findViewById(R.id.hotelpic);
        checkin = (TextView) findViewById(R.id.Datein);
        checkout = (TextView) findViewById(R.id.Dateout);
        unshow_discount = (TextView)findViewById(R.id.textView18);
    }
    private void showResults() {
        DecimalFormat nf = new DecimalFormat("0");
        Bundle bundle = this.getIntent().getExtras();
        nameinfo = bundle.getString("KEY_nameinfo");
        priceinfo = bundle.getString("KEY_priceinfo");
        roominfo = bundle.getString("KEY_roominfo");
        addressinfo = bundle.getString("KEY_addressinfo");
        photoinfo = bundle.getString("KEY_photoinfo");
        Accountstr = bundle.getString("KEY_userinfo");
        checkinmon = bundle.getDouble("KEY_checkinmon");
        checkind = bundle.getDouble("KEY_checkind");
        checkoutmon = bundle.getDouble("KEY_checkoutmon");
        checkoutd = bundle.getDouble("KEY_checkoutd");
        discount = bundle.getString("KEY_discount");
        show_name.setText( nameinfo);
        show_address.setText( addressinfo);
        show_room.setText( roominfo);
        show_price.setText( "NT$"+priceinfo);
        checkin.setText("入住日期:"+nf.format(checkinmon)+"/"+nf.format(checkind));
        checkout.setText("退房日期:"+nf.format(checkoutmon)+"/"+nf.format(checkoutd));

        if(discount.equals("不需要")){
            Drawable drawable = getResources().getDrawable(R.drawable.check);
            unshow_discount.setCompoundDrawables(null,null,null,null);
            unshow_discount.setText( " ");}
    }
    public static Bitmap getBitmapFromUrl(String src){
        try{
            URL url = new URL(src);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream input = conn.getInputStream();
            Bitmap mBitmap = BitmapFactory.decodeStream(input);
            return mBitmap;

        }catch (IOException e){
            //ToDo Auto-grnerated catch block
            e.printStackTrace();
            return  null;
        }
    }
    private void setListensers()
    {
        button_back.setOnClickListener(gotolog);
    }
    private View.OnClickListener gotolog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String url = "http://140.136.151.144/UserbookApi.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    result = response.trim();
                    Toast.makeText(bookhotel.this,"訂房成功", Toast.LENGTH_SHORT).show();



                    }









            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(bookhotel.this,error.toString(), Toast.LENGTH_SHORT).show();

                }


            }){

                protected Map<String,String> getParams(){

                    Map<String,String> params = new HashMap<String,String>();

                    params.put("regis_name",Accountstr);

                    params.put("room",roominfo);

                    params.put("price",priceinfo);

                    params.put("address",addressinfo);

                    params.put("photo",photoinfo);

                    params.put("hotel_name",nameinfo);

                    params.put("checkin",(int)checkinmon+"/"+(int)checkind);

                    params.put("checkout",(int)checkoutmon+"/"+(int)checkoutd);

                    params.put("real_name",editTextTextPersonName2.getText().toString());

                    params.put("phone_number",editTextPhone.getText().toString());

                    params.put("Email",editTextTextEmailAddress.getText().toString());

                    //新增要解析的參數名稱******************************************

                    return params;

                }

            };


            RequestQueue requestQueue = Volley.newRequestQueue(bookhotel.this);
            requestQueue.add(stringRequest);

            Intent intent = new Intent();
            intent.setClass(bookhotel.this,FinishActivity .class);
            Bundle bundle = new Bundle();
            bundle.putString("KEY_userinfo", Accountstr);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
}
