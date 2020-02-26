package com.example.tripschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

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
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;

public class ScheduleActivity extends AppCompatActivity {
    Handler handler;
    BufferedReader br;
    StringBuilder searchResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);





        Log.d("dong", "바보1");
        searchNaver();
        /*ScheduleActivity.ScheduleTask scheduleTask=new ScheduleActivity.ScheduleTask(uri,null);
        scheduleTask.execute();*/


    }
    public void searchNaver() { // 검색어 = searchObject로 ;
        final String clientId="7e7cc797q1";
        final String clientSecret="MrCQ9XX0nfNXHb1vw45otjiB7xGay2rxUC2q5jhu";

        // 네트워크 연결은 Thread 생성 필요
        new Thread() {

            @Override
            public void run() {
                try {

                    String apiURL = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?" +
                            "start=127.1058342,37.359708&goal=129.075986,35.179470&option=trafast"; // json 결과
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

    }
    public static double Calc(double Lat1,
                              double Long1, double Lat2, double Long2)
    {
        double dDistance = 0;
        double dLat1InRad = Lat1 * (Math.PI / 180.0);
        double dLong1InRad = Long1 * (Math.PI / 180.0);
        double dLat2InRad = Lat2 * (Math.PI / 180.0);
        double dLong2InRad = Long2 * (Math.PI / 180.0);

        double dLongitude = dLong2InRad - dLong1InRad;
        double dLatitude = dLat2InRad - dLat1InRad;

        double a = Math.pow(Math.sin(dLatitude / 2.0), 2.0) +
                Math.cos(dLat1InRad) * Math.cos(dLat2InRad) *
                        Math.pow(Math.sin(dLongitude / 2.0), 2.0);

        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));
        double  kEarthRadiusKms = 6376.5;
        dDistance = kEarthRadiusKms * c;
        return dDistance;
    }

    /*public class ScheduleTask extends AsyncTask<Void,Void,String> {
        private String url;
        private ContentValues values;

        ScheduleTask(String url,ContentValues values){
            this.url=url;
            this.values=values;
        }
        @Override
        protected String doInBackground(Void... voids) {
            String result;
            ScheduleActivity.RequestHttpURLConnection requestHttpURLConnection=new ScheduleActivity.RequestHttpURLConnection();
            result=requestHttpURLConnection.request(url,values);


            return result;
        }
    }
    public class RequestHttpURLConnection{
        String request(String _url, ContentValues _params){
            HttpURLConnection urlConn=null;
            StringBuffer sbParams=new StringBuffer();


            try {
                URL url=new URL(_url);
                urlConn=(HttpURLConnection) url.openConnection();

                urlConn.setRequestMethod("GET");
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
                StringBuilder page= new StringBuilder();
                Log.d("dong", "바보4");
                while((line=reader.readLine())!=null){
                    page.append(line);
                }




                Log.d("dong", "바보3");





                 catch (JSONException e) {
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

    }*/
}
