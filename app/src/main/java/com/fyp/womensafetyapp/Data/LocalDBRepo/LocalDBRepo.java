package com.fyp.womensafetyapp.Data.LocalDBRepo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
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

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+User_TABLE+ "(_uId integer primary key autoincrement,uID,name,number,age,email)");
        db.execSQL("create table "+Guardians_TABLE+ "(_gId integer primary key autoincrement,gID,g1,g2)");
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
            myDB.execSQL("insert into "+User_TABLE+" (uID,name,number,age,email) values('"+user.uID+"','"+user.name+"','"+user.number+"','"+user.age+"','"+user.email+"');");
            Toast.makeText(context,"user saved in local db",Toast.LENGTH_SHORT).show();
        }catch (SQLException e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public UserModel fetchUser()
    {
        try {
            UserModel userModel=new UserModel("","","","","");
            myDB=getReadableDatabase();
            Cursor cursor =myDB.rawQuery("select * from "+User_TABLE,null);
            while (cursor.moveToNext())
            {
                String uID=cursor.getString(1);
                String name=cursor.getString(2);
                String number=cursor.getString(3);
                String age=cursor.getString(4);
                String email=cursor.getString(5);
                userModel=new UserModel(uID,name,number,age,email);
            }
            cursor.close();
            return  userModel;
        }catch (SQLException e)
        {
            Log.i("Sql Exception :: ",e.getMessage());
            return null;
        }
    }

    public void updateUser(UserModel user)
    {
        myDB=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",user.name);
        values.put("number",user.number);
        values.put("age",user.age);
        values.put("email",user.email);
        String where=" uID like ?";
        String[] whereArg = {user.uID};
        myDB.update(User_TABLE,values,where,whereArg);
    }

    public void deleteUser(String uID){
        myDB=getWritableDatabase();
        String where="uID like ?";
        String[] whereArg ={uID};
        myDB.delete(User_TABLE,where,whereArg);
        Toast.makeText(context,"user deleted from local db",Toast.LENGTH_SHORT).show();
    }

    public void storeGuardians(GuardiansModel guardian)
    {
        try {
            Log.i("Guardian ID in Store::",guardian.gID);
            myDB=getWritableDatabase();
            myDB.execSQL("insert into "+Guardians_TABLE+" (gID,g1,g2) values('"+guardian.gID+"','"+guardian.g1+"','"+guardian.g2+"');");
        }catch (SQLException e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public GuardiansModel fetchGuardians()
    {
        GuardiansModel guardian = null;
        try{
            myDB=getReadableDatabase();
            Cursor cursor =myDB.rawQuery("select * from "+Guardians_TABLE,null);
            while (cursor.moveToNext())
            {
                String gID=cursor.getString(1);
                String g1=cursor.getString(2);
                String g2=cursor.getString(3);
                guardian =new GuardiansModel(gID,g1,g2);
            }
            cursor.close();
            return guardian;
        }catch (SQLException e)
        {
            Log.i("Sql Exception :: ",e.getMessage());
            return null;
        }
    }
    public void updateGuardians(GuardiansModel guardian)
    {
        myDB=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("g1",guardian.g1);
        values.put("g2",guardian.g2);
        String where="gID like ?";
        String[] whereArg ={guardian.gID};
        myDB.update(Guardians_TABLE,values,where,whereArg);
    }

    public void deleteGuardian(String gID){
        myDB=getWritableDatabase();
        String where="gID like ?";
        String[] whereArg = {gID};
        myDB.delete(Guardians_TABLE,where,whereArg);
    }
}