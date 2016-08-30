package ham.am.rdrun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SignUpActivity extends AppCompatActivity {

    //ประกาศตัวแปร  เพื่อพร้อมใช้งาน
    //ตัวแปร ประกอบด้วย 3 ส่วน access type name
    //access การเข้าถึง จะเป็น Public(ใช้ได้หมด) Private(ใช้เฉพาะ)
    //type ชนิดของตัวแปร เช่น ตัวเลข ข้อความ(EditText)
    //name ชื่อของตัวแปร
    //กรณีตั้งชื่อ ให้พิมพ์ชื่อที่ต้องการก่อน เช่น name จากนั้นกด Ctrl + space โปรแกรมจะขึ้นชื่อที่ควรใช้ให้ = nameEditText
    private EditText nameEditText, surnameEditText, userEditText, passwordEditText;
    private RadioGroup radioGroup;
    private RadioButton avata1RadioButton, avata2RadioButton, avata3RadioButton, avata4RadioButton, avata5RadioButton;
    private String nameString, surnameString, userString, passwordString, avataString;

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
            myAlert.MyDialog(this, R.drawable.nobita48, "มีช่องว่าง", "กรุณากรอกทุกช่องค่ะ");

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


} //Main Class
