package ham.am.rdrun;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ServiceActivity extends FragmentActivity implements OnMapReadyCallback {

    //Explicit
    private GoogleMap mMap;
    private String idString, avatarString,nameString,surnameString;
    private ImageView imageView;
    private TextView nameTextView, surnameTextView;
    private int[] avataInts;
    private double userLatADouble = 13.8063773, userLngADouble=100.5754295;
    private LocationManager locationManager;
    //แกน x,y,z
    private Criteria criteria;

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
            Log.d("1SepV1", "Cannot Find Location");
        }

        return location;
    }


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
        Log.d("1SepV2", "Lat = " + userLatADouble);
        Log.d("1SepV2", "Lng = " + userLngADouble);

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


}//Main Class ServiceActivity
