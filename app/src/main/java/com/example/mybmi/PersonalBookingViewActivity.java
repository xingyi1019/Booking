package com.example.mybmi;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;

public class PersonalBookingViewActivity extends AppCompatActivity {
    String Accountstr;
    String result;
    Button PersonalSure_button ;
    TextView button_back;
    TextView PersonalAccCheck;
    ListView PersonalHotellistview;
    ArrayList<String> Personalhotelname;//存飯店名字
    ArrayList<String> Personalhotelprice;//存飯店價格
    ArrayList<String> Personalhotelroom;//存飯店房型
    ArrayList<String> Personalhoteladdress;//存飯店地址
    ArrayList<String> Personalhotelphoto;//存飯店圖片
    ArrayList<String> Personalhotelcheckin;//存入住日期
    ArrayList<String> Personalhotelcheckout;//存離房日期

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_booking_view);
        PersonalSure_button =(Button) findViewById(R.id.PersonalSure_button);
        PersonalHotellistview = (ListView) findViewById(R.id.PersonalHotellistview);
        PersonalAccCheck = (TextView) findViewById(R.id.PersonalAccCheck);
        button_back = (TextView)findViewById(R.id.buttontomain);

        Personalhotelname = new ArrayList<>();//初始化飯店名稱陣列
        Personalhotelprice = new ArrayList<>();//初始化飯店價格陣列
        Personalhotelroom  = new ArrayList<>();//初始化飯店房型陣列
        Personalhoteladdress = new ArrayList<>();
        Personalhotelphoto = new ArrayList<>();
        Personalhotelcheckin = new ArrayList<>();
        Personalhotelcheckout = new ArrayList<>();

        Bundle bundle = this.getIntent().getExtras();
        Accountstr = bundle.getString("KEY_userinfo");
        PersonalAccCheck.setText(Accountstr+" 的訂購記錄");
        setListensers();
        PersonalSure_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                String url = "http://140.136.151.144/PersonalBookingView.php";


                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        result = response.trim();

                        if(response.equals("NO")){
                            Toast.makeText(PersonalBookingViewActivity.this,"查無資料", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {
                                JSONArray array = new JSONArray(result);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);

                                    String strroom = jsonObject.getString("room");//name是對應到資料庫裡name的column
                                    String strprice = jsonObject.getString("price");//price是對應到資料庫裡price的column
                                    String straddress = jsonObject.getString("address");
                                    String strphoto = jsonObject.getString("photo");
                                    String strhotel_name = jsonObject.getString("hotel_name");
                                    String strcheckin = jsonObject.getString("checkin");
                                    String strcheckout = jsonObject.getString("checkout");
                                    //新增要解析的參數名稱*****************************************
                                    Personalhotelroom.add(strroom);
                                    Personalhotelprice.add(strprice);
                                    Personalhoteladdress.add(straddress);
                                    Personalhotelphoto.add(strphoto);
                                    Personalhotelname.add(strhotel_name);
                                    Personalhotelcheckin.add(strcheckin);
                                    Personalhotelcheckout.add(strcheckout);

                                    //
                                    //新增要解析的參數名稱*****************************************

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }

                        Personallistlayoutadapter adasports = new Personallistlayoutadapter(PersonalBookingViewActivity.this);

                        PersonalHotellistview.setAdapter(adasports);////





                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PersonalBookingViewActivity.this,error.toString(), Toast.LENGTH_SHORT).show();

                    }


                }){

                    protected Map<String,String> getParams(){

                        Map<String,String> params = new HashMap<String,String>();

                        params.put("Personal_loginAct",Accountstr);//傳燈入的帳號



                        //新增要解析的參數名稱******************************************

                        return params;

                    }

                };


                RequestQueue requestQueue = Volley.newRequestQueue(PersonalBookingViewActivity.this);
                requestQueue.add(stringRequest);



            }


        });
    }

    public class Personallistlayoutadapter extends BaseAdapter {

        private LayoutInflater listlayoutInflater;

        public Personallistlayoutadapter(Context c){
            listlayoutInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            //取得ArrayList的總數 (要注意，跟array不同之處)
            return Personalhotelname.size();
        }

        @Override
        public Object getItem(int position) {
            //要用get(position)取得資料 (要注意，跟array不同之處)
            return  Personalhotelname.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {



            convertView = listlayoutInflater.inflate(R.layout.personalviewdetails,null);

            //設定自訂樣板上物件對應的資料。
            //ImageView img_logo = (ImageView) convertView.findViewById(R.id.imglogo);
            TextView Personalhotel_name = (TextView) convertView.findViewById(R.id.PersonalHotel__name);
            TextView Personalhotel_price = (TextView) convertView.findViewById(R.id.PersonalHotel__price);
            Button Personalsure_button = (Button) convertView.findViewById(R.id.PersonalSure_button);//////新增的button
            TextView Personalhotel_room = (TextView) convertView.findViewById(R.id.PersonalHotel__room);
            //TextView Personalhotel_address = (TextView) convertView.findViewById(R.id.PersonalHotel__address);
            TextView Personalhotel_checkin =(TextView) convertView.findViewById(R.id.PersonalHotel__checkin);
            //TextView Personalhotel_checkout = (TextView) convertView.findViewById(R.id.PersonalHotel__checkout);
            //TextView hotel_photo = (TextView) convertView.findViewById(R.id.hotel__photo);
            //新增要解析的參數名稱************************************

            //要用get(position)取得資料 (要注意，跟array不同之處)
            //img_logo.setImageResource(aryimas.get(position));
            DecimalFormat nf = new DecimalFormat("0");
            Personalhotel_name.setText(Personalhotelname.get(position));
            Personalhotel_price.setText("NT$"+Personalhotelprice.get(position));
            Personalhotel_room.setText(Personalhotelroom.get(position));
            //Personalhotel_address.setText(Personalhoteladdress.get(position));
            Personalhotel_checkin.setText(Personalhotelcheckin.get(position)+"~"+Personalhotelcheckout.get(position));
            //Personalhotel_checkout.setText(Personalhotelcheckout.get(position));
            //hotel_photo.setText(hotelphoto.get(position));

            new  Thread(new Runnable() {
                @Override
                public void run() {
                    //ToDo Auto-grnerated method stub
                    final Bitmap mBitmap = getBitmapFromUrl(Personalhotelphoto.get(position));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //ToDo Auto-grnerated method stub
                            ImageView jpg_hotel= (ImageView) findViewById(R.id.PersonalHotel__pic);
                            jpg_hotel.setImageBitmap(mBitmap);
                        }
                    });
                }
            }).start();
            //新增要解析的參數名稱************************************
            /*sure_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    hotel__nameinfo = hotelname.get(position);
                    hotel__priceinfo = hotelprice.get(position);
                    hotel__roominfo = hotelroom.get(position);
                    hotel__addressinfo = hoteladdress.get(position);
                    hotel__photoinfo = hotelphoto.get(position);
                    //修改+++++++++++++
                    Intent intent = new Intent();
                    intent.setClass(ReportActivity.this, bookhotel.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("KEY_priceinfo", hotel__priceinfo);
                    bundle.putString("KEY_nameinfo", hotel__nameinfo);
                    bundle.putString("KEY_roominfo", hotel__roominfo);
                    bundle.putString("KEY_addressinfo", hotel__addressinfo);
                    bundle.putString("KEY_photoinfo", hotel__photoinfo);
                    bundle.putString("KEY_userinfo", Accountstr);
                    bundle.putDouble("KEY_checkinmon", checkinmon);
                    bundle.putDouble("KEY_checkind", checkind);
                    bundle.putDouble("KEY_checkoutmon", checkoutmon);
                    bundle.putDouble("KEY_checkoutd", checkoutd);
                    //修改+++++++++++++

                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });*/

            return convertView;


        }
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
        button_back.setOnClickListener(seehistory);
    }

    private View.OnClickListener seehistory = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(PersonalBookingViewActivity.this, InitialActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("KEY_userinfo", Accountstr);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

}