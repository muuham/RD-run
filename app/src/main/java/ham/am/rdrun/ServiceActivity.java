package ham.am.rdrun;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class ServiceActivity extends FragmentActivity implements OnMapReadyCallback {

    //Explicit
    private GoogleMap mMap;
    private String idString, avatarString,nameString,surnameString;
    private ImageView imageView;
    private TextView nameTextView, surnameTextView;
    private int[] avataInts;
    //ละติจูด ลองติจูด
    private double userLatADouble = 13.8063773, userLngADouble=100.5754295;
    private LocationManager locationManager;
    //แกน x,y,z
    private Criteria criteria;
    //urlPHP
    private static final String urlPHP = "http://swiftcodingthai.com/rd/edit_location_droid.php";
    //เมื่อคลิกที่รูป Avatar ให้หยุด (Marker)
    private boolean statusABoolean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //เปลี่ยนไปใช้ my_service
        setContentView(R.layout.my_service);

        //ผูกตัวแปร Bind Widget
        imageView = (ImageView) findViewById(R.id.imageView7);
        nameTextView = (TextView) findViewById(R.id.textView8);
        surnameTextView = (TextView) findViewById(R.id.textView9);

        //รับค่าจาก Intent
        idString = getIntent().getStringExtra("id");
        avatarString = getIntent().getStringExtra("Avata");
        nameString = getIntent().getStringExtra("Name");
        surnameString = getIntent().getStringExtra("Surname");

        //Set up Location
        //Open Location Service
        //Cast to alt + Enter
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //ระดับในการหา LOCATION_SERVICE
        //300 เมตร รอบตัว
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //ตัดค่าแกน Z ออก  เอาแค่แกน x,y พอ
        criteria.setAltitudeRequired(false);
        //Bearing ตำแหน่งห่างจากน้ำทะเล  แกน z
        criteria.setBearingRequired(false);


        //แสดงค่า Show Text
        //กำหนดให้ nameTextView = ค่าจาก Intent
        nameTextView.setText(nameString);
        surnameTextView.setText(surnameString);

        //Show Avata
        MyConstant myConstant = new MyConstant();
        avataInts = myConstant.getAvatarInts();
        //ใส่รูปภาพไปที่ imageView
        imageView.setImageResource(avataInts[Integer.parseInt(avatarString)]);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }// Main Method onCreate

    //ผูกปุ่ม
    public void clickNormal(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void clickSatellite(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void clickTerrain(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    public void clickHybrid(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    //refresh Data
    //Void, Void, String <= ก่อนโหลด และระหว่างโหลด ไม่ต้องทำอะไร  หลังโหลด ให้แสดงค่าออกมา
    //กด shift + ctrl + Enter
    //กด alt + Enter -> implement Method -> doInBackground
    private class SynAllUser extends AsyncTask<Void, Void, String> {

        //Explicit
        private Context context;
        private GoogleMap googleMap; //สำหรับปัก Marker
        private static final String urlJSON = "http://swiftcodingthai.com/rd/get_user_master.php";
        //ประกาศตัวแปร เพื่อดึงค่าออกมาจาก JSON
        private String[] nameStrings, surnameStrings;
        private int[] avataInts;
        private double[] latDoubles, lngDoubles;

        //setter
        //alt + insert -> Constructor... -> เลือก 2 รายการ  เลือก context ก่อนนะ
        public SynAllUser (Context context, GoogleMap googleMap) {
            this.context = context;
            this.googleMap = googleMap;
        }//Constructor SynAllUser

        //ให้ทำการ โหลดข้อมูลมาจาก urlJSON
        //ดึงข้อมูลจากฐานมา
        //อ่านทุกๆ 1 วินาที
        @Override
        protected String doInBackground(Void... voids) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                //Error
                Log.d("2SepV2", "SynAllUser = e doInBackground => " + e.toString());
                return null;
            }

            //return null;
        }//doInBackground

        //alt + insert -> Override Method... -> onPostExecute
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //เอา Data ออกมา
            try {
                //จองพื้นที่ Array
                JSONArray jsonArray = new JSONArray(s);
                //จองหน่วยความจำ ตามจำนวน record ใน table
                nameStrings = new String[jsonArray.length()];
                surnameStrings = new String[jsonArray.length()];
                avataInts = new int[jsonArray.length()];
                latDoubles = new double[jsonArray.length()];
                lngDoubles = new double[jsonArray.length()];

                for (int i=0; i<jsonArray.length(); i++) {
                    //ดึงข้อมูลออกมา
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    nameStrings[i] = jsonObject.getString("Name");
                    surnameStrings[i] = jsonObject.getString("Surname");
                    avataInts[i] = Integer.parseInt(jsonObject.getString("Avata"));
                    latDoubles[i] = Double.parseDouble(jsonObject.getString("Lat"));
                    lngDoubles[i] = Double.parseDouble(jsonObject.getString("Lng"));

                    //ดึงภาพ Avata มาใช้
                    MyConstant myConstant = new MyConstant();
                    int[] iconInts = myConstant.getAvatarInts();

                    //Create Marker
                    //.position ปักหมุด
                    //.icon เปลี่ยนรูปตาม Avata ที่เลือก
                    //.title แสดงชื่อ-นามสกุล
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latDoubles[i], lngDoubles[i]))
                            .icon(BitmapDescriptorFactory.fromResource(iconInts[avataInts[i]]))
                            .title(nameStrings[i] + " " + surnameStrings[i])
                    );

                    Log.d("2SepV3", "lat = " + i + " => " + latDoubles[i] + "lng = " + i + " => " + lngDoubles[i]);
                    Log.d("2SepV3", "--------------------------------------------------");
                }//for

            //คลิกที่ Marker กำหนด statusABoolean = false
            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    statusABoolean = !statusABoolean;
                    Log.d("2SepV4", "Status => " + statusABoolean);
                }
            });


            } catch (Exception e) {

                //Error
                Log.d("2SepV3", "synAllUser = e onPostExecute => " + e.toString());

            }

            Log.d("2SepV2", "SynAllUser = JSON onPostExecute => " + s);

        }//onPostExecute




    }//synAllUser


    //alt + Insert เลือก Override Method.. เลือก onResume
    //หลังจากแอพหยุด แล้วกลับมาใช้ใหม่ ให้ทำ <= resume
    @Override
    protected void onResume() {
        super.onResume();

        //หยุดการค้นหา location
        locationManager.removeUpdates(locationListener);

        //เริ่มการค้นหาใหม่
        //เช็คทั้ง ISP และ GPS

        //networkLocation <= ค่า location ที่ได้จากการต่อเนต === ISP
        Location networkLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);
        //มือถือต่อเนตหรือเปล่า
        if (networkLocation != null) {
            //ใช่ เก็บพิกัดมา
            userLatADouble = networkLocation.getLatitude();
            userLngADouble = networkLocation.getLongitude();
        }

        //GPS
        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);
        //มือถือต่อเนตหรือเปล่า
        if (gpsLocation != null) {
            //ใช่ เก็บพิกัดมา
            userLatADouble = gpsLocation.getLatitude();
            userLngADouble = gpsLocation.getLongitude();
        }

    }//onResume


    //alt + Insert เลือก Override Method.. เลือก onStop
    //หยุดการทำงาน
    @Override
    protected void onStop() {
        super.onStop();

        //หยุดการค้นหา location
        locationManager.removeUpdates(locationListener);
    }//onStop

    /////////////////////////////////////////////////////////////////////////////////
    //ให้แอพหยุดชัวคราว  กรณีที่มีโทรศัพท์เข้า  หรือ เปลี่ยนไปเล่นแอพอื่น <= pause
    //หยุดการทำงาน
    @Override
    protected void onPause() {
        super.onPause();
        //หยุดการค้นหา location
        locationManager.removeUpdates(locationListener);
    }
    /////////////////////////////////////////////////////////////////////////////////


    public Location myFindLocation(String strProvider) {
        //หา Location จาก การ์ด(GPS) หรือ ISP

        Location location = null;

        if (locationManager.isProviderEnabled(strProvider)) {

            //True
            //ให้ Update ทุกๆ
            //1000 = 1 วินาที กรณีเดิน
            //ให้ทำทุก 1 วนาที หรือ
            //10 = 10 เมตร  กรณีวิ่ง รถวิ่ง
            //ให้ทำทุก 10 เมตร
            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);
            //เก็บค่าละติดจูด ลองติจูด
            location = locationManager.getLastKnownLocation(strProvider);

        } else {

            //False
            Log.d("1SepV1", "myFindLocation = Cannot Find Location");
        }
        Log.d("1SepV3", "myFindLocation = location => " + location);
        return location;
    }//myFindLocation


    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            //เมื่อ Location มีการเปลี่ยนแปลง
            userLatADouble = location.getLatitude();
            userLngADouble = location.getLongitude();

        }//onLocationChanged

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Set up Center Map
        //กำหนด ค่า private double userLatADouble = 13.8063773, userLngADouble = 100.5754295 ไว้
        LatLng latLng = new LatLng(userLatADouble, userLngADouble);
        //Zoom 16 เท่า
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        //Loop
        //alt + Enter แล้วคลิก Create Method myLoop
        myLoop();

    }//Method onMapReady

    private void myLoop() {

        //To do
        Log.d("1SepV2", "myLoop = Lat => " + userLatADouble);
        Log.d("1SepV2", "myLoop = Lng => " + userLngADouble);

        // Update
        //โยนค่าเข้า DB ตาม user ที่ login
        //กด alt + Enter เลือก create Method
        editLatLngOnServer();

        //Marker
        //Read
        //กด alt + Enter เลือก create Method
        //statusABoolean = true ให้ทำการสร้าง Marker
        if (statusABoolean) {
            createMarker();
        }

        //Post Delay
        //ใช้ของ android.os
        Handler handler = new Handler();
        //หน่วงไป 1 วินาที และทำงานใน myLoop
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myLoop();
            }
        },1000);

    }//myLoop

    private void createMarker() {

        SynAllUser synAllUser = new SynAllUser(this, mMap);
        synAllUser.execute();

    }//createMarker


    private void editLatLngOnServer() {

        //ใช้ของ com.squareup.okhttp
        OkHttpClient okHttpClient = new OkHttpClient();
        //มัดข้อมูลให้เป็นก้อน เรียงต่อกัน <= Package
        //FormEncodingBuilder() <= พิมพ์ Fo แล้วกด Ctrl + Space
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("id", idString)
                .add("Lat", Double.toString(userLatADouble))
                .add("Lng", Double.toString(userLngADouble))
                .build();
        Request.Builder builder = new Request.Builder();
        //เซต urlPHP ที่จะใช้
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {
            //กรณีที่ไม่สามารถทำงานได้
            Log.d("2SepV1", "editLatLngOnServer = e onFailure => " + e.toString());
        }

        @Override
        public void onResponse(Response response) throws IOException {
            //กรณีที่ Update DB ได้
            Log.d("2SepV1", "editLatLngOnServer = result onResponse => " + response.body().string());
        }
    }); //call.enqueue
    }//editLatLngOnServer


}//Main Class ServiceActivity
