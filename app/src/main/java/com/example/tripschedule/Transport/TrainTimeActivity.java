package com.example.tripschedule.Transport;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripschedule.Calendar.CalendarActivity;
import com.example.tripschedule.R;
import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
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
        apiKey = "Uthu/s+j11jLrUlntMUggGbRbvmhzv5Of48rWLmtXT4";
        // 싱글톤 생성, Key 값을 활용하여 객체 생성
        ODsayService odsayService = ODsayService.init(this, apiKey);
        // 서버 연결 제한 시간(단위(초), default : 5초)
        odsayService.setReadTimeout(5000);
        // 데이터 획득 제한 시간(단위(초), default : 5초)
        odsayService.setConnectionTimeout(5000);

        // 콜백 함수 구현
        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
            // 호출 성공 시 실행
            @Override
            public void onSuccess(ODsayData odsayData, API api) {
                try {
                    // API Value 는 API 호출 메소드 명을 따라갑니다.
                    if (api == API.TRANIN_SERVICE_TIME) {
                        JSONObject json1 = odsayData.getJson().getJSONObject("result");
                        JSONArray json2 = odsayData.getJson().getJSONObject("result").getJSONArray("station");
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

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // 호출 실패 시 실행
            @Override
            public void onError(int i, String s, API api) {
                if (api == API.TRANIN_SERVICE_TIME) {}
            }
        };
        // API 호출
        odsayService.requestTrainServiceTime(startStationID,endStationID, onResultCallbackListener);


        adapter = new TrainTimeAdapter(arrayList, R.layout.cardview_train);

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


}


















