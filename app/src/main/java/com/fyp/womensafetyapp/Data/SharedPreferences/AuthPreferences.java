package com.fyp.womensafetyapp.Data.SharedPreferences;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class AuthPreferences {

    public void storeLoginFlag(boolean flag, Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("application",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("login_flag",Boolean.toString(flag));
        editor.apply();
    }

    public Boolean getLoginFlag(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("application",Context.MODE_PRIVATE);
        String flag=sharedPreferences.getString("login_flag","false");
        return Boolean.parseBoolean(flag);
    }

    public void deleteLogin(Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("application",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove("login_flag").apply();
    }
}
