package ham.am.rdrun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }   // Main Method

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
