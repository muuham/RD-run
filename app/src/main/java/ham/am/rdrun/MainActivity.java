package ham.am.rdrun;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private ImageView imageView;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind widget
        //ชื่อตัวแปร = พิมพ์ finv แล้วเลื่อก findViewById
        //จากนั้นใส่ R.id.imageView6 จากนั้น alt + Enter เลือก cast
        imageView = (ImageView) findViewById(R.id.imageView6);
        userEditText = (EditText) findViewById(R.id.editText5);
        passwordEditText =  (EditText) findViewById(R.id.editText6);

        //Load Image แสดงรูปจาก URL
        Picasso.with(this)
                .load("http://swiftcodingthai.com/rd/Image/rd_logo.png")
                .resize(150,150).into(imageView);

    }   // Main Method


    //เอาข้อมูลที่ได้จากไฟล์ PHP มาเก็บไว้ใน String
    //Inner Class <= class ซ้อน class
    //extends สืบทอดคุณสมบัตื
    //ก่อนโหลด ระหว่างโหลด หลังโหลด ให้ทำอะไร
    //Void,Void,String <== ก่อนโหลด:ไม่ทำอะไร ระหว่างโหลด:ไม่ทำอะไร หลังโหลด:คืนค่ามาเป็น String
    //เมาส์วางที่ extends กด alt + Enter แล้วเลือก Implement Method
    private class SynUser extends AsyncTask<Void, Void, String> {

        //Explicit
        private Context context;
        private String myUserString, myPasswordString , truePasswordString, nameString, surnameString, idString, avataString;
        private final String urlJSON = "http://swiftcodingthai.com/rd/get_user_master.php";
        private boolean statusABoolean = true;

        //สร้าง constructor ทำหน้าที่ในดึงข้อมูลจาก JSON ขึ้นมา
        //constructor ชื่อเดียวกันกับ class
        //alt + Insert เลือก constructor เลือกตัวแปรทั้งหมด
        public SynUser(Context context, String myUserString, String myPasswordString) {
            this.context = context;
            this.myUserString = myUserString;
            this.myPasswordString = myPasswordString;
        }

        //doInBackground ทำการดึงข้อมูล
        @Override
        protected String doInBackground(Void... params) {

            try {
                //สร้าง Instant ของ OkHttp
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                //กำหนดว่าต้องไปโหลด JSON มาจากไหน
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                //Error
                //หรือเนตหลุด URL ผิด จะเข้ามาทำในนี้
                Log.d("31AugV2", "e doInBackground => " + e.toString());
                return null;
            }

        } // doInBack ต่อเนตไปเรื่อยๆ

        //ทำงานต่อจาก doInBack
        //alt + insert เลือก Override Method... เลือก onPostExecute
        //คืนค่ามาเป็น String
        //แสดงค่าที่ได้จาก JSON
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("31AugV2", "JSON => " + s);

            try {

                //รับ parameter จาก doInBackground
                JSONArray jsonArray = new JSONArray(s);

                //วนลูปเก็บ
                for (int i=0; i<jsonArray.length(); i+=1) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    //ถ้า User = User ใน jsonObject (ที่ได้มาจาก DB)
                    if (myUserString.equals(jsonObject.getString("User"))) {
                        statusABoolean = false;
                        truePasswordString = jsonObject.getString("Password");
                        nameString = jsonObject.getString("Name");
                        surnameString = jsonObject.getString("Surname");
                        idString = jsonObject.getString("id");
                        avataString = jsonObject.getString("Avata");

                    } //if
                } //for

                if (statusABoolean) {
                    //statusABoolean = true
                    //User false ไม่มี User นี้ในฐานข้อมูล
                    MyAlert myAlert = new MyAlert();
                    myAlert.MyDialog(context, R.drawable.kon48, "แจ้งเตือน", "ไม่มี" + myUserString + "ในฐานข้อมูล");
                } else if (myPasswordString.equals(truePasswordString)) {

                    Intent intent = new Intent(MainActivity.this, ServiceActivity.class);

                    //ส่งค่าที่รับจาก DB แนบไปกับการ Intent
                    //ดูตัวแปรที่ if (myUserString.equals(jsonObject.getString("User"))) {
                    //intent.putExtra("id", idString); => id ชื่อตัวแปรที่ส่งไป idString ค่าที่ส่งไป
                    intent.putExtra("id", idString);
                    intent.putExtra("Avata", avataString);
                    intent.putExtra("Name", nameString);
                    intent.putExtra("Surname", surnameString);
                    //โยนไปที่ ServiceActivity
                    startActivity(intent);



                    //statusABoolean = false <= ถูก
                    //ตรวจสอบ password ต่อ
                    //password = true <= ถูก
                    Toast.makeText(context, "welcome คุณ " + nameString + " " + surnameString,
                            Toast.LENGTH_SHORT).show();

                } else {
                    //statusABoolean = false <= ถูก
                    //password = false <= ผิด
                    MyAlert myAlert = new MyAlert();
                    myAlert.MyDialog(context,R.drawable.bird48, "แจ้งเตือน", "พาสเวิคไม่ถูกต้อง");
                }

            } catch (Exception e) {
                //Error
                //หรือ JSON ไม่อยู่ในรูปแบบมาตรฐาน
                Log.d("31AugV3", "e onPostExecute => " + e.toString());
            }

        }//onPost
    }



    public void clickSignInMain(View view) {

        //ให้ทำการดึงข้อมูล จาก ฐานข้อมูล ขึ้นมา
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //check space ตรวจสอบช่องว่างก่อน
        //พิมพ์ if แล้วกด shift + ctrl + Enter
        if (userString.equals("") || passwordString.equals("")) {

            //True = Have Space
            MyAlert myAlert = new MyAlert();
            myAlert.MyDialog(this, R.drawable.rat48, "มีช่องว่าง", "กรุณากรอกข้อมูลให้ครบถ้วน");

        } else {

            //False = No Space
            //ดึงข้อมูลจากฐานข้อมูล
            //check Authen
            //สร้างตัวแปร เพื่อไปเรียก class SynUser
            SynUser synUser = new SynUser(this, userString, passwordString);
            //สั้งให้ execute
            synUser.execute();

        }


    }//clickSignInMain

    //Get Event from Click Button
    //1.void ดำเนินการอะไรก็ตาม โดยไม่แจ้งผลกลับ
    //2.สร้างตัวแปร view ด้วยการ Ctrl + Space
    //3.shift + ctrl + Enter = เพื่อให้ใส่ { อัตโนมัติ
    //4.ไปที่ไฟล์ activity_main.xml คลิกที่ปุ่ม sign up แล้วเลือก Properties Onclick เลือก clickSignUpMain
    public void clickSignUpMain(View view) {
        //5.เคลื่อนย้ายจาก MainActivity.this ไป SignUpActivity.class => หมายถึง คลิกที่ปุ่ม สมัครสมาชิก  แล้วไปหน้า สมัครสมาชิก
        //6.Run Test
        startActivity(new Intent(MainActivity.this,SignUpActivity.class));
    }

}   // Main Class
