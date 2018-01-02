package com.example.zac.project1;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Array;

import javax.xml.transform.Result;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

////
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;





public class MapBusStation extends AppCompatActivity {

    GoogleMap googlemap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_bus_station);

        Button button = (Button) findViewById(R.id.mkstop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new GoodTask().execute("http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=7e71d313-e827-4314-8764-d5d5653ff5ee");

            }
        });

<<<<<<< HEAD
=======
        ////
>>>>>>> 49977a5e17afad878ba40d0cd3db874b1dcc0242



        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googlemap=googleMap;
                if (ActivityCompat.checkSelfPermission(MapBusStation.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(MapBusStation.this,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

                    // 詢問權限
                    ActivityCompat.requestPermissions(MapBusStation.this,
                            new String[] {ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION },1
                    );
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(25.042233,121.535497),14));//北科大
                //double showLat=0.0;
                //double showLon=0.0;
                //String nameZh="";
                //marker(showLat,showLon,nameZh);
            }
        });
    }

    class GoodTask extends AsyncTask<String, Integer, String> {
        // <傳入參數, 處理中更新介面參數, 處理後傳出參數>
        private static final int TIME_OUT = 1000;

        String jsonString1 = "";
        @Override
        protected String doInBackground(String... countTo) {
            // TODO Auto-generated method stub
            // 再背景中處理的耗時工作
            try{
                HttpURLConnection conn = null;
                URL url = new URL(countTo[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.connect();
                // 讀取資料
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(), "UTF-8"));
                jsonString1 = reader.readLine();
                reader.close();

                if (Thread.interrupted()) {
                    throw new InterruptedException();

                }
                if (jsonString1.equals("")) {
                    Thread.sleep(1000);
                }
            }

            catch(Exception e)
            {
                e.printStackTrace();
                return "網路中斷"+e;
            }


            String[] c = jsonString1.split("\\[");
            Log.d("asd",c[0]);
            jsonString1 = jsonString1.replace(c[0],"");
            Log.d("asd",jsonString1);

            return jsonString1;
        }

        String [] nameZh;
        double [] showLat;
        double [] showLon;

        public void onPostExecute(String result )
        { super.onPreExecute();
            // 背景工作處理完"後"需作的事

            // JSONObject obj;
            try {


                //  obj = new JSONObject(result);
                //如果傳來的json是物件，且有給名字比如:data:{}才會使用obj.getString(“data”)
                // String data = obj.getString("data");
                //這裡傳來的是JSON陣列，因此要把剛剛撈到的字串轉換為JSON陣列
                Log.d("test",result);
                JSONArray dataArray = new JSONArray(result);
                //先宣告tital跟id的字串陣列來存放等等要拆解的資料
                nameZh = new String [dataArray.length()];
                showLat = new double [dataArray.length()];
                showLon = new double [dataArray.length()];

                //因為data陣列裡面有好多個JSON物件，因此利用for迴圈來將資料抓取出來
                //因為不知道data陣列裡有多少物件，因此我們用.length()這個方法來取得物件的數量
                for (int i = 0; i < dataArray.length(); i++) {
                    //接下來這兩行在做同一件事情，就是把剛剛JSON陣列裡的物件抓取出來
                    //並取得裡面的字串資料
                    //dataArray.getJSONObject(i)這段是在講抓取data陣列裡的第i個JSON物件
                    //抓取到JSON物件之後再利用.getString(“欄位名稱”)來取得該項value

                    nameZh[i] = dataArray.getJSONObject(i).getString("nameZh");
                    showLat[i] = dataArray.getJSONObject(i).getDouble("showLat");
                    showLon[i] = dataArray.getJSONObject(i).getDouble("showLon");

                    Log.d("Main_get", "name:" + nameZh[i] + ",Lat:" + showLat[i] + ",Lon" + showLon[i]);

                    marker(showLat[i] , showLon[i] , nameZh[i]);

                }

            }
            catch (JSONException e) {
                e.printStackTrace();

                Toast.makeText(MapBusStation.this,"失敗",Toast.LENGTH_SHORT).show();
            }
            //step1:
            //mytest.setText("JSON:\r\n"+ result);
            //step2:



        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
            // 背景工作處理"中"更新的事

        }

    }

    public void marker(double showLat,double showLon,String nameZh){

        Log.d("maker",nameZh+":"+showLat +":"+showLon);
<<<<<<< HEAD
=======
        //Toast.makeText(MainActivity.this,"水ㄛ",Toast.LENGTH_SHORT);
>>>>>>> 49977a5e17afad878ba40d0cd3db874b1dcc0242
        MarkerOptions [] m=new MarkerOptions[11000];
        for (int i=0;i<nameZh.length();i++){

            m[i]= new MarkerOptions();
            m[i].position(new LatLng(showLat,showLon));
            m[i].title(nameZh);
            m[i].draggable(true);
            googlemap.addMarker(m[i]);

        }

//單點手動標記
//        //加入台北101標記
//        MarkerOptions a1=new MarkerOptions();
//        a1.position(new LatLng(25.033611,121.565000));
//        a1.title("台北101");
//        a1.draggable(true);
//        googlemap.addMarker(a1);
//        //加入台北車站標記
//        MarkerOptions a2=new MarkerOptions();
//        a2.position(new LatLng(25.047924,121.517081));
//        a2.title("台北車站");
//        a2.draggable(true);
//        googlemap.addMarker(a2);

        //googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(25.042233,121.535497),14));//北科大
    }


    //處理使用者選擇後的結果
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    double showLat=0.0;
                    double showLon=0.0;
                    String nameZh="";
                    marker(showLat,showLon,nameZh);
                } else {
                    Toast.makeText(MapBusStation.this,"不給權限不做事",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}


/*////老師用的json方法  1.內網 2.直接丟入json字串
public class MainActivity extends AppCompatActivity {

    GoogleMap googlemap;



    class Data{
        Result result;
        class Result{
            Results[] results;
            class Results{
                String nameZh;
                double showLat;
                double showLon;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=(Button)findViewById(R.id.mkstop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OkHttpClient mOkHttpClinet=new OkHttpClient();
                Request request=new Request.Builder()
                        .url("http://192.168.0.100/test.php")
                        .build();
                Call call=mOkHttpClinet.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e)
                    {
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        Intent i=new Intent("MyMessage");
                        i.putExtra("json", response.body().string());
                        sendBroadcast(i);
                    }
                });
            }
        });

        BroadcastReceiver myBroadcasReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //String myjson=intent.getExtras().getString("json");
                String myjson="{\"result\":{\"offset\":0,\"limit\":10000,\"count\":31,\"sort\":999,\"results\":[{\"showLat\":\"25.063729548028\",\"showLon\":\"121.501722548340\",\"nameZh\":\"三重派出所(重新路)\" },{\"showLat\":\"24.986882\",\"showLon\":\"121.569664\",\"nameZh\":\"永安街\"},{\"showLat\":\"24.986882\",\"showLon\":\"121.569664\",\"nameZh\":\"永安街\"},{\"showLat\":\"25.06996983\",\"showLon\":\"121.61165280\",\"nameZh\":\"明湖國中\"},{\"showLat\":\"25.067093\",\"showLon\":\"121.534193\",\"nameZh\":\"建北站\"},{\"showLat\":\"25.084350\",\"showLon\":\"121.511960\",\"nameZh\":\"葫東重慶路口\"},{\"showLat\":\"25.09127039\",\"showLon\":\"121.51710220\",\"nameZh\":\"陽明高中\"}]}}";


                Gson gson=new Gson();
                Data data=gson.fromJson(myjson,Data.class);
                String[] marker_item=new String[data.result.results.length];
                MarkerOptions[]  m = new MarkerOptions[99999];
                //參數錯誤 Toast.makeText(context,data.result.results.length,Toast.LENGTH_SHORT).show();
                for (int i=0;i<data.result.results.length;i++){
                        m[i]= new MarkerOptions();
                        //Log.d("test","lat:"+data.result.results[i].showLat+",Lon:"+data.result.results[i].showLon);
                        m[i].position(new LatLng(data.result.results[i].showLat, data.result.results[i].showLon));
                        m[i].title(data.result.results[i].nameZh);
                        m[i].draggable(true);
                        googlemap.addMarker(m[i]);
                    //dialog測試
                    //marker_item[i] +="\n經度:"+data.result.results[i].showLat;
                    //marker_item[i] +="\n緯度:"+data.result.results[i].showLon;
                    //marker_item[i] +="\n站牌名:"+data.result.results[i].nameZh;

                }

                //dialog測試
                //AlertDialog.Builder dialog_list=new AlertDialog.Builder(MainActivity.this);
               // dialog_list.setTitle("測試一下");
                //dialog_list.setItems(marker_item,null);
                //dialog_list.show();
            }
        };
        IntentFilter intentFilter=new IntentFilter("MyMessage");
        registerReceiver(myBroadcasReceiver,intentFilter);
        ////




        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googlemap=googleMap;
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(MainActivity.this,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

                    // 詢問權限
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[] {ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION },1
                    );
                    return;

                }
                googleMap.setMyLocationEnabled(true);
                marker();



            }
        });
     }

    public void marker(){


//        for (int i=0;i<5;i++){
//            MarkerOptions[] m=new MarkerOptions[i];
//            m[i].position(new LatLng(26+i,))
//        }
        //加入台北101標記
        MarkerOptions a1=new MarkerOptions();
        a1.position(new LatLng(25.033611,121.565000));
        a1.title("台北101");
        a1.draggable(true);
        googlemap.addMarker(a1);
        //加入台北車站標記
        MarkerOptions a2=new MarkerOptions();
        a2.position(new LatLng(25.047924,121.517081));
        a2.title("台北車站");
        a2.draggable(true);
        googlemap.addMarker(a2);
        //加入路徑
//        PolylineOptions polylineOpt=new PolylineOptions();
//        polylineOpt.add(new LatLng(25.033611,121.565000));
//        polylineOpt.add(new LatLng(25.032728,121.565137));
//        polylineOpt.add(new LatLng(25.033739,121.527886));
//        polylineOpt.add(new LatLng(25.038716,121.517758));
//        polylineOpt.add(new LatLng(25.045656,121.519636));
//        polylineOpt.add(new LatLng(25.046200,121.517533));
//        polylineOpt.color(Color.BLUE);
//        Polyline polyline=googlemap.addPolyline(polylineOpt);
//        polyline.setWidth(10);

        googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(25.042233,121.535497),14));//北科大
    }


    //處理使用者選擇後的結果
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    marker();
                } else {
                    Toast.makeText(MainActivity.this,"不給權限不做事",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}*/














