package com.moskovko.barutdapp;

import android.util.Log;

public class GetGamesRequest extends AsyncTask<URL, Integer, void> {
    static private final String TAG = "ScheduleRequest";

    @Override
    protected void doInBackground(URL... urls) {
        HttpUrlConnection huc = (HttpUrlConnection)url[0].openConnection();
        try {
            parseResponse(huc.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            huc.disconnect();
        }
    }

    @Override
    protected void onPostExecute(void) {
        Log.d(TAG, "MONKEY: onPostExecute");
    }

    private void parseResponse(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
/*
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        xpp.setInput(br);
*/
    }
}

