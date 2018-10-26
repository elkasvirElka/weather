package com.example.a25fli.weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import utils.Utils;
import weathermodel.Coordi;
import weathermodel.Main;
import weathermodel.Temp;
import weathermodel.Weather;
import weathermodel.WeatherData;
import weathermodel.WeatherWeekData;
import weathermodel.Wind;

/**
 * Created by 25fli on 20.10.2018.
 */

public class WeatherClass {
    public static WeatherData getWeather(String data) {

        WeatherData wd = new WeatherData();
        try {
            //TODO dt??
            JSONObject jsonObject = new JSONObject(data);
            wd.setID(Utils.getString("id", jsonObject));
            wd.setName(Utils.getString("name", jsonObject));
            wd.setDt(new Date(Utils.getLong("dt", jsonObject)));
            JSONObject coordData = Utils.getObject("coord", jsonObject);
//Coord
            Coordi coordi = new Coordi(Utils.getString("lat", coordData), Utils.getString("lon", coordData));
            wd.setCoordinates(coordi);
            //Main
            JSONObject mainData = Utils.getObject("main", jsonObject);
            //usually the temp in Kelvin
            //TODO temp_min, temp_max
            int temp = Utils.getInteger("temp", mainData);
            Main main = new Main(
                    temp > 273 ? temp - 273 : temp,
                    Utils.getInteger("pressure", mainData).toString(),
                    Utils.getString("humidity", mainData),
                    Utils.getString("temp_min", mainData),
                    Utils.getString("temp_max", mainData)
            );
            wd.setMain(main);

            JSONArray weatherData = Utils.getArray("weather", jsonObject);

            Weather weather = new Weather(
                    Utils.getString("main", (JSONObject) weatherData.get(0)),
                    Utils.getString("icon", (JSONObject) weatherData.get(0)),
                    Utils.getString("description", (JSONObject) weatherData.get(0))
            );
            JSONObject wind = Utils.getObject("wind", jsonObject);
            Wind windIndo = new Wind(
                    Utils.getInteger("speed", wind).toString() + " m/s",
                    Utils.getString("deg", wind)
            );

            wd.setWind(windIndo);

            wd.setWeather(weather);

        } catch (Exception e) {
            System.out.println("Weather parsing error. please try again.");
            e.printStackTrace();
        }
        return wd;
    }

    public static ArrayList<WeatherData> getWeatherList(String data,int daysNumb) {

        ArrayList<WeatherData> wdList = new ArrayList<WeatherData>() {
        };

        try {
            //TODO dt??
            JSONObject jsonObjectList = new JSONObject(data);


            JSONArray list = Utils.getArray("list", jsonObjectList);
            int i = 0;
            while (i < daysNumb) {//только сегодняшний день или list.length() 8
                WeatherData wd = new WeatherData();
                JSONObject jsonObject = (JSONObject) list.get(i);

                wd.setName(Utils.getString("name", jsonObject));

                //TODO SimpleDateFormat 2 параметр, если буду переводить на 2 языка, Locale.ENGLISH
                String dt_txt = Utils.getString("dt_txt", jsonObject);
                if(dt_txt.length() > 0) {
                    Date dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dt_txt);
                    long theDate = dt != null ? dt.getTime() : 0;
                    wd.setDt_txt(Utils.convertTimeWithTimeZome(theDate));
                }

                //Main
                JSONObject mainData = Utils.getObject("main", jsonObject);
                //usually the temp in Kelvin
                //TODO temp_min, temp_max
                int temp = Utils.getTemperature("temp", mainData);
                Main main = new Main(
                        temp,
                        Utils.getString("pressure", mainData),
                        Utils.getString("humidity", mainData),
                        Utils.getString("temp_min", mainData),
                        Utils.getString("temp_max", mainData)
                );
                wd.setMain(main);

                JSONObject wind = Utils.getObject("wind", jsonObject);
                Wind windInfo = new Wind(
                        Utils.getInteger("speed", wind).toString() + " m/s",
                        Utils.getString("deg", wind)
                );
                wd.setWind(windInfo);

                JSONArray weatherData = Utils.getArray("weather", jsonObject);

                Weather weather = new Weather(
                        Utils.getString("main", (JSONObject) weatherData.get(0)),
                        Utils.getString("icon", (JSONObject) weatherData.get(0)),
                        Utils.getString("description", (JSONObject) weatherData.get(0))
                );

                wd.setWeather(weather);

                i++;
                wdList.add(wd);
            }

        } catch (Exception e) {
            System.out.println("Weather parsing error. please try again.");
            e.printStackTrace();
        }
        return wdList;
    }
    public static ArrayList<WeatherWeekData> getWeatherForWeek(String data) {

        ArrayList<WeatherWeekData> wdList = new ArrayList<WeatherWeekData>();

        try {
            //TODO dt??
            JSONObject jsonObjectList = new JSONObject(data);
            JSONArray list = Utils.getArray("list", jsonObjectList);
            int i = 0;
            while (i < 7) {//только сегодняшний день или list.length()
                WeatherWeekData wd = new WeatherWeekData();
                JSONObject jsonObject = (JSONObject) list.get(i);

                //TODO день недели
                Date myDate = new Date(Utils.getLong("dt", jsonObject)*1000);
                //Locale locale = new Locale("ENGLISH"); если переводить на 2 языка
                String weekDayText = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(myDate);
                wd.setDt(weekDayText);

                //Main
                JSONObject mainData = list.getJSONObject(i);
                //usually the temp in Kelvin
                //TODO temp_min, temp_max
                JSONObject temp = Utils.getObject("temp", mainData);
                Temp temperature = new Temp(
                        Utils.getTemperature("day", temp),
                        Utils.getTemperature("min", temp),
                        Utils.getTemperature("max", temp),
                        Utils.getTemperature("night", temp),
                        Utils.getTemperature("eve", temp),
                        Utils.getTemperature("morn", temp)
                );
                wd.setTemperature(temperature);

                JSONArray weatherData = Utils.getArray("weather", jsonObject);

                Weather weather = new Weather(
                        Utils.getString("main", (JSONObject) weatherData.get(0)),
                        Utils.getString("icon", (JSONObject) weatherData.get(0)),
                        Utils.getString("description", (JSONObject) weatherData.get(0))
                );

                wd.setWeather(weather);
                i++;
                wdList.add(wd);
            }

        } catch (Exception e) {
            System.out.println("Weather parsing error. please try again.");
            e.printStackTrace();
        }
        return wdList;
    }

}
