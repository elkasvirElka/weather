package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by 25fli on 20.10.2018.
 */

public class Utils {
    public static JSONObject getObject(String tagName, JSONObject jsonObject) throws JSONException {
        JSONObject jObj = jsonObject.getJSONObject(tagName);
        return jObj;
    }

    public static JSONArray getArray(String tagName, JSONObject jsonObject) throws JSONException {
        JSONArray jObj = jsonObject.getJSONArray(tagName);
        return jObj;
    }

    public static float getFloat(String tagName, JSONObject jsonObject) throws JSONException {
        return (float) jsonObject.getDouble(tagName);
    }

    public static long getLong(String tagName, JSONObject jsonObject) throws JSONException {
        return (long) jsonObject.getLong(tagName);
    }

    public static Integer getInteger(String tagName, JSONObject jsonObject) throws JSONException {
        return (int) jsonObject.getInt(tagName);
    }

    public static int getTemperature(String tagName, JSONObject jsonObject) throws JSONException {
        int temp = (int) jsonObject.getInt(tagName);
        return temp > 100 ? temp - 273 : temp;
    }

    public static String getString(String tagName, JSONObject jsonObject) throws JSONException {
        String res;
        try {
            res = (String) jsonObject.getString(tagName);
        } catch (Exception e) {
            return "";
        }
        return res;
    }

    public static String convertTimeWithTimeZome(long time) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(time);
        String curTime = String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
        return curTime;
    }
}
