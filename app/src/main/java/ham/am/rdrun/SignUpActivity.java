package ham.am.rdrun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import okio.BufferedSink;

public class SignUpActivity extends AppCompatActivity {

    //ประกาศตัวแปร  เพื่อพร้อมใช้งาน  ตัวแปร ประกอบด้วย 3 ส่วน access type name
    //access การเข้าถึง จะเป็น Public(ใช้ได้หมด) Private(ใช้เฉพาะ)
    //type ชนิดของตัวแปร เช่น ตัวเลข ข้อความ(EditText)
    //name ชื่อของตัวแปร
    //กรณีตั้งชื่อ ให้พิมพ์ชื่อที่ต้องการก่อน เช่น name จากนั้นกด Ctrl + space โปรแกรมจะขึ้นชื่อที่ควรใช้ให้ = nameEditText
    private EditText nameEditText, surnameEditText, userEditText, passwordEditText;
    private RadioGroup radioGroup;
    private RadioButton avata1RadioButton, avata2RadioButton, avata3RadioButton, avata4RadioButton, avata5RadioButton;
    private String nameString, surnameString, userString, passwordString, avataString;
    private static final String urlPHP = "http://swiftcodingthai.com/rd/add_user_droid.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ctrl ค้าง แล้วคลิกที่ชื่อ layout
        setContentView(R.layout.activity_sign_up);

        //จะทำงานที่ method นี้ก่อน
        //Bind or Inital Widget คือ การผูกความสัมพันธ์ระหว่างตัวแปร และ Widget
        //nameEditText <= ชื่อตัวแปรที่เราตั้ง
        //editText ชื่อ id ของ activity_sign_up.xml
        //(EditText) findViewById(R.id.editText) <= เรียกว่า cast หมายความว่า ไม่ว่าจะใส่ type อะไรมา จะถูกแปลงให้เป็น text
        //ชื่อตัวแปร = finv แล้วเลือก findViewById
        //กำหนดค่าภายใน R.id.editText
        //คลิก alt + Enter
        // Shift + Ctrl + Enter ใส่ ; ให้อัตโนมัติ
        nameEditText = (EditText) findViewById(R.id.editText);
        surnameEditText = (EditText) findViewById(R.id.editText2);
        userEditText = (EditText) findViewById(R.id.editText3);
        passwordEditText = (EditText) findViewById(R.id.editText4);
        radioGroup = (RadioGroup) findViewById(R.id.ragAvata);
        avata1RadioButton = (RadioButton) findViewById(R.id.radioButton);
        avata2RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        avata3RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        avata4RadioButton = (RadioButton) findViewById(R.id.radioButton4);
        avata5RadioButton = (RadioButton) findViewById(R.id.radioButton5);

        //Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radioButton:
                        avataString = "0";
                        break;
                    case R.id.radioButton2:
                        avataString = "1";
                        break;
                    case R.id.radioButton3:
                        avataString = "2";
                        break;
                    case R.id.radioButton4:
                        avataString = "3";
                        break;
                    case R.id.radioButton5:
                        avataString = "4";
                        break;
                }

            }
        });

    } //Main Method

    //ไม่มีการ return ค่ากลับ
    //กำหนดให้มีการมองเห็น โดยใช้ View
    public void clickSignUpSign(View view){

        //รับค่ามา
        //change data ให้เป็น String
        //Get Value From EditText
        //trim ตัดช่องว่างออกให้อัตโนมัตื
        nameString = nameEditText.getText().toString().trim();
        surnameString = surnameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //check space ตรวจสอบช่องว่าง
        //เช็คจาก Methed checkSpace()
        //คลิกที่ checkSpace() กด alt + Enter จะสร้าง Method ให้อัตโนมัติ
        if (checkSpace()) {

            //เมื่อไหร่ก็ตามสิ่งที่อยู่ใน if เป็นจริงจะเข้ามาทำงานในนี้
            //True
            //เรียก class myAlert
            MyAlert myAlert = new MyAlert();
            //Alert ประกอบด้วย icon(รูปภาพ) Title Message ปุ่ม(OK)
            //เรียก Method MyDialog
            myAlert.MyDialog(this, R.drawable.doremon48, "มีช่องว่าง", "กรุณากรอกทุกช่องค่ะ");

            //คลิกที่ checkChoose() กด alt + Enter จะสร้าง Method ให้อัตโนมัติ
        } else if (checkChoose()) {

            //true => Have Choose
            confirmValue();

        } else {

            //false => No Choose
            //เรียก class myAlert
            MyAlert myAlert = new MyAlert();
            //Alert ประกอบด้วย icon(รูปภาพ) Title Message ปุ่ม(OK)
            //เรียก Method MyDialog
            myAlert.MyDialog(this, R.drawable.nobita48, "ยังไม่เลือก Avatar", "กรุณากรอกเลือก Avatar ก่อน");

        }

    } //clickSignUpSign

    //return ค่ากลับไปเป็น boolean
    private boolean checkSpace() {

        //กำหนดค่าเริ่มต้นของ result
        boolean result = false;

        //ตรวจสอบแล้วถูก return true
        if (nameString.equals("") || surnameString.equals("") || userString.equals("") || passwordString.equals("")) {
            result = true;
        }

        //return ค่า result กลับไป
        return result;
    }

    private boolean checkChoose() {

        //กำหนดค่าเริ่มต้นของ result
        boolean result = false;

        //ตรวจสอบแล้วถูก return true
        if (avata1RadioButton.isChecked() ||
                avata2RadioButton.isChecked() ||
                avata3RadioButton.isChecked() ||
                avata4RadioButton.isChecked() ||
                avata5RadioButton.isChecked()) {
            result = true;
        }

        //return ค่า result กลับไป
        return result;

    } //checkChoose

    private void confirmValue() {

        //รับค่าจากที่เลือก Avatar มาเก็บไว้
        MyConstant myConstant = new MyConstant();
        int[] avataInts = myConstant.getAvatarInts();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        //แสดงรูปที่เลือก ตามการเลือกจากหน้าจอ
        builder.setIcon(avataInts[Integer.parseInt(avataString)]);
        builder.setTitle("โปรดตรวจสอบข้อมูล");
        builder.setMessage("Name = " + nameString + "\n" +
                "Surname = " + surnameString + "\n" +
                "User = " + userString + "\n" +
                "Password = " + passwordString);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                uploadValueToServer();
                dialog.dismiss();
            }
        });
        builder.show();

    } //confirmValue

    private void uploadValueToServer() {

        //โยน String ทั้งหมดขึ้นไป
        OkHttpClient okHttpClient = new OkHttpClient();
        //กำหนดค่า isAdd เพื่อใช้ใน add_user_droid.php
        //ตัวที่เป็น ชื่อ ต้องเหมือนกับในไฟล์ PHP นะ
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Name", nameString)
                .add("Surname", surnameString)
                .add("User", userString)
                .add("Password", passwordString)
                .add("Svata", avataString)
                .build();
        Request.Builder builder = new Request.Builder();
        //เมื่อไปที่ URL ให้ส่ง requestBody ไปด้วย
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        //ถ้าส่งไม่ได้ ให้ call อีก
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
finish();
            }
        });

    }//uploadValueToServer


} //Main Class
