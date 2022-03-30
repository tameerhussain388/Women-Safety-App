package com.fyp.womensafetyapp.Utils;
import android.content.Context;
import android.widget.Toast;
import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.Models.Guardian;
import com.fyp.womensafetyapp.Models.User;

public class LocalDBHelper {

    private static final LocalDBHelper instance = new LocalDBHelper();

    public boolean hasUserData(Context context)
    {
        try {
            User user= new LocalDBRepo(context).fetchUser();
            return user != null;
        }catch (Exception e)
        {
            Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean hasGuardiansData(Context context) {
        try {
            Guardian guardian= new LocalDBRepo(context).fetchGuardian();
            return guardian != null;
        }catch (Exception e)
        {
            Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static LocalDBHelper getInstance()
    {
        return instance;
    }
}
