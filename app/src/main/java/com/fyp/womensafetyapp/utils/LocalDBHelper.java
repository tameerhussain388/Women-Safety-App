package com.fyp.womensafetyapp.utils;
import android.content.Context;
import android.widget.Toast;
import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.Models.GuardiansModel;
import com.fyp.womensafetyapp.Models.UserModel;

public class LocalDBHelper {

    private static LocalDBHelper instance=new LocalDBHelper();
    public static LocalDBHelper getInstance()
    {
        return instance;
    }

    public boolean hasUserData(Context context)
    {
        try {
            UserModel user= new LocalDBRepo(context).fetchUser();
            if(!user.name.isEmpty())
            {
                return true;
            }else
            {
                return false;
            }
        }catch (Exception e)
        {
            Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    public boolean hasGuardiansData(Context context)
    {
        try {
            GuardiansModel guardian= new LocalDBRepo(context).fetchGuardians();
            return !guardian.g1.isEmpty();
        }catch (Exception e)
        {
            Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
