package com.example.tripschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.Tm128;
import com.naver.maps.geometry.WebMercatorCoord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {
    Handler handler;
    BufferedReader br;
    StringBuilder searchResult;
    private long date;
    private  int arrivalTime;
    private SimpleDateFormat df;
    private float[] code_array={0,0,0,0,0};
    private float[] first_array={0,0,0,0,0};
    private LatLng latLng;
    private  double tmp_sleep =1000;
    private ArrayList<SelectItem> selectItems;
    private int[][] day_array;
    int p;

    private ArrayList<SelectItem> al[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        selectItems = new ArrayList<>();
        selectItems = FoodAdapter.selectItems;


        try {
            df = new SimpleDateFormat("yyyymmdd");
            Date scal = df.parse(CalendarActivity.sendStartDate);
            Date dcal = df.parse(CalendarActivity.sendFinishDate);

            long calDate = dcal.getTime() - scal.getTime();
            date = calDate / (24 * 60 * 60 * 1000);
            date = Math.abs(date) + 1;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        al = new ArrayList[(int) date];

        for (int i = 0; i < date; i++) {
            al[i] = new ArrayList<>();

        }
        if (CalendarActivity.transport == 1) {
            WebMercatorCoord webMercatorCoord = new WebMercatorCoord(14388405.8808019, 4239075.7671820);
            latLng = webMercatorCoord.toLatLng();
        } else if (CalendarActivity.transport == 2) {
            WebMercatorCoord webMercatorCoord = new WebMercatorCoord(14397825.5502095, 4237186.6379713);
            latLng = webMercatorCoord.toLatLng();
        } else if (CalendarActivity.transport == 3) {
            WebMercatorCoord webMercatorCoord = new WebMercatorCoord(14397945.8788126, 4236981.6613309);
            latLng = webMercatorCoord.toLatLng();
        } else if (CalendarActivity.transport == 4) {
            WebMercatorCoord webMercatorCoord = new WebMercatorCoord(14374644.8270535, 4239444.8147092);
            latLng = webMercatorCoord.toLatLng();
        } else {
            WebMercatorCoord webMercatorCoord = new WebMercatorCoord(14398631.7219922, 4237388.1809169);
            latLng = webMercatorCoord.toLatLng();
        }

        arrivalTime = Integer.valueOf(CalendarActivity.arrivaltime.substring(0, 2));
        al[0].add(selectItems.get(0));
        for (int i = 0; i < selectItems.size(); i++) {
            if (selectItems.get(i).getCode() == 0) {
                code_array[0] += 1;
            } else if (selectItems.get(i).getCode() == 1) {
                code_array[1] += 1;
            } else if (selectItems.get(i).getCode() == 2) {
                code_array[2] += 1;
            } else if (selectItems.get(i).getCode() == 3) {
                code_array[3] += 1;
            } else {
                code_array[4] += 1;
                Tm128 tm128 = new Tm128(Float.valueOf(selectItems.get(i).getMapx()), Float.valueOf(selectItems.get(i).getMapy()));
                LatLng sleep = tm128.toLatLng();
                if (tmp_sleep > distance(latLng.latitude, latLng.longitude, sleep.latitude, sleep.longitude, "kilometer")) {
                    tmp_sleep = distance(latLng.latitude, latLng.longitude, sleep.latitude, sleep.longitude, "kilometer");
                    al[0].add(selectItems.get(i));
                    al[0].remove(0);
                    Log.d("hhhh", String.valueOf(tmp_sleep));
                    Log.d("hhhh", String.valueOf(al[0].get(0).getTitle()));
                    p = i;


                }

            }
        }
        selectItems.remove(p);
        long day = date;
        day_array = new int[(int) date][5];
        for (int j = 0; j < date; j++) {
            String a = "";
            for (int i = 0; i < code_array.length; i++) {
                first_array[i] = Math.round(Float.valueOf(code_array[i] / day));
                day_array[j][i] = (int) (first_array[i]);
                code_array[i] -= first_array[i];

            }
            day -= 1;
        }

        float max = 0;
        for (int i = 0; i < day_array[0].length; i++) {

            if (max < day_array[0][i]) {
                //max의 값보다 array[i]이 크면 max = array[i]
                max = day_array[0][i];
            }
        }
        int Q=day_array[0][2];
        if (arrivalTime > 7 && arrivalTime < 12) {
            for (int i = 0; i < max; i++) {
                if (day_array[0][1] > 0) {
                    select_location(al[0].get(al[0].size() - 1).getMapx(), al[0].get(al[0].size() - 1).getMapy(), 1, 0);
                }
                if (day_array[0][0] > 0) {
                    select_location(al[0].get(al[0].size() - 1).getMapx(), al[0].get(al[0].size() - 1).getMapy(), 0, 0);
                }
                if (day_array[0][3] > 0) {
                    select_location(al[0].get(al[0].size() - 1).getMapx(), al[0].get(al[0].size() - 1).getMapy(), 3, 0);
                }
            }for(int i =0 ; i<Q;i++ )
             {
                select_location(al[0].get(al[0].size() - 1).getMapx(), al[0].get(al[0].size() - 1).getMapy(), 2, 0);
            }
                al[0].add(al[0].get(0));
            for (int i = 0; i < al[0].size(); i++) {
                Log.d("1일차", al[0].get(i).getTitle());
            }
        }
        int m=1;
        //시간 뒤에 더해야됨
        while (m<date) {
            int L=day_array[m][2];
            al[m].add(al[m-1].get(al[m-1].size()-1));

            max = 0;
            for (int i = 0; i < day_array[m].length; i++) {

                if (max < day_array[m][i]) {
                    max = day_array[m][i];
                }
            }

            for (int i = 0; i < max; i++) {
                if (day_array[m][1] > 0) {
                    select_location(al[m].get(al[m].size() - 1).getMapx(), al[m].get(al[m].size() - 1).getMapy(), 1, m);   //음식점
                }
                if (day_array[m][0] > 0) {
                    select_location(al[m].get(al[m].size() - 1).getMapx(), al[m].get(al[m].size() - 1).getMapy(), 0, m);   //카페
                }
                if (day_array[m][3] > 0) {
                    select_location(al[m].get(al[m].size() - 1).getMapx(), al[m].get(al[m].size() - 1).getMapy(), 3, m);   //관광지
                }
            }
            for(int i =0 ; i<L;i++ ) {

                select_location(al[m].get(al[m].size() - 1).getMapx(), al[m].get(al[m].size() - 1).getMapy(), 2, m);   //술집
            }
            if ( day_array[m][4]>0){
                select_location(al[m].get(0).getMapx(), al[m].get(0).getMapy(), 4, m);   //숙소
            }
            for (int i = 0; i < al[m].size(); i++) {
                Log.d((m+1)+"일차", al[m].get(i).getTitle());
            }
            m++;

        }
    }



    /*public void searchNaver() { // 검색어 = searchObject로 ;
        final String clientId="7e7cc797q1";
        final String clientSecret="MrCQ9XX0nfNXHb1vw45otjiB7xGay2rxUC2q5jhu";

        // 네트워크 연결은 Thread 생성 필요
        new Thread() {

            @Override
            public void run() {
                try {

                    String apiURL = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?" +
                            "start=35.543968,129.256231&goal=35.540414,129.336337&option=trafast"; // json 결과
                    // Json 형태로 결과값을 받아옴.
                    URL url = new URL(apiURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
                    con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
                    con.connect();

                    int responseCode = con.getResponseCode();


                    if(responseCode==200) { // 정상 호출
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    } else {  // 에러 발생
                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }

                    searchResult = new StringBuilder();
                    String inputLine;
                    while ((inputLine = br.readLine()) != null) {
                        searchResult.append(inputLine + "\n");

                    }

                    br.close();
                    con.disconnect();
                    Log.d("dong",searchResult.toString());
                } catch (Exception e) {
                    Log.d("dong", "error : " + e);
                }

            }
        }.start();

    }*/
    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }


    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
    private void select_location(String x,String y,int code,int day){
        double j=1000;
        int k=0;
        Tm128 Tm_Cafe = new Tm128(Float.valueOf(x),Float.valueOf(y));
        LatLng LL_Cafe = Tm_Cafe.toLatLng();
        for(int i =0 ; i<selectItems.size();i++){
            if(selectItems.get(i).getCode()==code) {
                Tm128 tm128 = new Tm128(Float.valueOf(selectItems.get(i).getMapx()), Float.valueOf(selectItems.get(i).getMapy()));
                LatLng cafeLatLng = tm128.toLatLng();
                if(j>distance(LL_Cafe.latitude, LL_Cafe.longitude, cafeLatLng.latitude, cafeLatLng.longitude,"kilometer")){
                    j=distance(LL_Cafe.latitude, LL_Cafe.longitude, cafeLatLng.latitude, cafeLatLng.longitude,"kilometer");
                    k=i;
                }
            }
        }
        day_array[day][code]-=1;
        al[day].add(selectItems.get(k));
        selectItems.remove(k);
    }

}
