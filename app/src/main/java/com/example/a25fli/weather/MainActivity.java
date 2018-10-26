package com.example.a25fli.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    public static ServerRequest server = new ServerRequest("openweathermap.org", "openweathermap.org");
    public static boolean isDeveloper = false;//если надо посмотреть что с локэйшн - ставим true

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView weekDay = (TextView) findViewById(R.id.weekDay);
        TextView todayIs = (TextView) findViewById(R.id.todayIs);
        Button myButton = (Button) findViewById(R.id.my_button);


        //Locale locale = new Locale("ru"); если переводить на 2 языка
        String weekDayText = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date());
        String todayText = new SimpleDateFormat("dd MMMM YYYY", Locale.ENGLISH).format(new Date());

        weekDay.setText(weekDay.getText() + " " + weekDayText);
        todayIs.setText(todayIs.getText() + " " + todayText);


        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });
    }
    protected void onClickCityName(View view){

        EditText cityName = (EditText) findViewById(R.id.cityName);
        Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
        intent.putExtra("city", cityName.getText().toString());
        startActivity(intent);
    }
}
