package com.example.edu.restapiactivity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Scene;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class RestAPIActivity extends AppCompatActivity implements View.OnClickListener {
    String weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);
        Button london = findViewById(R.id.london);
        london.setOnClickListener(this);
        Button seoul = findViewById(R.id.seoul);
        seoul.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView textView = findViewById(R.id.textView);
        OpenWeatherAPITask task = new OpenWeatherAPITask();
        switch (v.getId()) {
            case R.id.london:

                try {

                    String weather = task.execute("London").get();
                    textView.setText(weather);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.seoul:
                try {
                    String weather = task.execute("Seoul").get();
                    textView.setText(weather);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    class OpenWeatherAPITask extends AsyncTask<String, Void, String> {
        @Override
        public String doInBackground(String... params) {
            String weather = null;
            String id = params[0];
            String urlString = "http://api.openweathermap.org/data/2.5/weather" + "?q=" + id + "&appid=1bf8816688250462bd1b04073f192cfe";
            try {

                URL url = null;
                url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();

                byte[] buffer = new byte[1000];
                in.read(buffer);
                weather = new String(buffer);

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return weather;
        }
    }
}