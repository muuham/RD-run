package ham.am.rdrun;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by user on 8/30/2016.
 */
public class MyAlert {

    //Method ที่มีการใช้ อากิวเมนต์
    //Alert ประกอบด้วย icon(รูปภาพ) Title Message ปุ่ม(OK)
    public void MyDialog(Context context,int intIcon, String strTitle, String strMessage) {
        //เลือก AlertDialog ของ Android.app
        //ทำการต่อท่อ ตัวแปร ต้องชื่อเดียวกันกับข้างบนนะ
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //กำหนดให้ปุ่ม undo ไม่ทำงาน
        builder.setCancelable(false);
        //รูปภาพ
        builder.setIcon(intIcon);
        //ชื่อเรื่อง
        builder.setTitle(strTitle);
        //ข้อความ
        builder.setMessage(strMessage);
        //ปุ่ม ประกอบด้วย ชื่อปุ่ม และสิ่งที่ต้องทำ
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //ไม่แสดง popup
                dialog.dismiss();
            }
        });
        builder.show();
    }

} //Main Class
