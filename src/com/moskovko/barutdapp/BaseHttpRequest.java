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

public class BaseHttpRequest extends AsyncTask<URL, Integer, String> {
    static public final String REQUEST_RESPONSE = "RequestResponse";

    private ResultReceiver mResultReceiver;

    public BaseHttpRequest(ResultReceiver rr) {
        super();
        mResultReceiver = rr;
    }

    @Override
    protected String doInBackground(URL... urls) {
        String r = null;
        HttpURLConnection huc = null;
        try {
            huc = (HttpURLConnection)urls[0].openConnection();
            r = readResponse(huc.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            huc.disconnect();
        }
        return r;
    }

    @Override
    protected void onPostExecute(String response) {
        if (mResultReceiver != null) {
            Bundle b = new Bundle();
            b.putString(REQUEST_RESPONSE, response);
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

    // TODO: Parse response on the fly from stream:
    private void parseResponse(InputStream is) {
    }
}

