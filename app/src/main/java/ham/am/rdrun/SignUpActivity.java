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

    } //Main Method

    //ไม่มีการ return ค่ากลับ
    //กำหนดให้มีการมองเห็น โดยใช้ View
    public void clickSignUpSign(View view){

    } //clickSignUpSign

} //Main Class
