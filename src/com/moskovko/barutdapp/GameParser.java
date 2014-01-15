package com.moskovko.barutdapp;

import android.util.Log;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import java.io.StringReader;
import java.util.Date;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;

public class GameParser {
    static private final String TAG = "GamesParser";

    private String mXML;

    public class Game {
        public String name;
        public Date date;
        public Game(String name, String date) {
            this.name = name;
        }
    }

    public GameParser(String xml) {
        mXML = xml;
    }

    public Game[] parse() {
        try {
            ArrayList games = null;
            XmlPullParser xpp = Xml.newPullParser();
            xpp.setInput(new StringReader(mXML));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    games = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    Log.d(TAG, "MONKEY: START_TAG: " + xpp.getName());
                    if (xpp.getName().equals("played-at")) {
                        Log.d(TAG, "MONKEY: TEXT: " + xpp.nextText());
                    }
                    break;
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

