package com.example.a25fli.weather;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ServerRequest {
    /**
     * url для определения погоды(на сейчас, по часам, на неделю)
     */
    private String url;
    /**
     * url_img достает картинку
     */
    private String url_img;
    private OkHttpClient client;
    /**
     * мой id = "641c34e1a35454994cc7fc2921d395de"; он не работает:( пришлось взять из примера
     */
    private String appId = "b6907d289e10d714a6e88b30761fae22";// - это тестовый id


    /**
     * определение ip сервера, к которому обращаемся и порта
     */
    public ServerRequest(String url, String url_img) {
        this.url = url;
        this.url_img = url_img;
        client = new OkHttpClient();
    }
    /**
     * строим строку
     * */
    private HttpUrl.Builder getHttpBuilder() {
        return new HttpUrl.Builder().scheme("https").host(url);
    }

    /**
     * Гет запрос по url
     * @param url
     * @param callback
     */
    private void makeGetRequest(HttpUrl url, Callback callback) {
        client.newCall(new Request.Builder().url(url).build()).enqueue(callback);
    }
    /**
     * Пост запрос по url
     * @param url
     * @param body
     * @param callback
     */
    private void makePostRequest(HttpUrl url, RequestBody body, Callback callback) {
        client.newCall(new Request.Builder().url(url).post(body).build()).enqueue(callback);
    }
    /**
     * Обращение к серверному получения погоды на сейчас по коорд.
     * */
    public void getWeatherByCoordinate(Callback callback, String lat, String lon) {
        makeGetRequest(getHttpBuilder().addPathSegment("data").addPathSegment("2.5").addPathSegment("weather")
                .addQueryParameter("lat", lat).addQueryParameter("lon", lon).addQueryParameter("appid", appId)
                .build(), callback);
    }
    /**
     * Обращение к серверному получения погоды на 5 дней по 3 часа по коорд.
     * */
    public void getHoursWeaterByCoordinate(Callback callback, String lat, String lon) {
        makeGetRequest(getHttpBuilder().addPathSegment("data").addPathSegment("2.5").addPathSegment("forecast")
                .addQueryParameter("lat", lat).addQueryParameter("lon", lon).addQueryParameter("appid", appId)
                .build(), callback);
    }
    /**
     * Получить погоду на неделю по координатам
     */
    public void getWeatherForWeekByCoordinate(Callback callback, String lat, String lon) {
        makeGetRequest(getHttpBuilder().addPathSegment("data").addPathSegment("2.5").addPathSegment("forecast").addPathSegment("daily")
                .addQueryParameter("lat", lat).addQueryParameter("lon", lon)
                .addQueryParameter("cnt", "7").addQueryParameter("appid", appId)
                .build(), callback);
    }

    /**
     * Получение текущей погоды по названию города
     * @param callback
     * @param city
     */
    public  void getWeatherByCityName(Callback callback, String city) {
        makeGetRequest(getHttpBuilder().addPathSegment("data").addPathSegment("2.5").addPathSegment("find")
                .addQueryParameter("q", city).addQueryParameter("type", "like").addQueryParameter("appid", appId)
                .build(), callback);
    }
    /**
     * Получение по 3 часа погоды по названию города
     * @param callback
     * @param city
     */
    public  void getHoursWeaterByCityName(Callback callback, String city) {
        makeGetRequest(getHttpBuilder().addPathSegment("data").addPathSegment("2.5").addPathSegment("forecast")
                .addQueryParameter("q", city).addQueryParameter("type", "like").addQueryParameter("appid", appId)
                .build(), callback);
    }
    /**
     * Получение погоды на неделю по названию города
     * @param callback
     * @param city
     */
    public  void getWeatherForWeekByCityName(Callback callback, String city) {
        makeGetRequest(getHttpBuilder().addPathSegment("data").addPathSegment("2.5").addPathSegment("forecast").addPathSegment("daily")
                .addQueryParameter("q", city).addQueryParameter("type", "like").addQueryParameter("appid", appId)
                .build(), callback);
    }
    /**
     * Достаем картинку погоды
     * @param icon_code
     * @return
     */
    public void getIcon(Callback callback, String icon_code) {
        makeGetRequest(new HttpUrl.Builder().scheme("https").host(url_img).addPathSegment("img").addPathSegment("w")
                .addPathSegment(icon_code + ".png")
                .build(), callback);
    }

}

