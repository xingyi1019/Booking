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


public class ReportActivity extends AppCompatActivity {


    double checkinmon ;
    double checkind ;
    double checkoutmon ;
    double checkoutd ;
    double day;
    double price ;
    double hprice ;//高的price
    double lprice ;//低的price
    String person ;//房型
    String county;
    String discount;//有無學生優惠
    String Accountstr;

    String hotel__nameinfo;//要傳給下一頁資料的名字參數
    String hotel__priceinfo;//要傳給下一頁資料的價格參數
    String hotel__roominfo;//要傳給下一頁資料的房型參數
    String hotel__addressinfo;//要傳給下一頁資料的地址參數
    String hotel__photoinfo;//要傳給下一頁資料的圖片參數


    private Button button_back;

    //String county ="台中";//存放使用者要求縣市

    int positionOflist =-3;//存放點下的飯店的position

    String result;
    //Bundle bundle = this.getIntent().getExtras();
    //String county = bundle.getString("signal_string");
    TextView show_result;
    TextView num_result;
    TextView suggest;
    Button sure;
    ListView hotellistview;//ReportActivity.xml裡的listview
    ArrayList<String> hotelname;//存飯店名字
    ArrayList<String> hotelprice;//存飯店價格
    ArrayList<String> hotelroom;//存飯店房型
    ArrayList<String> hoteladdress;//存飯店地址
    ArrayList<String> hotelphoto;//存飯店圖片
    //可以在此新增其他參數********************************

    private void setListensers()
    {
        button_back.setOnClickListener(backtoMain);
    }

    private Button.OnClickListener backtoMain = new Button.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent intent = new Intent();
            intent.setClass(ReportActivity.this, InitialActivity.class);
            DecimalFormat nf = new DecimalFormat("0");

            Bundle bundle = new Bundle();
            bundle.putString("KEY_userinfo", Accountstr);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        sure = (Button) findViewById(R.id.sure);
        hotellistview = (ListView) findViewById(R.id.hotellistview);
        show_result = (TextView) findViewById(R.id.textView7);
        num_result = (TextView) findViewById(R.id.textViewNO);
        button_back = (Button)findViewById(R.id.button);

        hotelname = new ArrayList<>();//初始化飯店名稱陣列
        hotelprice = new ArrayList<>();//初始化飯店價格陣列
        hotelroom  = new ArrayList<>();//初始化飯店房型陣列
        hoteladdress = new ArrayList<>();
        hotelphoto = new ArrayList<>();
        //////////可以新增其他初始化陣列

        try {
            DecimalFormat nf = new DecimalFormat("0");
            Bundle bundle = this.getIntent().getExtras();

            checkinmon = Double.parseDouble(bundle.getString("KEY_checkinmon"));
            checkind = Double.parseDouble(bundle.getString("KEY_checkind"));
            checkoutmon = Double.parseDouble(bundle.getString("KEY_checkoutmon"));
            checkoutd = Double.parseDouble(bundle.getString("KEY_checkoutd"));
            hprice = Double.parseDouble(bundle.getString("KEY_highprice"));
            lprice = Double.parseDouble(bundle.getString("KEY_lowprice"));
            person = bundle.getString("KEY_person");
            county = bundle.getString("signal_string");
            discount = bundle.getString("signal_stringyn");
            Accountstr = bundle.getString("KEY_userinfo");


            show_result.setText(getText(R.string.dayf_result) + nf.format(checkinmon)+"/"+nf.format(checkind)+"到"+nf.format(checkoutmon)+"/"+nf.format(checkoutd)+"    "
                            +getText(R.string.location_result) + county+" \n"
                            +getText(R.string.hotel_pricerange) + nf.format(lprice) +getText(R.string.dayb_result) + nf.format(hprice)+"元"+"   "
                            + getText(R.string.roomf_result) + person+ "\n"+
                             getText(R.string.hotel_studis)+discount
            );

        }
             catch(Exception obj) {
                Toast.makeText(this, "要先輸入完整資料喔!", Toast.LENGTH_SHORT).show();
            }


        setListensers();////////////

        String strHprice = String.valueOf(hprice);
        String strLprice = String.valueOf(lprice);
        //在此建立新增參數的陣列*****************************************
        sure.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String url = "";
                if(discount.equals("需要")){
                    url = "http://140.136.151.144/bookingSearch.php";
                }
                else{
                     url = "http://140.136.151.144/bookingSearchHotelCom.php";
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        result = response.trim();

                        if(response.equals("NO")){
                            Toast.makeText(ReportActivity.this,"查無資料", Toast.LENGTH_SHORT).show();
                            num_result.setText("沒有符合的結果 請更改條件");
                        }
                        else {
                            try {
                                JSONArray array = new JSONArray(result);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);

                                    String strname = jsonObject.getString("name");//name是對應到資料庫裡name的column
                                    String strprice = jsonObject.getString("price");//price是對應到資料庫裡price的column
                                    String strroom = jsonObject.getString("room");//room是對應到資料庫裡room的column
                                    String straddress = jsonObject.getString("address");
                                    String strphoto = jsonObject.getString("photo");
                                    //新增要解析的參數名稱*****************************************
                                    hotelname.add(strname);
                                    hotelprice.add(strprice);
                                    hotelroom.add(strroom);
                                    hoteladdress.add(straddress);
                                    hotelphoto.add(strphoto);
                                    //
                                    //新增要解析的參數名稱*****************************************

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }

                        listlayoutadapter adasports = new listlayoutadapter(ReportActivity.this);

                        hotellistview.setAdapter(adasports);////





                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportActivity.this,error.toString(), Toast.LENGTH_SHORT).show();

                    }


                }){

                    protected Map<String,String> getParams(){

                        Map<String,String> params = new HashMap<String,String>();

                        params.put("dataofAddress",county);

                        params.put("dataofHprice",strHprice);

                        params.put("dataofLprice",strLprice);

                        params.put("dataofPerson",person);

                        //新增要解析的參數名稱******************************************

                        return params;

                    }

                };


                    RequestQueue requestQueue = Volley.newRequestQueue(ReportActivity.this);
                    requestQueue.add(stringRequest);



            }


        });









    }

    public class listlayoutadapter extends BaseAdapter {

        private LayoutInflater listlayoutInflater;

        public listlayoutadapter(Context c){
            listlayoutInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            //取得ArrayList的總數 (要注意，跟array不同之處)
            return hotelname.size();
        }

        @Override
        public Object getItem(int position) {
            //要用get(position)取得資料 (要注意，跟array不同之處)
            return  hotelname.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {



            convertView = listlayoutInflater.inflate(R.layout.hotelmessage,null);

            //設定自訂樣板上物件對應的資料。
            //ImageView img_logo = (ImageView) convertView.findViewById(R.id.imglogo);
            TextView hotel_name = (TextView) convertView.findViewById(R.id.hotel__name);
            TextView hotel_price = (TextView) convertView.findViewById(R.id.hotel__price);
            Button sure_button = (Button) convertView.findViewById(R.id.sure_button);//////新增的button
            TextView hotel_room = (TextView) convertView.findViewById(R.id.hotel__room);
            TextView hotel_address = (TextView) convertView.findViewById(R.id.hotel__address);
            //TextView hotel_photo = (TextView) convertView.findViewById(R.id.hotel__photo);
            //新增要解析的參數名稱************************************

            //要用get(position)取得資料 (要注意，跟array不同之處)
            //img_logo.setImageResource(aryimas.get(position));
            hotel_name.setText(hotelname.get(position));
            hotel_price.setText("NT$"+hotelprice.get(position));
            hotel_room.setText(hotelroom.get(position));
            hotel_address.setText(hoteladdress.get(position));
            //hotel_photo.setText(hotelphoto.get(position));

            new  Thread(new Runnable() {
                @Override
                public void run() {
                    //ToDo Auto-grnerated method stub
                    if(!hotelphoto.get(position).equals("https://a.travel-assets.com/egds/marks/brands/hotels/loyalty.svg")){
                    final Bitmap mBitmap = getBitmapFromUrl(hotelphoto.get(position));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //ToDo Auto-grnerated method stub
                            ImageView jpg_hotel = (ImageView) findViewById(R.id.hotel__pic);
                            jpg_hotel.setImageBitmap(mBitmap);
                        }
                    });
                    }
                }
            }).start();
            //新增要解析的參數名稱************************************
            sure_button.setOnClickListener(new View.OnClickListener() {
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
                    bundle.putString("KEY_discount", discount);
                    //修改+++++++++++++

                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });

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


}
