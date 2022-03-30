package com.fyp.womensafetyapp.Utils;

import android.util.Log;
import android.widget.Toast;
import android.content.Context;

import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.FirebaseUser;
import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.FirebaseGuardian;

public class SetterFetcherHelper {

    private static final SetterFetcherHelper instance = new SetterFetcherHelper();

    public static SetterFetcherHelper getInstance() {
        return instance;
    }

    public void dataSetter(Context context) {
        LocalDBRepo localDBRepo = new LocalDBRepo(context);
        if (NetworkHelper.getInstance().haveNetworkConnection(context)) {
            if (!LocalDBHelper.getInstance().hasUserData(context)) {
                if (FirebaseUser.getUser() != null) {
                    localDBRepo.storeUser(FirebaseUser.getUser());
                } else {
                    Toast.makeText(context, "Something went wrong getting user from firebase", Toast.LENGTH_LONG).show();
                }

            }
            if (!LocalDBHelper.getInstance().hasGuardiansData(context)) {
                if (FirebaseGuardian.guardian != null) {
                    Log.i("guardians if", "Guardians if called");
                    localDBRepo.storeGuardian(FirebaseGuardian.guardian);
                } else {
                    Log.i("guardians else", "Guardians else called");
                }
            }
        } else {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    public void dataFetcher(Context context) {
        if (NetworkHelper.getInstance().haveNetworkConnection(context)) {
            FirebaseUser.fetchUser(Firebase_Auth.getInstance().getUid(), context);
            FirebaseGuardian.fetchGuardians(Firebase_Auth.getInstance().getUid(), context);
        } else {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
        }
    }
}
