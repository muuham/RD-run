package ham.am.rdrun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private ImageView imageView;
    private EditText userEditText, passwordEditText;

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

        //Load Image
        Picasso.with(this)
                .load("http://swiftcodingthai.com/rd/Image/rd_logo.png")
                .resize(150,150).into(imageView);

    }   // Main Method

    public void clickSignInMain(View view) {

        //ให้ทำการดึงข้อมูล จาก ฐานข้อมูล ขึ้นมา


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
