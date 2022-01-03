package com.fyp.womensafetyapp.Data.LocalDBRepo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import com.fyp.womensafetyapp.Models.GuardiansModel;
import com.fyp.womensafetyapp.Models.UserModel;

public class LocalDBRepo extends SQLiteOpenHelper {

    private final static String DB_NAME="Women-Safety-DB";
    private final static String User_TABLE="User";
    private final static String Guardians_TABLE="Guardians";
    private final static int DB_VERSION=1;
    Context context;
    SQLiteDatabase myDB;
    public LocalDBRepo(Context ct)
    {
        super(ct,DB_NAME,null,DB_VERSION);
        context=ct;
    }

//    private LocalDB localDBRepo=new LocalDB();
//    public LocalDBRepo instance()
//    {
//        return  localDBRepo;
//    }

//    public LocalDBRepo()
//    {
//
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+User_TABLE+ "(_uId integer primary key autoincrement,name,number,age)");
        db.execSQL("create table "+Guardians_TABLE+ "(_id integer primary key autoincrement,g1,g2,g3)");
//      FOREIGN KEY (uID) REFERENCES "+User_TABLE+" (uId)
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table "+User_TABLE);
        db.execSQL("drop table "+Guardians_TABLE);
        onCreate(db);
    }
    public void storeUser(UserModel user)
    {
        try {
            myDB=getWritableDatabase();
            myDB.execSQL("insert into "+User_TABLE+" (name,number,age) values('"+user.name+"','"+user.number+"','"+user.age+"');");
            Toast.makeText(context,"User saved Successfully",Toast.LENGTH_SHORT).show();
        }catch (SQLException e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public UserModel fetchUser()
    {
        UserModel userModel=new UserModel("","","");
        myDB=getReadableDatabase();
        Cursor cursor =myDB.rawQuery("select * from "+User_TABLE,null);
        while (cursor.moveToNext())
        {
            String name=cursor.getString(1);
            String number=cursor.getString(2);
            String age=cursor.getString(3);
            userModel=new UserModel(name,number,age);
        }
        return  userModel;
    }

    public void updateUser(UserModel user)
    {
        myDB=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",user.name);
        values.put("number",user.number);
        values.put("age",user.age);
        String where=" _uId like ?";
        String whereArg[]={"1"};
        myDB.update(User_TABLE,values,where,whereArg);
        Toast.makeText(context,"User updated",Toast.LENGTH_SHORT).show();
    }

    public void deleteUser(){
        myDB=getWritableDatabase();
        String where="_id like ?";
        String whereArg[]={"1"};
        int code= myDB.delete(User_TABLE,where,whereArg);
        if (code==1)
            Toast.makeText(context,"Successfully Deleted",Toast.LENGTH_SHORT).show();
    }

    public void storeGuardians(GuardiansModel guardian)
    {
        try {
            myDB=getWritableDatabase();
            myDB.execSQL("insert into "+Guardians_TABLE+" (g1,g2,g3) values('"+guardian.g1+"','"+guardian.g2+"','"+guardian.g3+"');");
            Toast.makeText(context,"Guardians saved Successfully",Toast.LENGTH_SHORT).show();
        }catch (SQLException e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public GuardiansModel fetchGuardians()
    {
        GuardiansModel guardians=new GuardiansModel("","","");
        myDB=getReadableDatabase();
        Cursor cursor =myDB.rawQuery("select * from "+Guardians_TABLE,null);
        while (cursor.moveToNext())
        {
            String g1=cursor.getString(1);
            String g2=cursor.getString(2);
            String g3=cursor.getString(3);
            guardians=new GuardiansModel(g1,g2,g3);
        }
        return  guardians;
    }
    public void updateGuardians(GuardiansModel guardian)
    {
        myDB=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("g1",guardian.g1);
        values.put("g2",guardian.g2);
        values.put("g3",guardian.g3);
        String where=" _uId like ?";
        String whereArg[]={"1"};
        myDB.update(Guardians_TABLE,values,where,whereArg);
        Toast.makeText(context,"Guardian updated",Toast.LENGTH_SHORT).show();
    }

    public void deleteGuardian(){
        myDB=getWritableDatabase();
        String where="_id like ?";
        String whereArg[]={"1"};
        int code= myDB.delete(Guardians_TABLE,where,whereArg);
        if (code==1)
            Toast.makeText(context,"Successfully Deleted",Toast.LENGTH_SHORT).show();
    }

}
