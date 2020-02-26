package com.example.tripschedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.Utmk;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView=findViewById(R.id.map_view);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {


    }
}
