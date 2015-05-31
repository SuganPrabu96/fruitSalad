package util;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import app.TheDreamStop.Master;

/**
 * Created by Suganprabu on 31-05-2015.
 */
public class GetLatLong extends AsyncTask<String,Void,String>{

    private String latLongReturnedJSON;
    @Override
    protected String doInBackground(String... params) {

        String str1 = "", str2 = "", uri;
        if(params[0].contains(" ")){
            str1 = params[0].split(" ")[0];
            str2 = params[0].split(" ")[1];
        }
        if(!params[0].contains(" "))
            uri = "http://maps.google.com/maps/api/geocode/json?address=" + params[0] + "&sensor=false";
        else
            uri = "http://maps.google.com/maps/api/geocode/json?address=" + str1 + "%20" + str2 + "$sensor=false";
        HttpGet httpGet = new HttpGet(uri);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            double lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            double lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            Log.d("latitude", "" + lat);
            Log.d("longitude", "" + lng);

            Bundle b = new Bundle();
            b.putDouble("lat", lat);
            b.putDouble("long", lng);

            Message msg = new Message();
            msg.arg1 = 1;
            msg.setData(b);

            Master.latLongHandler.sendMessage(msg);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
