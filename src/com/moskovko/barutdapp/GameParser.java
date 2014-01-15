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
        public String homeTeamName;
        public String awayTeamName;
        public Date date;
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
}

