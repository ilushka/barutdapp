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
import java.io.Serializable;

public class GameParser {
    static private final String TAG = "GamesParser";

    private String mXML;

    public static class Game implements Serializable {
        public String homeTeamName;
        public String awayTeamName;
        public Date date;
        public int homeScore;
        public int awayScore;
    }

    public GameParser(String xml) {
        mXML = xml;
    }

    public Game[] parse() {
        try {
            ArrayList<Game> games = null;
            Game game = null;
            XmlPullParser xpp = Xml.newPullParser();
            xpp.setInput(new StringReader(mXML));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    games = new ArrayList<Game>();
                    break;
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("game")) {
                        game = new Game();
                    } else if (xpp.getName().equals("homeTeamName")) {
                        if (game != null) {
                            game.homeTeamName = xpp.nextText();
                        }
                    } else if (xpp.getName().equals("awayTeamName")) {
                        if (game != null) {
                            game.awayTeamName = xpp.nextText();
                        }
                    } else if (xpp.getName().equals("homeScore")) {
                        if (game != null) {
                            game.homeScore = Integer.parseInt(xpp.nextText());
                        }
                    } else if (xpp.getName().equals("awayScore")) {
                        if (game != null) {
                            game.awayScore = Integer.parseInt(xpp.nextText());
                        }
                    } else if (xpp.getName().equals("date")) {
                        if (game != null) {
                            game.date = parseDate(xpp.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("game")) {
                        if (game != null) {
                            games.add(game);
                        }
                    }
                    break;
                }
                eventType = xpp.next();
            }
            return games.toArray(new Game[0]);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.parse(dateString, new ParsePosition(0));
    }
}

