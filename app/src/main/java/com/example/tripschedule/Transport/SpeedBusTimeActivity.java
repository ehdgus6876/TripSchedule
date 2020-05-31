package com.example.tripschedule.Transport;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SpeedBusTimeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SpeedAdapter adapter;
    Handler handler;


    private ArrayList<SpeedBus> arrayList;

    private int code;
    private String uri;
    private String apiKey = "Uthu/s+j11jLrUlntMUggGbRbvmhzv5Of48rWLmtXT4";
    private int startStationID;
    private int endStationID;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_time);

        intent=getIntent();
        code=intent.getIntExtra("code",0);
        startStationID=code;
        endStationID=3400090;
        apiKey="8Th5C6lM/PWAAlWDvualgRNDftIgCTjjTRF6gvYDDqU";


        recyclerView=findViewById(R.id.rv_speedBus);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
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
                    if (api == API.EXPRESS_SERVICE_TIME) {
                        String[] scheduleArr;
                        JSONArray json2 = odsayData.getJson().getJSONObject("result").getJSONArray("station");
                        for(int k=0;k<json2.length();k++){

                            JSONObject json3 =json2.getJSONObject(k);

                            String fare;
                            String startTerminal=json3.getString("startTerminal");
                            String destTerminal=json3.getString("destTerminal");
                            String wasteTime=json3.getString("wasteTime");
                            String normalFare=json3.getString("normalFare");
                            String specialFare=json3.getString("specialFare");
                            String nightFare=json3.getString("nightFare");
                            String nightSpecialFare=json3.getString("nightSpecialFare");
                            String schedule=json3.getString("schedule");
                            String nightSchedule = json3.getString("nightSchedule");

                            scheduleArr=schedule.split("/|\n");
                            if(!scheduleArr[0].equals("")) {
                                for (int i = 0; i < scheduleArr.length; i++) {
                                    fare = null;
                                    if (scheduleArr[i].contains("(우등)")) {
                                        fare = specialFare;
                                        arrayList.add(new SpeedBus(startTerminal, destTerminal,
                                                wasteTime, fare, scheduleArr[i]));
                                    } else {
                                        fare = normalFare;
                                        arrayList.add(new SpeedBus(startTerminal, destTerminal,
                                                wasteTime, fare, scheduleArr[i]));
                                    }
                                }
                            }
                            scheduleArr=nightSchedule.split("/|\n");
                            if(!scheduleArr[0].equals("")) {
                                for (int j = 0; j < scheduleArr.length; j++) {
                                    fare = null;
                                    if (scheduleArr[j].contains("(우등)")) {
                                        fare = nightSpecialFare;
                                        arrayList.add(new SpeedBus(startTerminal, destTerminal,
                                                wasteTime, fare, scheduleArr[j]));
                                    } else {
                                        fare = nightFare;
                                        arrayList.add(new SpeedBus(startTerminal, destTerminal,
                                                wasteTime, fare, scheduleArr[j]));
                                    }
                                }
                            }
                        }
                        Collections.sort(arrayList, new Comparator<SpeedBus>() {
                            @Override
                            public int compare(SpeedBus o1, SpeedBus o2) {
                                if(o1.getSchedule().compareTo(o2.getSchedule())<0)
                                    return -1;
                                else if(o1.getSchedule().compareTo(o2.getSchedule())>0)
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
                if (api == API.EXPRESS_SERVICE_TIME) {}
            }
        };
        // API 호출
        odsayService.requestExpressServiceTime(String.valueOf(code),String.valueOf(endStationID), onResultCallbackListener);

        adapter=new SpeedAdapter(arrayList);


        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                recyclerView.setAdapter(adapter);


            }
        },1000);

    }
}
