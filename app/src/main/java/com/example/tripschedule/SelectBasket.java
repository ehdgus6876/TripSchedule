package com.example.tripschedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SelectBasket extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SelectAdapter adapter;
    private Button btn_select, btn_map;
    int count = 0;
    Document doc = null;
    public static ArrayList<Weather> weathers = new ArrayList<>();

    private long date;
    private SimpleDateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_basket);
        btn_select = findViewById(R.id.btn_select);
        btn_map = findViewById(R.id.btn_map);
        weathers = new ArrayList<>();


        recyclerView = findViewById(R.id.rv_speedBus);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView = findViewById(R.id.rv_speedBus);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new SelectAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
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
        GetXMLTask task = new GetXMLTask(0);
        task.execute("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=3114057000");
        GetXMLTask task1 = new GetXMLTask(1);
        task1.execute("http://www.weather.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=159");


        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < FoodAdapter.selectItems.size(); i++) {
                    if (FoodAdapter.selectItems.get(i).getCode() == 4) {
                        count++;
                    }
                }
                if (count == 0) {
                    Toast.makeText(getApplicationContext(), "숙소를 최소 1개이상을 선택해주세요", Toast.LENGTH_SHORT).show();
                } else if (count >= date) {
                    Toast.makeText(getApplicationContext(), "숙소가 너무 많이 선택되어 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent1 = new Intent(getApplicationContext(), PlanActivity.class);
                    startActivity(intent1);
                }
            }
        });

    }

    private class GetXMLTask extends AsyncTask<String, Void, Document> {
        int code;

        GetXMLTask(int code) {
            this.code = code;
        }

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
                doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {
            if (code == 0) {
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                Date da = null;


                NodeList todayList = doc.getElementsByTagName("header");
                Node todayNode = todayList.item(0);
                Element todayElmnt = (Element) todayNode;

                NodeList todayList1 = todayElmnt.getElementsByTagName("tm");
                String today = todayList1.item(0).getChildNodes().item(0).getNodeValue();
                today = today.substring(0, 8);
                try {
                    da = dateFormat.parse(today);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(da);
                Calendar cal2 = Calendar.getInstance();
                cal.setTime(da);


                //data태그가 있는 노드를 찾아서 리스트 형태로 만들어서 반환
                NodeList nodeList = doc.getElementsByTagName("data");
                //data 태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환
                //data엘리먼트 노드


                cal.add(Calendar.DATE, 1);
                cal2.add(Calendar.DATE, 2);


                //Log.d("dong", dateFormat.format(cal2.getTime()));

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    Element fstElmnt = (Element) node;

                    NodeList date = fstElmnt.getElementsByTagName("day");
                    NodeList hour = fstElmnt.getElementsByTagName("hour");
                    NodeList weather = fstElmnt.getElementsByTagName("wfKor");
                    NodeList lowTemp = fstElmnt.getElementsByTagName("tmn");
                    NodeList highTemp = fstElmnt.getElementsByTagName("tmx");


                    if (Integer.parseInt(date.item(0).getChildNodes().item(0).getNodeValue()) == 1 &&
                            Integer.parseInt(hour.item(0).getChildNodes().item(0).getNodeValue()) == 12) {
                        weathers.add(new Weather(dateFormat.format(cal.getTime()),
                                weather.item(0).getChildNodes().item(0).getNodeValue(),
                                lowTemp.item(0).getChildNodes().item(0).getNodeValue(),
                                highTemp.item(0).getChildNodes().item(0).getNodeValue()
                        ));
                    } else if (Integer.valueOf(date.item(0).getChildNodes().item(0).getNodeValue()) == 2 &&
                            Integer.valueOf(hour.item(0).getChildNodes().item(0).getNodeValue()) == 12) {
                        weathers.add(new Weather(dateFormat.format(cal2.getTime()),
                                weather.item(0).getChildNodes().item(0).getNodeValue(),
                                lowTemp.item(0).getChildNodes().item(0).getNodeValue(),
                                highTemp.item(0).getChildNodes().item(0).getNodeValue()
                        ));
                    }

                }
                super.onPostExecute(doc);
            } else {
                String weatherdate = "";
                //data태그가 있는 노드를 찾아서 리스트 형태로 만들어서 반환
                NodeList nodeList = doc.getElementsByTagName("location");
                //data 태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환
                Log.d("dong1", String.valueOf(nodeList.getLength()));
                Node node = nodeList.item(1);
                Element fst = (Element) node;

                for (int i = 1; i < 10; i += 2) {

                    NodeList date = fst.getElementsByTagName("tmEf");
                    NodeList weather = fst.getElementsByTagName("wf");
                    NodeList lowTemp = fst.getElementsByTagName("tmn");
                    NodeList highTemp = fst.getElementsByTagName("tmx");
                    weatherdate = date.item(i).getChildNodes().item(0).getNodeValue().replace("-", "").substring(0, 8);


                    weathers.add(new Weather(weatherdate,
                            weather.item(i).getChildNodes().item(0).getNodeValue(),
                            lowTemp.item(i).getChildNodes().item(0).getNodeValue(),
                            highTemp.item(i).getChildNodes().item(0).getNodeValue()));


                }

            }

            super.onPostExecute(doc);
        }
    }//end inner class - GetXMLTask

}
