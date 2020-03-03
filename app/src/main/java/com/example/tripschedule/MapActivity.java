package com.example.tripschedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.Tm128;
import com.naver.maps.geometry.Utmk;
import com.naver.maps.geometry.WebMercatorCoord;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
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

        WebMercatorCoord webMercatorCoord = new WebMercatorCoord(14394868.003024507,4237437.772808846);
        LatLng cityhall = webMercatorCoord.toLatLng();
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(cityhall);
        naverMap.moveCamera(cameraUpdate);
        for(int i=0 ; i<FoodAdapter.selectItems.size();i++) {
            Marker marker = new Marker();
            //marker.setPosition(new LatLng(Float.valueOf(FoodAdapter.selectItems.get(0).getMapx()),Float.valueOf(FoodAdapter.selectItems.get(0).getMapy())));
            marker.setCaptionText(FoodAdapter.selectItems.get(i).getTitle());
            Tm128 tm128 = new Tm128(Float.valueOf(FoodAdapter.selectItems.get(i).getMapx()), Float.valueOf(FoodAdapter.selectItems.get(i).getMapy()));
            LatLng latLng = tm128.toLatLng();
            marker.setPosition(latLng);
            marker.setMap(naverMap);
        }
    }
}
