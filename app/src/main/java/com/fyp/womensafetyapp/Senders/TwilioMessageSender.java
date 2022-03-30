package com.fyp.womensafetyapp.Senders;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fyp.womensafetyapp.Models.Guardian;
import com.fyp.womensafetyapp.R;
import com.fyp.womensafetyapp.Interfaces.LoaderCallback;
import com.fyp.womensafetyapp.Utils.Message;
import com.fyp.womensafetyapp.Utils.MySingleton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TwilioMessageSender {

    private final Context context;

    public TwilioMessageSender(Context context) {
        this.context = context;
    }

    public void sendMessage(Guardian guardian, Location location, LoaderCallback callback) {

        final String URL = context.getString(R.string.server_url);
        JSONObject jsonRequest = getJsonObject(guardian, location);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, URL, jsonRequest,
                response -> {
                    if(callback != null) callback.finish();
                    Toast.makeText(context, "Alert Sent", Toast.LENGTH_LONG).show();
                }, error -> {
            LocalMessageSender localMessageSender = new LocalMessageSender(context);
            localMessageSender.sendMessage(guardian,location,callback);
        });

        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    private JSONObject getJsonObject(Guardian guardian, Location location) {
        try {
            JSONObject jsonRequest = new JSONObject();
            String message = Message.getCurrentMessage(location.getLatitude(), location.getLongitude());
            JSONArray numbers = new JSONArray();
            numbers.put(guardian.getFirstContact());
            numbers.put(guardian.getSecondContact());
            jsonRequest.put("body", message);
            jsonRequest.put("numbers", numbers);
            return jsonRequest;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}