package com.example.tripschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.Collections;
import java.util.Comparator;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        String uri="https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?start={126.9783881,37.5666102}&goal={129.3112381,35.5396493}&option={} \\\n" +
                "\t-H X-NCP-APIGW-API-KEY-ID: {7e7cc797q1} \\\n" +
                "\t-H X-NCP-APIGW-API-KEY: {MrCQ9XX0nfNXHb1vw45otjiB7xGay2rxUC2q5jhu} -v";


        ScheduleActivity.ScheduleTask scheduleTask=new ScheduleActivity.ScheduleTask(uri,null);
        scheduleTask.execute();

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

    public class ScheduleTask extends AsyncTask<Void,Void,String> {
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
                StringBuilder page= new StringBuilder();

                while((line=reader.readLine())!=null){
                    page.append(line);
                }




                Log.d("dong", page.toString());





                /* catch (JSONException e) {
                    e.printStackTrace();
                }*/
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
