package com.example.tripschedule;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private String apiKey;
    private int startStationID;
    private int endStationID;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_time);

        intent=getIntent();
        code=intent.getIntExtra("code",0);
        Log.d("dong",String.valueOf(code));
        startStationID=code;
        endStationID=3400090;
        apiKey="8Th5C6lM/PWAAlWDvualgRNDftIgCTjjTRF6gvYDDqU";
        uri="https://api.odsay.com/v1/api/expressServiceTime?apiKey="+apiKey+"&lang=0&startStationID="+startStationID+"&endStationID="+endStationID;

        recyclerView=findViewById(R.id.rv_speedBus);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        arrayList=new ArrayList<>();
        SpeedBusTask speedBusTask=new SpeedBusTask(String.valueOf(uri),null);
        speedBusTask.execute();

        adapter=new SpeedAdapter(arrayList);


        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                recyclerView.setAdapter(adapter);


            }
        },1000);





    }
    public class SpeedBusTask extends AsyncTask<Void,Void,String>{

        private String url;
        private ContentValues values;

        public SpeedBusTask(String url,ContentValues values){
            this.url=url;
            this.values=values;

        }
        @Override
        protected String doInBackground(Void... voids) {
            String result; //요청 결과 저장 변수
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url,values);




            return result;
        }
    }
    public class RequestHttpURLConnection{
        public String request(String _url,ContentValues _params){
            HttpURLConnection urlConn=null;
            StringBuffer sbParams=new StringBuffer();


            try {
                URL url=new URL(_url);
                urlConn=(HttpURLConnection) url.openConnection();

                urlConn.setRequestMethod("POST");
                urlConn.setRequestProperty("Accept-Charset","UTF-8");
                urlConn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;charset=UTF-8");

                String strParams = sbParams.toString();
                OutputStream os=urlConn.getOutputStream();
                os.write(strParams.getBytes("UTF-8"));
                os.flush();
                os.close();


                if(urlConn.getResponseCode()!= HttpURLConnection.HTTP_OK)
                    return null;


                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"UTF-8"));

                String line;
                String page="";

                while((line=reader.readLine())!=null){
                    page+=line;
                }
                try{
                    String[] scheduleArr;
                    JSONObject json=new JSONObject(page);
                    JSONObject json1=json.getJSONObject("result");
                    Log.d("dong", String.valueOf(json1));

                    JSONArray json2=json1.getJSONArray("station");

                    Log.d("dong", String.valueOf(json2));



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
                        Log.d("dong", schedule);

                        scheduleArr=schedule.split("/|\n");
                        if(!scheduleArr[0].equals("")) {
                            for (int i = 0; i < scheduleArr.length; i++) {
                                fare = null;
                                if (scheduleArr[i].contains("(우등)")) {
                                    fare = specialFare;
                                    arrayList.add(new SpeedBus(startTerminal, destTerminal,
                                            wasteTime, fare, scheduleArr[i]));
                                    Log.d("dong", startTerminal + destTerminal + wasteTime + fare + scheduleArr[i]);
                                } else {
                                    fare = normalFare;
                                    arrayList.add(new SpeedBus(startTerminal, destTerminal,
                                            wasteTime, fare, scheduleArr[i]));
                                    Log.d("dong", startTerminal + destTerminal + wasteTime + fare + scheduleArr[i]);
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
                                    Log.d("dong", startTerminal + destTerminal + wasteTime + fare + scheduleArr[j]);
                                } else {
                                    fare = nightFare;
                                    arrayList.add(new SpeedBus(startTerminal, destTerminal,
                                            wasteTime, fare, scheduleArr[j]));
                                    Log.d("dong", startTerminal + destTerminal + wasteTime + fare + scheduleArr[j]);
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
