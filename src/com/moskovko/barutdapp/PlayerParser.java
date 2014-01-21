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
    static private final boolean DEBUG = false;

    private String mXML;

    public static class Player {
        public enum Position {
            UNKNOWN, DEFENDER, MIDFIELDER,
            FORWARD, GOALKEEPER;

            @Override
            public String toString() {
                switch (this) {
                case UNKNOWN:
                    return "?";
                case DEFENDER:
                    return "D";
                case MIDFIELDER:
                    return "M";
                case FORWARD:
                    return "F";
                case GOALKEEPER:
                    return "G";
                }
                return "?";
            }
        }

        public String name;
        public int playerID;
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
                    if (xpp.getName().equals("player")) {
                        if (DEBUG) Log.d(TAG, "parse: player");
                        player = new Player();
                    } else if (xpp.getName().equals("name")) {
                        if (player != null) {
                            player.name = xpp.nextText();
                            if (DEBUG) Log.d(TAG, "parse: name: " + player.name);
                        }
                    } else if (xpp.getName().equals("playerId")) {
                        if (player != null) {
                            player.playerID = Integer.parseInt(xpp.nextText());
                            if (DEBUG) Log.d(TAG, "parse: playerId: " + player.playerID);
                        }
                    } else if (xpp.getName().equals("position")) {
                        if (player != null) {
                            player.position = parsePosition(xpp.nextText());
                            if (DEBUG) Log.d(TAG, "parse: position: " + player.position);
                        }
                    } else if (xpp.getName().equals("number")) {
                        if (player != null) {
                            player.number = Integer.parseInt(xpp.nextText());
                            if (DEBUG) Log.d(TAG, "parse: number: " + player.number);
                        }
                    } else if (xpp.getName().equals("country")) {
                        if (player != null) {
                            player.country = xpp.nextText();
                            if (DEBUG) Log.d(TAG, "parse: country: " + player.country);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("player")) {
                        if (player != null) {
                            players.add(player);
                        }
                    }
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

    private Player.Position parsePosition(String position) {
        if (position.equals("D")) {
            return Player.Position.DEFENDER;
        } else if (position.equals("M")) {
            return Player.Position.MIDFIELDER;
        } else if (position.equals("F")) {
            return Player.Position.FORWARD;
        } else if (position.equals("G")) {
            return Player.Position.GOALKEEPER;
        }
        return Player.Position.UNKNOWN;
    }
}

