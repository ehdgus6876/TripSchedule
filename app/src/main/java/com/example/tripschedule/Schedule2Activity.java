package com.example.tripschedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.Tm128;
import com.naver.maps.geometry.WebMercatorCoord;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class Schedule2Activity extends Fragment {

    public static long date;
    private  int arrivalTime;
    Button btn_scheduleselect;
    private SimpleDateFormat df;
    private int[] code_array={0,0};
    private int[] first_array={0,0}; //첫날
    private LatLng latLng;
    private  double tmp_sleep =1000000;
    private ArrayList<SelectItem> selectItems;
    private int[][] day_array;
    int p;
    RecyclerView rv;
    ScheduleAdapter adapter=new ScheduleAdapter();
    ItemTouchHelper helper;
    Document doc = null;
    public static ArrayList<SelectItem> al[];
    String[] date1;
    BufferedReader br;
    StringBuilder searchResult;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_schedule2,container,false);
        selectItems = new ArrayList<>();
        selectItems.addAll(FoodAdapter.selectItems) ;
        btn_scheduleselect=v.findViewById(R.id.btn_scheduleselect);
        rv = v.findViewById(R.id.rv); //RecyclerView의 레이아웃 방식을 지정
        LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        initAlgorithm();
        //RecyclerView의 Adapter 세팅
        rv.setAdapter(adapter);
        date1 = new String[(int) date];
        //ItemTouchHelper 생성
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter)); //RecyclerView에 ItemTouchHelper 붙이기
        helper.attachToRecyclerView(rv);
        for(int i =0 ; i<date;i++) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date da = null;
            Calendar cal = Calendar.getInstance();
            try {
                da = dateFormat.parse(CalendarActivity.sendStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cal.setTime(da);
            cal.add(Calendar.DATE, i);
            date1[i]=dateFormat.format(cal.getTime());
        }
        for ( int i =0;i<date1.length;i++){
            Log.d("날짜",date1[i]);
        }
        for(int i=0 ; i<SelectBasket.weathers.size();i++) {
            Log.d("날씨날짜" ,SelectBasket.weathers.get(i).getDate());
        }
        for(int i=0 ; i <date;i++){
            SelectItem selectItem = new SelectItem((i+1)+"일차",null,null,null,"",null,null,9999);
            for(int j =0;j<SelectBasket.weathers.size();j++){
                if(date1[i].equals(SelectBasket.weathers.get(j).getDate())){
                    selectItem.setLowTemp(SelectBasket.weathers.get(j).getLowTemp());
                    selectItem.setHighTemp(SelectBasket.weathers.get(j).getHighTemp());
                    selectItem.setWeather(SelectBasket.weathers.get(j).getWeather());
                }
            }
            adapter.addItem(selectItem);
            for(int j =0;j<al[i].size();j++){
                adapter.addItem(al[i].get(j));
                //Log.d("dong:weather1",weathers1.get(i).getDate());
            }
        }
        btn_scheduleselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k =0;
                for(int i =0;i<date;i++){
                    al[i].clear();
                    for(int j = k ; j<adapter.getArray().size();j++){
                        if(!adapter.getArray().get(j).getTitle().equals((i+2)+"일차")) {
                            al[i].add(adapter.getArray().get(j));
                        }
                        else {
                            k=j;
                            break;
                        }
                    }
                }
                for(int i =0;i<date;i++) {
                    for (int j = 0; j < al[i].size(); j++) {
                        Log.d("일정이름", al[i].get(j).getTitle());
                        Log.d("일정날씨", al[i].get(j).getDetail());
                    }
                }
            }
        });
        return v;
    }
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
        double j=100000;
        int k=0;
        Tm128 Tm_Cafe = new Tm128(Float.parseFloat(x),Float.valueOf(y));
        LatLng LL_Cafe = Tm_Cafe.toLatLng();
        for(int i =0 ; i<selectItems.size();i++){
                Tm128 tm128 = new Tm128(Float.valueOf(selectItems.get(i).getMapx()), Float.valueOf(selectItems.get(i).getMapy()));
                LatLng cafeLatLng = tm128.toLatLng();
                if(j>distance(LL_Cafe.latitude, LL_Cafe.longitude, cafeLatLng.latitude, cafeLatLng.longitude,"kilometer")){
                    j=distance(LL_Cafe.latitude, LL_Cafe.longitude, cafeLatLng.latitude, cafeLatLng.longitude,"kilometer");
                    Log.d("naver결과",selectItems.get(i).getTitle());
                    Log.d("naver결과", String.valueOf(searchNaver(LL_Cafe.latitude, LL_Cafe.longitude, cafeLatLng.latitude, cafeLatLng.longitude)));
                    k=i;
                }
        }
        day_array[day][code]-=1;
        al[day].add(selectItems.get(k));
        selectItems.remove(k);
    }
        public int searchNaver(final double lat1, final double lon1, final double lat2, final double lon2) { // 검색어 = searchObject로 ;
        int result1=0;
        final String clientId = "7e7cc797q1";
        final String clientSecret = "MrCQ9XX0nfNXHb1vw45otjiB7xGay2rxUC2q5jhu";
        String result = "";

        // 네트워크 연결은 Thread 생성 필요
        ExecutorService mPool = Executors.newFixedThreadPool(5);
        Future<Integer> mFuture = mPool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {

                String apiURL = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?" +
                        "start=" + lon1 + "," + lat1 + "&goal=" + lon2 + "," + lat2 + "&option=trafast"; // json 결과
                // Json 형태로 결과값을 받아옴.

                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
                con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
                con.connect();

                int responseCode = con.getResponseCode();


                if (responseCode == 200) { // 정상 호출
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
                JSONObject json = new JSONObject(String.valueOf(searchResult));
                JSONObject json1 = json.getJSONObject("route");
                JSONArray json2 = json1.getJSONArray("trafast");
                JSONObject json3 = json2.getJSONObject(0);
                JSONObject json4 = json3.getJSONObject("summary");
                Log.d("dong : distance", String.valueOf(json4.getInt("distance")));
                return json4.getInt("distance");
            }
        });
        try{
            result1=mFuture.get();
            Log.d("dong result1",String.valueOf(result1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result1;
    }
    private void initAlgorithm(){
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
                code_array[0] += 1;
            } else if (selectItems.get(i).getCode() == 2) {
                code_array[0] += 1;
            } else if (selectItems.get(i).getCode() == 3) {
                code_array[0] += 1;
            } else {
                code_array[1] += 1;
                Tm128 tm128 = new Tm128(Float.valueOf(selectItems.get(i).getMapx()), Float.valueOf(selectItems.get(i).getMapy()));
                LatLng sleep = tm128.toLatLng();
                if (tmp_sleep > distance(latLng.latitude, latLng.longitude, sleep.latitude, sleep.longitude, "kilometer")) {
                    tmp_sleep = distance(latLng.latitude, latLng.longitude, sleep.latitude, sleep.longitude, "kilometer");
                    al[0].add(selectItems.get(i));
                    al[0].remove(0);
                    Log.d("hhhh", String.valueOf(tmp_sleep));
                    Log.d("hhhh", String.valueOf(al[0].get(0).getTitle()));
                    p = i;
                    code_array[1]-=1;
                }
            }
        }
        selectItems.remove(p);
        long day = date;
        int amount= (int) (code_array[0]/date);
        day_array = new int[(int) date][2];
        if (date==1){
            day_array[0][0]=code_array[0];
            day_array[0][1]=0;
        }
        else {
            if (arrivalTime >= 6 && arrivalTime < 12) {  //도착시간이 6~12시
                for (int i = 0; i < date; i++) {
                    if (i == 0) {
                        day_array[i][0] = amount;
                        code_array[0] -= day_array[i][0];
                    } else if (i == date - 1) {
                        day_array[i][0] = code_array[0];
                    } else {
                        day_array[i][0] = amount;
                        code_array[0] -= day_array[i][0];
                    }
                    if (code_array[1] > 0 && i != 0) {
                        day_array[i][1] = 1;
                        code_array[1] -= 1;
                    }
                }
            } else if (arrivalTime >= 12 && arrivalTime < 15) {  //도착시간이 12~15시
                for (int i = 0; i < date; i++) {
                    if (i == 0) {
                        day_array[i][0] = amount - 1;
                        code_array[0] -= day_array[i][0];
                    } else if (i == date - 1) {
                        day_array[i][0] = code_array[0];
                    } else {
                        day_array[i][0] = amount;
                        code_array[0] -= day_array[i][0];
                    }
                    if (code_array[1] > 0 && i != 0) {
                        day_array[i][1] = 1;
                        code_array[1] -= 1;
                    }
                }
            } else if (arrivalTime >= 15 && arrivalTime < 18) {  //도착시간이 15~18시
                for (int i = 0; i < date; i++) {
                    if (i == 0) {
                        day_array[i][0] = amount - 2;
                        code_array[0] -= day_array[i][0];
                    } else if (i == date - 1) {
                        day_array[i][0] = code_array[0];
                    } else {
                        day_array[i][0] = amount;
                        code_array[0] -= day_array[i][0];
                    }
                    if (code_array[1] > 0 && i != 0) {
                        day_array[i][1] = 1;
                        code_array[1] -= 1;
                    }
                }
            } else if (arrivalTime >= 18 && arrivalTime < 21) {  //도착시간이 18~21시
                for (int i = 0; i < date; i++) {
                    if (i == 0) {
                        day_array[i][0] = 2;
                        code_array[0] -= day_array[i][0];
                    } else if (i == date - 1) {
                        day_array[i][0] = code_array[0];
                    } else {
                        day_array[i][0] = amount;
                        code_array[0] -= day_array[i][0];
                    }
                    if (code_array[1] > 0 && i != 0) {
                        day_array[i][1] = 1;
                        code_array[1] -= 1;
                    }
                }
            } else { //나머지 시간
                for (int i = 0; i < date; i++) {
                    if (i == 0) {
                        day_array[i][0] = 1;
                        code_array[0] -= day_array[i][0];
                    } else {
                        if (code_array[1] > 0 && i != 0) {
                            day_array[i][1] = 1;
                            code_array[1] -= 1;
                        }
                        day_array[i][0] = amount;
                        code_array[0] -= day_array[i][0];
                    }
                }
            }
        }

        float max = 0;
        for (int i = 0; i < day_array[0].length; i++) {
            if (max < day_array[0][i]) {
                //max의 값보다 array[i]이 크면 max = array[i]
                max = day_array[0][i];
            }
        }
        for (int i =0;i<date;i++){
            if(i!=0){
                al[i].add(al[i-1].get(al[i-1].size()-1));
            }
            for (int j=0;j<day_array[i][0];j++){
                select_location(al[i].get(al[i].size() - 1).getMapx(), al[i].get(al[i].size() - 1).getMapy(),0,i);
            }
            if( day_array[i][1]>0){
                select_location(al[i].get(al[i].size() - 1).getMapx(), al[i].get(al[i].size() - 1).getMapy(),1,i);
            }
            else if(i!=date-1 && day_array[i][1]==0){
                al[i].add(al[i].get(0));
            }
        }
    }
}
