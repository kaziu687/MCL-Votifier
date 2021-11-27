package pl.ibcgames.mclvotifier;

import org.bukkit.ChatColor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {
    public static String message(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static JSONObject sendRequest(String url) {
        try {
            URL _url = new URL(url);
            HttpURLConnection con = (HttpURLConnection) _url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setConnectTimeout(5 * 1000);

            int status = con.getResponseCode();
            Reader streamReader = null;

            if (status > 299) {
                streamReader = new InputStreamReader(con.getErrorStream());
            } else {
                streamReader = new InputStreamReader(con.getInputStream());
            }

            BufferedReader in = new BufferedReader(streamReader);
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(content.toString());
            return json;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }
}
