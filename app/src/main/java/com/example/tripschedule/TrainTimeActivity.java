package com.example.tripschedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;


public class TrainTimeActivity extends AppCompatActivity {

    private String requestUrl;
    private String openURL = "http://openapi.tago.go.kr/openapi/service/TrainInfoService/getStrtpntAlocFndTrainInfo?ServiceKey=MjdakRRYxSARCsdeSrbLvR4Wh8EkubBvrOWPZ%2BZQENSGaGth%2FjCavsx%2FSccbUtWk5X7bm%2BrVy25ihgDdEbJd7Q%3D%3D";
    private String ulsankey="NATH13717";
    public String code;
    ArrayList<TrainItem> list = null;
    public TrainItem train = null;
    public RecyclerView recyclerView;
    static public String totalCount="200";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_time);
        Intent intent;
        intent=getIntent();
        code=intent.getStringExtra("code");
        recyclerView = findViewById(R.id.recycler_train);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager((layoutManager));



        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    private class MyAsyncTask extends AsyncTask<String, Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            requestUrl = openURL+ "&numOfRows="+totalCount+"&depPlaceId="+code+"&arrPlaceId="+TrainFragment.Station+"&depPlandTime="+CalendarActivity.sendStartDate;
            try{
                boolean b_arrplandtime =false;
                boolean b_depplandtime = false;
                boolean b_traingradename = false;

                URL url = new URL(requestUrl);
                InputStream is = url.openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new InputStreamReader(is,"UTF-8"));
                String tag;
                int eventType = parser.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    switch(eventType){
                        case XmlPullParser.START_DOCUMENT:
                            list = new ArrayList<TrainItem>();
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("item")&& train !=null){
                                list.add(train);
                            }
                            break;
                        case XmlPullParser.START_TAG:
                            if(parser.getName().equals("item")){
                                train = new TrainItem();
                            }
                            if(parser.getName().equals("arrplandtime")) b_arrplandtime= true;
                            else if(parser.getName().equals("depplandtime")) b_depplandtime= true;
                            else if (parser.getName().equals("traingradename")) b_traingradename= true;
                            break;
                        case XmlPullParser.TEXT:
                            if(b_arrplandtime){
                                train.setArrplandtime(parser.getText());
                                b_arrplandtime = false;
                            }
                            else if(b_depplandtime){
                                train.setDepplandtime(parser.getText());
                                b_depplandtime=false;
                            }
                            else if(b_traingradename){
                                train.setTraingradename(parser.getText());
                                b_traingradename=false;
                            }
                            break;
                    }
                    eventType=parser.next();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);

            TrainTimeAdapter adapter = new TrainTimeAdapter(getApplicationContext(),list);
            recyclerView.setAdapter(adapter);
        }
    }


}
