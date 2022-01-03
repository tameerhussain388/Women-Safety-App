package com.fyp.womensafetyapp.Data.SharedPreferences;
import android.content.Context;
import android.content.SharedPreferences;

public class AuthPreferences {

    public void storeAuthToken(String token, Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("application",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("auth_token",token);
        editor.apply();
    }
    public String getAuthToken(Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("application",Context.MODE_PRIVATE);
        String authToken=sharedPreferences.getString("auth_token","");
        return authToken;
    }

    public boolean deleteToken(Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("application",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove("auth_token").commit();
        return true;
    }
}
