package com.example.android.sunshine.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alexandre on 16/09/2016.
 */
public class WeatherDataParser {
    public static double getMaxTemperature(String weatherJsonStr, int dayIndex) throws JSONException {
        JSONObject weather = new JSONObject(weatherJsonStr);
        JSONArray list = weather.getJSONArray("list");
        JSONObject day = list.getJSONObject(dayIndex);
        JSONObject temp = day.getJSONObject("temp");
        return temp.getDouble("max");

    }
}
