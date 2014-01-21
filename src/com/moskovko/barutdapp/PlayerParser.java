package com.moskovko.barutdapp;

import android.util.Log;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import java.io.StringReader;
import java.util.Date;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.text.ParsePosition;

public class PlayerParser {
    static private final String TAG = "PlayerParser";

    private String mXML;

    public static class Player {
        public enum Position {
            DEFENDER;
        }

        public String name;
        public Position position;
        public int number;
        public String country;
    }

    public PlayerParser(String xml) {
        mXML = xml;
    }

    public Player[] parse() {
        try {
            ArrayList<Player> players = null;
            Player player = null;
            XmlPullParser xpp = Xml.newPullParser();
            xpp.setInput(new StringReader(mXML));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    players = new ArrayList<Player>();
                    break;
                case XmlPullParser.START_TAG:
/*
                    if (xpp.getName().equals("player")) {
                        player = new Player();
                    } else if (xpp.getName().equals("homeTeamName")) {
                        if (player != null) {
                            player.homeTeamName = xpp.nextText();
                        }
                    } else if (xpp.getName().equals("awayTeamName")) {
                        if (player != null) {
                            player.awayTeamName = xpp.nextText();
                        }
                    } else if (xpp.getName().equals("homeScore")) {
                        if (player != null) {
                            player.homeScore = Integer.parseInt(xpp.nextText());
                        }
                    } else if (xpp.getName().equals("awayScore")) {
                        if (player != null) {
                            player.awayScore = Integer.parseInt(xpp.nextText());
                        }
                    } else if (xpp.getName().equals("date")) {
                        if (player != null) {
                            player.date = parseDate(xpp.nextText());
                        }
                    }
*/
                    break;
                case XmlPullParser.END_TAG:
/*
                    if (xpp.getName().equals("player")) {
                        if (player != null) {
                            players.add(player);
                        }
                    }
*/
                    break;
                }
                eventType = xpp.next();
            }
            return players.toArray(new Player[0]);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

