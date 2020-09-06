package com.tauqeer.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.*;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

public class LahoreActivity extends AppCompatActivity {

    static TextView mainTemp, dateView,weatherType,feelsLike;
    static JSONObject object = null;
    static ProgressBar progressBar;
    static ImageView imageView;
    static ListView nextWeekForecast;
    static Context context;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lahore);
        context = this;
        mainTemp = (TextView) findViewById(R.id.viewText_lhr);
        dateView = (TextView) findViewById(R.id.date_lhr);
        weatherType = (TextView) findViewById(R.id.weather_type_lhr);
        feelsLike = (TextView) findViewById(R.id.feels_like_lhr);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_lhr);
        progressBar.setIndeterminate(true);
        imageView = (ImageView) findViewById(R.id.weather_icon_lhr);
        toolbar = (Toolbar) findViewById(R.id.toolbar_lhr);
        toolbar.setTitle("City Weather Info");
        setSupportActionBar(toolbar);
        nextWeekForecast = (ListView) findViewById(R.id.next_week_forecast_lhr);
        setTitle("City Weather Info");
        getData(31.533253, 74.348372);
    }

    public static String getData (final double lat,final double lon) {
        new AsyncTask<Void,Void,Void> () {

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lon+"&exclude=hourly&appid=1aca8b01724808e81031434cd1341c65&units=metric");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuffer json = new StringBuffer(1024);
                    String tmp = "";
                    while ((tmp = reader.readLine()) != null) {
                        json.append(tmp).append("\n");
                    }
                    reader.close();
                    object = new JSONObject(json.toString());
                    if (object.getInt("cod") != 200) {
                        System.out.println("Cancelled!");
                        return null;
                    }
                    System.out.println("Do In Background!");
                }catch (Exception e) {
                    System.out.println("EXCEPTION: "+e.getLocalizedMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                System.out.println("Method Run!");
                if (object != null) {
                    progressBar.setVisibility(View.GONE);
                    setDataToLayout();
                }
                else {
                    System.out.println("Object is null!");
                }
                super.onPostExecute(aVoid);
            }
        }.execute();
        return null;
    }

    public static void setWeatherIcon() {
        try {
            String iconCode = object.getJSONObject("current").getJSONArray("weather").getJSONObject(0).getString("icon");
            new DownloadImageFromWeb(imageView).execute("http://openweathermap.org/img/wn/"+iconCode+"@2x.png");
        }catch (Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
        }
    }

    public static void setDataToLayout() {
        try {
            dateView.setText(setGregorianDate(object.getJSONObject("current").getInt("dt")));
            setWeatherIcon();
            mainTemp.setText(Math.round(Double.parseDouble(object.getJSONObject("current").getString("temp")))+"");
            weatherType.setText(object.getJSONObject("current").getJSONArray("weather").getJSONObject(0).getString("description")+",");
            feelsLike.setText("feels like "+Math.round(Double.parseDouble(object.getJSONObject("current").getString("feels_like")))+"\u00B0"+"C");
            setNextWeekForecast();
        }catch (JSONException e) {
            System.out.println("EXCEPTION: "+e.getMessage());
        }
    }

    public static String setGregorianDate (long epochDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, MMM dd yyyy, HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Karachi"));
        return simpleDateFormat.format(new Date(epochDate * 1000L));
    }

    //This method is for the ListView Date. Format is Tue, Jan 10.
    public static String setSimpleDate (long epochDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, MMM dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Karachi"));
        return simpleDateFormat.format(new Date(epochDate * 1000L));
    }

    public static boolean setNextWeekForecast () {
        try{
            List <ViewModel> viewModels = new ArrayList<ViewModel>();
            List dates = new LinkedList();
            List descriptions = new LinkedList();
            List iconCodes = new LinkedList();
            JSONArray array = object.getJSONArray("daily");
            for (int i = 0;i < array.length();i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                dates.add(jsonObject.getInt("dt"));
                descriptions.add(jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"));
                iconCodes.add(jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon"));
            }
            for(int i = 1;i < array.length() - 1;i++) {
                ViewModel viewModel = new ViewModel(setSimpleDate(Long.parseLong(dates.get(i).toString())),descriptions.get(i).toString(),"http://openweathermap.org/img/wn/"+iconCodes.get(i).toString()+"@2x.png");
                viewModels.add(viewModel);
            }
            ViewAdapter adapter = new ViewAdapter(context,viewModels);
            nextWeekForecast.setAdapter(adapter);
            return true;
        }catch (Exception e) {
            System.out.println("setNextWeekForecast() Error: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int source = item.getItemId();
        if (source == R.id.refresh_icon) {
            recreate();
        }
        return true;
    }
}