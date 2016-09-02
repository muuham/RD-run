package ham.am.rdrun;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 9/2/2016.
 */
//เมื่อพิมพ์ SQLiteOpenHelper ให้กด alt + Enter
public class MyOpenHelper extends SQLiteOpenHelper{

    //Explicit
    //public static final <= ไม่สามารถแก้ไขได้
    //ชื่อ Database อย่าลืม .db
    public static final String database_name = "rdRun.db";
    private static final int database_version = 1;
    //สร้าง ตาราง
    private static final String create_user_table = "create table userTABLE(" +
        "_id integer primary key," +
            "User text," +
            "Password text," +
            "Name text," +
            "Surname text," +
            "Avata text," +
            "idUser text);";

    //เมื่อพิมพ์ public MyOpenHelper(Context context) ให้กด alt + Enter -> Insert Super...
    public MyOpenHelper(Context context) {
        //ความหมาย super(context , database_name <= ชื่อ database, null <= security ไม่มี, database_version <= version);
        super(context, database_name, null, database_version);
    }//constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_user_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
} //Main Class
