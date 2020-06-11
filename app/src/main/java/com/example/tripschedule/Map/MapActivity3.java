package com.example.tripschedule.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.tripschedule.R;
import com.example.tripschedule.Schedule.Schedule2Activity;
import com.example.tripschedule.Schedule.ScheduleActivity;
import com.example.tripschedule.SelectLocation.LocationAdapter;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.Tm128;
import com.naver.maps.geometry.WebMercatorCoord;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.MultipartPathOverlay;
import com.naver.maps.map.util.MarkerIcons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapActivity3 extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    LinearLayout linearLayout;
    Marker marker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView=findViewById(R.id.map_view);
        mapView.getMapAsync(this);


    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        List<List<LatLng>> coordParts=new ArrayList<>();
        WebMercatorCoord webMercatorCoord = new WebMercatorCoord(14394868.003024507,4237437.772808846);
        LatLng cityhall = webMercatorCoord.toLatLng();
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(cityhall);
        naverMap.moveCamera(cameraUpdate);
        MultipartPathOverlay multipartPathOverlay=new MultipartPathOverlay();

        for(int i = 0; i< Schedule2Activity.al1.length; i++){
            List<LatLng> latLngs=new ArrayList<>();
            for(int j=1;j<Schedule2Activity.al1[i].size();j++){
                marker=new Marker();


                if(i==0){
                    marker.setIcon(MarkerIcons.BLACK);
                    marker.setIconTintColor(Color.GREEN);
                }
                else if(i==1){
                    marker.setIcon(MarkerIcons.BLACK);
                    marker.setIconTintColor(Color.BLUE);
                }
                else if(i==2){
                    marker.setIcon(MarkerIcons.BLACK);
                    marker.setIconTintColor(Color.YELLOW);
                }
                else if(i==3){
                    marker.setIcon(MarkerIcons.BLACK);
                    marker.setIconTintColor(Color.GRAY);
                }
                else if(i==4){
                    marker.setIcon(MarkerIcons.BLACK);
                    marker.setIconTintColor(Color.CYAN);
                }
                else if(i==5){
                    marker.setIcon(MarkerIcons.BLACK);
                    marker.setIconTintColor(Color.MAGENTA);
                }
                else if(i==6){
                    marker.setIcon(MarkerIcons.BLACK);
                    marker.setIconTintColor(Color.RED);
                }
                marker.setCaptionText(Schedule2Activity.al1[i].get(j).getTitle());
                marker.setCaptionColor(Color.BLUE);
                marker.setSubCaptionText(i+1+"일차");
                marker.setSubCaptionColor(Color.BLACK);
                marker.setSubCaptionTextSize(10);
                Tm128 tm128 = new Tm128(Float.valueOf(Schedule2Activity.al1[i].get(j).getMapx()), Float.valueOf(Schedule2Activity.al1[i].get(j).getMapy()));
                LatLng latLng = tm128.toLatLng();
                latLngs.add(latLng);
                marker.setPosition(latLng);
                marker.setMap(naverMap);
                latLngs.add(latLng);

            }
            coordParts.add(latLngs);

        }
        //multipartPathOverlay.setCoordParts(coordParts);
        //multipartPathOverlay.setMap(naverMap);

    }
}
