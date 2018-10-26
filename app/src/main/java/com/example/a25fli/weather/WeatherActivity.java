package com.example.a25fli.weather;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import weathermodel.WeatherData;
import weathermodel.WeatherWeekData;

public class WeatherActivity extends Activity {


    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context myContext = this;
        setContentView(R.layout.activity_weather);

        setBackground(myContext);

        String lat = "";
        String lon = "";
        String city = "";
        if (getIntent().getStringExtra("lat") != null) {
            lat = new String(getIntent().getStringExtra("lat"));
            lon = new String(getIntent().getStringExtra("lon"));
        } else if (getIntent().getStringExtra("city") != null) {
            city = new String(getIntent().getStringExtra("city"));
        }
        if (city.length() > 0) {
            final TextView cityName = (TextView) findViewById(R.id.cityName);
            final TextView tempText = (TextView) findViewById(R.id.tempText);
            final TextView clouds = (TextView) findViewById(R.id.clouds);
            final TextView pressure = (TextView) findViewById(R.id.pressure);
            final TextView wind = (TextView) findViewById(R.id.wind);

            getTodayHoursWeather(city);
            getTodayWeather(city);
            getWeekWeather(city);

        } else {

            getTodayHoursWeather(lat,lon);
            getTodayWeather(lat,lon);
            getWeekWeather(lat,lon);
        }
        /**
         * Переопределение метода кнопки для запроса пермишенов и шаринга картинки
         */
        Button myB = findViewById(R.id.buttonShare);
        View.OnClickListener lis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @Override
                        public void run() {

                            if (ContextCompat.checkSelfPermission(WeatherActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(WeatherActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                                // }
                            } else {
                                View view = getWindow().getDecorView().getRootView();
                                view.setDrawingCacheEnabled(true);
                                Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
                                view.setDrawingCacheEnabled(false);
                                File file = saveBitmap(bitmap, "my_images.png");
                                Log.i("chase", "filepath: " + file.getAbsolutePath());
                                // Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
                                Uri uri = FileProvider.getUriForFile(myContext, "com.example.a25fli.weather", file);

                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                sendIntent.setType("image/*");
                                sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                startActivity(Intent.createChooser(sendIntent, "Image"));
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.i("chase", "");
                }
            }
        };
        myB.setOnClickListener(lis);
    }

    /***
     * Задний фон, меняется от времени года:)
     * @param myContext
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setBackground(Context myContext) {
        View activity_weather = findViewById(R.id.activity_weather);
        switch (new Date().getMonth() + 1) {
            case 12:
            case 1:
            case 2:
                activity_weather.setBackground(ContextCompat.getDrawable(myContext, R.drawable.w6));
                break;
            case 3:
            case 4:
            case 5:
                activity_weather.setBackground(ContextCompat.getDrawable(myContext, R.drawable.w4));
                break;
            case 6:
            case 7:
            case 8:
                activity_weather.setBackground(ContextCompat.getDrawable(myContext, R.drawable.w7));
                break;
            case 9:
            case 10:
            case 11:
                activity_weather.setBackground(ContextCompat.getDrawable(myContext, R.drawable.w4));
                break;
        }
    }

    private File saveBitmap(Bitmap bm, String fileName) {

        final String path = Environment.getExternalStorageDirectory().getAbsolutePath();//this.getCacheDir().toString();
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dir, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Button myB = findViewById(R.id.buttonShare);
                    myB.setOnClickListener((View.OnClickListener) myB);

                } else {
                    Activity activity = WeatherActivity.this;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast error = Toast.makeText(WeatherActivity.this, "Ошибка создания прав", Toast.LENGTH_LONG);
                            error.show();
                        }
                    });
                }
                return;
            }
        }
    }

    private void getWeekWeather(String lat,String lon) {
        MainActivity.server.getWeatherForWeekByCoordinate(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Activity activity = WeatherActivity.this;
                if (activity == null)
                    return; // ToDo
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast error = Toast.makeText(WeatherActivity.this, "Connection error", Toast.LENGTH_LONG);
                        error.show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response)
                    throws IOException {
                if (response.code() != 200)
                    return;

                InputStream in = (InputStream) response.body().byteStream();
                try {

                    StringBuffer stringBuffer = new StringBuffer();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line + "\r\n");
                    }
                    in.close();

                    final ArrayList<WeatherWeekData> wd = WeatherClass.getWeatherForWeek(stringBuffer.toString());
                    final LinearLayout myToolbar = findViewById(R.id.weatherWeek);

                    runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @Override
                        public void run() {


                            for (WeatherWeekData item : wd) {

                                final LinearLayout myLayout = new LinearLayout(WeatherActivity.this);
                                myLayout.setOrientation(LinearLayout.HORIZONTAL);

                                final TextView textView = new TextView(WeatherActivity.this);
                                textView.setTextSize(20);
                                textView.setText(item.getDt());
                                //textView.setOnTouchListener(myContext);
                                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                llp.setMargins(30, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
                                llp.width = 350;
                                textView.setLayoutParams(llp);
                                myLayout.addView(textView);

                                ImageView myImg = new ImageView(WeatherActivity.this);
                                getIconImg(item.getWeather().getIcon(), myImg);
                                int width = 100;
                                int height = 100;
                                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
                                myImg.setLayoutParams(parms);
                                myLayout.addView(myImg);

                                final TextView textViewClouds = new TextView(WeatherActivity.this);
                                textViewClouds.setText(item.getWeather().getMain());
                                textViewClouds.setTextSize(17);
                                LinearLayout.LayoutParams llpClouds = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                llpClouds.setMargins(0, 0, 100, 0); // llp.setMargins(left, top, right, bottom);
                                llpClouds.width = 190;
                                textViewClouds.setLayoutParams(llpClouds);
                                myLayout.addView(textViewClouds);

                                final TextView textViewTemp = new TextView(WeatherActivity.this);
                                textViewTemp.setText(item.getTemperature().getDay() + "ºC");
                                textViewTemp.setTextSize(20);
                                textViewTemp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                myLayout.addView(textViewTemp);

                                myToolbar.addView(myLayout);
                            }
                            //  myToolbar.refreshDrawableState();

                        }
                    });


                } catch (Exception e) {
                    System.out.println("Weather parsing error. please try again or contact app developer.");
                    e.printStackTrace();
                }
            }

        }, lat, lon);
    }
    private void getWeekWeather(String city){
        MainActivity.server.getWeatherForWeekByCityName(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Activity activity = WeatherActivity.this;
                if (activity == null)
                    return; // ToDo
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast error = Toast.makeText(WeatherActivity.this, "Connection error", Toast.LENGTH_LONG);
                        error.show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response)
                    throws IOException {
                if (response.code() != 200)
                    return;

                InputStream in = (InputStream) response.body().byteStream();
                try {

                    StringBuffer stringBuffer = new StringBuffer();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line + "\r\n");
                    }
                    in.close();

                    final ArrayList<WeatherWeekData> wd = WeatherClass.getWeatherForWeek(stringBuffer.toString());
                    final LinearLayout myToolbar = findViewById(R.id.weatherWeek);

                    runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @Override
                        public void run() {


                            for (WeatherWeekData item : wd) {

                                final LinearLayout myLayout = new LinearLayout(WeatherActivity.this);
                                myLayout.setOrientation(LinearLayout.HORIZONTAL);

                                final TextView textView = new TextView(WeatherActivity.this);
                                textView.setTextSize(20);
                                textView.setText(item.getDt());
                                //textView.setOnTouchListener(myContext);
                                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                llp.setMargins(30, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
                                llp.width = 350;
                                textView.setLayoutParams(llp);
                                myLayout.addView(textView);

                                ImageView myImg = new ImageView(WeatherActivity.this);
                                getIconImg(item.getWeather().getIcon(), myImg);
                                int width = 100;
                                int height = 100;
                                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
                                myImg.setLayoutParams(parms);
                                myLayout.addView(myImg);

                                final TextView textViewClouds = new TextView(WeatherActivity.this);
                                textViewClouds.setText(item.getWeather().getMain());
                                textViewClouds.setTextSize(20);
                                LinearLayout.LayoutParams llpClouds = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                llpClouds.setMargins(0, 0, 100, 0); // llp.setMargins(left, top, right, bottom);
                                llpClouds.width = 190;
                                textViewClouds.setLayoutParams(llpClouds);
                                myLayout.addView(textViewClouds);

                                final TextView textViewTemp = new TextView(WeatherActivity.this);
                                textViewTemp.setText(item.getTemperature().getDay() + "ºC");
                                textViewTemp.setTextSize(20);
                                textViewTemp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                myLayout.addView(textViewTemp);

                                myToolbar.addView(myLayout);
                            }
                            //  myToolbar.refreshDrawableState();

                        }
                    });


                } catch (Exception e) {
                    System.out.println("Weather parsing error. please try again or contact app developer.");
                    e.printStackTrace();
                }
            }

        }, city);
    }

    private void getTodayWeather(String lat,String lon) {
        final TextView cityName = (TextView) findViewById(R.id.cityName);
        final TextView tempText = (TextView) findViewById(R.id.tempText);
        final TextView clouds = (TextView) findViewById(R.id.clouds);
        final TextView pressure = (TextView) findViewById(R.id.pressure);
        final TextView wind = (TextView) findViewById(R.id.wind);

        MainActivity.server.getWeatherByCoordinate(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Activity activity = WeatherActivity.this;
                if (activity == null)
                    return; // ToDo
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast error = Toast.makeText(WeatherActivity.this, "Connection error", Toast.LENGTH_LONG);
                        error.show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response)
                    throws IOException {
                if (response.code() != 200)
                    return;

                InputStream in = (InputStream) response.body().byteStream();
                try {

                    StringBuffer stringBuffer = new StringBuffer();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line + "\r\n");
                    }
                    in.close();

                    final WeatherData wd = WeatherClass.getWeather(stringBuffer.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            cityName.setText(wd.getName());
                            tempText.setText(wd.getMain() != null ? wd.getMain().getTemp().toString() + "ºC" : "-");
                            clouds.setText(wd.getWeather() != null ? wd.getWeather().getDescription() : "-");
                            wind.setText("Wind: " + (wd.getWind() != null ? wd.getWind().getSpeed() : "-"));
                            String press = wd.getMain() != null ? wd.getMain().getPressure() : "0";
                            pressure.setText("Pressure: " + press + " hPa");
                        }
                    });


                } catch (Exception e) {
                    System.out.println("Weather parsing error. please try again or contact app developer.");
                    e.printStackTrace();
                }
            }

        }, lat, lon);
    }

    private void getTodayWeather(String city) {
        final TextView cityName = (TextView) findViewById(R.id.cityName);
        final TextView tempText = (TextView) findViewById(R.id.tempText);
        final TextView clouds = (TextView) findViewById(R.id.clouds);
        final TextView pressure = (TextView) findViewById(R.id.pressure);
        final TextView wind = (TextView) findViewById(R.id.wind);

        MainActivity.server.getWeatherByCityName(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Activity activity = WeatherActivity.this;
                if (activity == null)
                    return; // ToDo
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast error = Toast.makeText(WeatherActivity.this, "Connection error", Toast.LENGTH_LONG);
                        error.show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response)
                    throws IOException {
                if (response.code() != 200)
                    return;

                InputStream in = (InputStream) response.body().byteStream();
                try {

                    StringBuffer stringBuffer = new StringBuffer();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line + "\r\n");
                    }
                    in.close();

                    final ArrayList<WeatherData> wdList = WeatherClass.getWeatherList(stringBuffer.toString(), 1);
                    final WeatherData wd = wdList.get(0);
                    // final LinearLayout myToolbar = findViewById(R.id.weatherWeek);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cityName.setText(wd.getName());
                            tempText.setText(wd.getMain() != null ? wd.getMain().getTemp().toString() + "ºC" : "-");
                            clouds.setText(wd.getWeather() != null ? wd.getWeather().getDescription() : "-");
                            wind.setText("Wind: " + (wd.getWind() != null ? wd.getWind().getSpeed() : "-"));
                            String press = wd.getMain() != null ? wd.getMain().getPressure() : "0";
                            pressure.setText("Pressure: " + press + " hPa");
                        }
                    });


                } catch (Exception e) {
                    System.out.println("Weather parsing error. please try again or contact app developer.");
                    e.printStackTrace();
                }
            }

        }, city);
    }

    private void getTodayHoursWeather(String lat,String lon) {
        MainActivity.server.getHoursWeaterByCoordinate(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Activity activity = WeatherActivity.this;
                if (activity == null)
                    return; // ToDo
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast error = Toast.makeText(WeatherActivity.this, "Connection error", Toast.LENGTH_LONG);
                        error.show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response)
                    throws IOException {
                if (response.code() != 200)
                    return;

                InputStream in = (InputStream) response.body().byteStream();
                try {

                    StringBuffer stringBuffer = new StringBuffer();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line + "\r\n");
                    }
                    in.close();

                    final ArrayList<WeatherData> wd = WeatherClass.getWeatherList(stringBuffer.toString(), 7);
                    final LinearLayout myToolbar = findViewById(R.id.weatherTime);

                    runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @Override
                        public void run() {
                            for (WeatherData item : wd) {
                                final LinearLayout myLayout = new LinearLayout(WeatherActivity.this);
                                //myLayout.setMinimumHeight(35);
                                myLayout.setOrientation(LinearLayout.VERTICAL);
                                final TextView textView = new TextView(WeatherActivity.this);
                                textView.setTextSize(15);
                                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                textView.setText(item.getDt_txt());
                                myLayout.addView(textView);
//TODO
                                ImageView myImg = new ImageView(WeatherActivity.this);
                                getIconImg(item.getWeather().getIcon(), myImg);
                                int width = 130;
                                int height = 130;
                                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
                                myImg.setLayoutParams(parms);
                                myLayout.addView(myImg);


                                final TextView textViewTemp = new TextView(WeatherActivity.this);
                                textViewTemp.setText(item.getMain().getTemp() + "ºC");
                                textViewTemp.setTextSize(20);
                                textViewTemp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                myLayout.addView(textViewTemp);
                                myToolbar.addView(myLayout);
                                //костыль, через марджинг ломается программа:(
                                final TextView textViewCont = new TextView(WeatherActivity.this);
                                textViewCont.setWidth(40);
                                myToolbar.addView(textViewCont);

                            }
                            myToolbar.refreshDrawableState();

                        }
                    });


                } catch (Exception e) {
                    System.out.println("Weather parsing error. please try again or contact app developer.");
                    e.printStackTrace();
                }
            }

        }, lat, lon);

    }

    private void getTodayHoursWeather(String city) {
        MainActivity.server.getHoursWeaterByCityName(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Activity activity = WeatherActivity.this;
                if (activity == null)
                    return; // ToDo
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast error = Toast.makeText(WeatherActivity.this, "Connection error", Toast.LENGTH_LONG);
                        error.show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response)
                    throws IOException {
                if (response.code() != 200)
                    return;

                InputStream in = (InputStream) response.body().byteStream();
                try {

                    StringBuffer stringBuffer = new StringBuffer();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line + "\r\n");
                    }
                    in.close();

                    final ArrayList<WeatherData> wd = WeatherClass.getWeatherList(stringBuffer.toString(), 7);
                    final LinearLayout myToolbar = findViewById(R.id.weatherTime);

                    runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @Override
                        public void run() {
                            for (WeatherData item : wd) {
                                final LinearLayout myLayout = new LinearLayout(WeatherActivity.this);
                                //myLayout.setMinimumHeight(35);
                                myLayout.setOrientation(LinearLayout.VERTICAL);
                                final TextView textView = new TextView(WeatherActivity.this);
                                textView.setTextSize(15);
                                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                textView.setText(item.getDt_txt());
                                myLayout.addView(textView);
//TODO
                                ImageView myImg = new ImageView(WeatherActivity.this);
                                getIconImg(item.getWeather().getIcon(), myImg);
                                int width = 130;
                                int height = 130;
                                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
                                myImg.setLayoutParams(parms);
                                myLayout.addView(myImg);


                                final TextView textViewTemp = new TextView(WeatherActivity.this);
                                textViewTemp.setText(item.getMain().getTemp() + "ºC");
                                textViewTemp.setTextSize(25);
                                textViewTemp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                myLayout.addView(textViewTemp);
                                myToolbar.addView(myLayout);
                                //костыль, через марджинг ломается программа:(
                                final TextView textViewCont = new TextView(WeatherActivity.this);
                                textViewCont.setWidth(40);
                                myToolbar.addView(textViewCont);

                            }
                            myToolbar.refreshDrawableState();

                        }
                    });


                } catch (Exception e) {
                    System.out.println("Weather parsing error. please try again or contact app developer.");
                    e.printStackTrace();
                }
            }

        }, city);
    }

    protected void getIconImg(String icon_code, final ImageView myImg) {

        MainActivity.server.getIcon(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Activity activity = WeatherActivity.this;
                if (activity == null)
                    return; // ToDo
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast error = Toast.makeText(WeatherActivity.this, "Connection error", Toast.LENGTH_LONG);
                        error.show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response)
                    throws IOException {
                if (response.code() != 200)
                    return;
//final HttpEntity entity = response.getEntity;
                InputStream in = (InputStream) response.body().byteStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(in);
                runOnUiThread(new Runnable() {
                    public void run() {
                        myImg.setImageBitmap(bitmap);
                    }
                });
            }

        }, icon_code);
    }

}
