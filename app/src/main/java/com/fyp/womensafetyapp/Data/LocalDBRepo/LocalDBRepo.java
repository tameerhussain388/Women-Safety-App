package com.fyp.womensafetyapp.Data.LocalDBRepo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import com.fyp.womensafetyapp.Models.Guardian;
import com.fyp.womensafetyapp.Models.User;

public class LocalDBRepo extends SQLiteOpenHelper {

    private final Context context;
    private SQLiteDatabase myDB;
    private final static String DB_NAME = "Women-Safety-DB";
    private final static String User_TABLE = "User";
    private final static String Guardians_TABLE = "Guardians";
    private final static int DB_VERSION = 1;

    public LocalDBRepo(Context ct) {
        super(ct, DB_NAME, null, DB_VERSION);
        context = ct;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + User_TABLE + "(_uId integer primary key autoincrement,uID,name,number,age,email)");
        db.execSQL("create table " + Guardians_TABLE + "(_gId integer primary key autoincrement,gID,firstContact,secondContact)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + User_TABLE);
        db.execSQL("drop table " + Guardians_TABLE);
        onCreate(db);
    }

    public void storeUser(User user) {
        try {
            myDB = getWritableDatabase();
            myDB.execSQL("insert into " + User_TABLE + " (uID,name,number,age,email) " +
                    "values('" + user.getId() + "','" + user.getName() + "','" + user.getNumber() + "','" + user.getAge() + "','" + user.getEmail() + "');");
        } catch (SQLException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public User fetchUser() {
        User user = null;
        try {
            myDB = getReadableDatabase();
            Cursor cursor = myDB.rawQuery("select * from " + User_TABLE, null);
            while (cursor.moveToNext()) {
                String id = cursor.getString(1);
                String name = cursor.getString(2);
                String number = cursor.getString(3);
                String age = cursor.getString(4);
                String email = cursor.getString(5);
                user = new User(id, name, number, age, email);
            }
            cursor.close();
            return user;
        } catch (SQLException e) {
            Log.i("Sql Exception :: ", e.getMessage());
            return null;
        }
    }

    public void updateUser(User user) {
        myDB = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("number", user.getNumber());
        values.put("age", user.getAge());
        values.put("email", user.getEmail());
        String where = " uID like ?";
        String[] whereArg = {user.getId()};
        myDB.update(User_TABLE, values, where, whereArg);
    }

    public void deleteUser(String id) {
        myDB = getWritableDatabase();
        String where = "uID like ?";
        String[] whereArg = {id};
        myDB.delete(User_TABLE, where, whereArg);
    }

    public void storeGuardian(Guardian guardian) {
        try {
            Log.i("Guardian ID in Store::", guardian.getId());
            myDB = getWritableDatabase();
            myDB.execSQL("insert into " + Guardians_TABLE + " (gID,firstContact,secondContact) values('" + guardian.getId() + "','" + guardian.getFirstContact() + "','" + guardian.getSecondContact() + "');");
        } catch (SQLException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public Guardian fetchGuardian() {
        Guardian guardian = null;
        try {
            myDB = getReadableDatabase();
            Cursor cursor = myDB.rawQuery("select * from " + Guardians_TABLE, null);
            while (cursor.moveToNext()) {
                String id = cursor.getString(1);
                String firstContact = cursor.getString(2);
                String secondContact = cursor.getString(3);
                guardian = new Guardian(id, firstContact, secondContact);
            }
            cursor.close();
            return guardian;
        } catch (SQLException e) {
            Log.i("Sql Exception :: ", e.getMessage());
            return null;
        }
    }

    public void updateGuardian(Guardian guardian) {
        myDB = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("firstContact", guardian.getFirstContact());
        values.put("secondContact", guardian.getSecondContact());
        String where = "gID like ?";
        String[] whereArg = {guardian.getId()};
        myDB.update(Guardians_TABLE, values, where, whereArg);
    }

    public void deleteGuardian(String id) {
        myDB = getWritableDatabase();
        String where = "gID like ?";
        String[] whereArg = {id};
        myDB.delete(Guardians_TABLE, where, whereArg);
    }
}