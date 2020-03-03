package com.example.tripschedule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class TrainTimeActivity extends AppCompatActivity {

    public int code;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TrainTimeAdapter adapter;
    Handler handler;
    private ArrayList<TrainItem> arrayList;
    private String uri;
    private String apiKey;
    private String startStationID;
    private String endStationID;
    private Intent intent;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_time);
        arrayList = new ArrayList<>();
        intent = getIntent();
        code = intent.getIntExtra("code", 0);
        recyclerView = findViewById(R.id.recycler_train);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        startStationID = String.valueOf(code);
        endStationID = TrainFragment.Station;
        apiKey = "8Th5C6lM/PWAAlWDvualgRNDftIgCTjjTRF6gvYDDqU";
        uri = "https://api.odsay.com/v1/api/trainServiceTime?apiKey=" + apiKey + "&lang=0&startStationID=" + startStationID + "&endStationID=" + endStationID;

        TrainTask TrainTask = new TrainTask(uri, null);
        TrainTask.execute();

        adapter = new TrainTimeAdapter(arrayList, R.layout.trainitem);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
            }
        }, 1000);


    }

    public class TrainTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public TrainTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;

        }

        @Override
        protected String doInBackground(Void... voids) {
            String result; //요청 결과 저장 변수
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);


            return result;
        }
    }

    public class RequestHttpURLConnection {
        public String request(String _url, ContentValues _params) {
            HttpURLConnection urlConn = null;
            StringBuffer sbParams = new StringBuffer();


            try {
                URL url = new URL(_url);
                urlConn = (HttpURLConnection) url.openConnection();

                urlConn.setRequestMethod("POST");
                urlConn.setRequestProperty("Accept-Charset", "UTF-8");
                urlConn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;charset=UTF-8");

                String strParams = sbParams.toString();
                OutputStream os = urlConn.getOutputStream();
                os.write(strParams.getBytes("UTF-8"));
                os.flush();
                os.close();


                if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)
                    return null;


                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

                String line;
                String page = "";

                while ((line = reader.readLine()) != null) {
                    page += line;
                }
                try {
                    JSONObject json = new JSONObject(page);
                    JSONObject json1 = json.getJSONObject("result");
                    JSONArray json2 = json1.getJSONArray("station");
                    String startStation,endStation,trainClass,departureTime,arrivalTime,wasteTime,fare;


                    for (int k = 0; k < json2.length(); k++) {

                        JSONObject json3 = json2.getJSONObject(k);
                        String runDay = json3.getString("runDay");
                        JSONObject json4 = json3.getJSONObject("generalFare");




                        if (CalendarActivity.dateNum == 2 && CalendarActivity.dateNum == 3 && CalendarActivity.dateNum == 4 && CalendarActivity.dateNum == 5) {
                            if (runDay.equals("매일") || runDay.equals("월") ||runDay.equals("월화수목") ||runDay.equals("화수목금토일") || runDay.equals("월화수")) {
                                startStation = json1.getString("startStationName");
                                endStation = json1.getString("endStationName");
                                trainClass = json3.getString("trainClass");
                                departureTime = json3.getString("departureTime");
                                arrivalTime = json3.getString("arrivalTime");
                                wasteTime = json3.getString("wasteTime");
                                fare=json4.getString("weekday");
                                arrayList.add(new TrainItem(startStation,endStation,trainClass,departureTime,arrivalTime,wasteTime,fare));

                            }
                        } else {
                            if (runDay.equals("매일") || runDay.equals("금토일") || runDay.equals("금") || runDay.equals("금토") || runDay.equals("금일") || runDay.equals("토")) {
                                startStation = json1.getString("startStationName");
                                endStation = json1.getString("endStationName");
                                trainClass = json3.getString("trainClass");
                                departureTime = json3.getString("departureTime");
                                arrivalTime = json3.getString("arrivalTime");
                                wasteTime = json3.getString("wasteTime");
                                fare=json4.getString("weekend");
                                arrayList.add(new TrainItem(startStation,endStation,trainClass,departureTime,arrivalTime,wasteTime,fare));


                            }

                        }


                    }
                    Collections.sort(arrayList, new Comparator<TrainItem>() {
                        @Override
                        public int compare(TrainItem o1, TrainItem o2) {
                            if (o1.getDepartureTime().compareTo(o2.getDepartureTime()) < 0)
                                return -1;
                            else if (o1.getDepartureTime().compareTo(o2.getDepartureTime()) > 0)
                                return 1;
                            return 0;
                        }
                    });

                } catch(JSONException e){
                    e.printStackTrace();
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }
    }
}


















