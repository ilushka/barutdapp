package com.moskovko.barutdapp;

import android.util.Log;
import android.os.AsyncTask;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.StringBuilder;
import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import android.os.ResultReceiver;
import android.os.Bundle;

public class GetGamesRequest extends AsyncTask<URL, Integer, Void> {
    static private final String TAG = "GetGamesRequest";

    static public final String REQUEST_RESPONSE = "RequestResponse";

    private ResultReceiver mResultReceiver;
    private String mResponse;

    public GetGamesRequest(ResultReceiver rr) {
        super();
        mResultReceiver = rr;
    }

    @Override
    protected Void doInBackground(URL... urls) {
        HttpURLConnection huc = null;
        try {
            huc = (HttpURLConnection)urls[0].openConnection();
            mResponse = readResponse(huc.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            huc.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        if (mResultReceiver != null) {
            Bundle b = new Bundle();
            b.putString(REQUEST_RESPONSE, mResponse);
            mResultReceiver.send(1, b);
        }
    }

    private String readResponse(InputStream is) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void parseResponse(InputStream is) {
/*
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        xpp.setInput(br);
*/
    }
}

