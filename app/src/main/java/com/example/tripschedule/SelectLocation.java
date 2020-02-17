package com.example.tripschedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class SelectLocation extends AppCompatActivity {
    private Button btn_food;
    private Button btn_tour;
    private FloatingActionButton fab_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        btn_food=findViewById(R.id.btn_food);
        btn_tour=findViewById(R.id.btn_tour);
        fab_main=findViewById(R.id.fab_main);


        btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                FoodFragment foodFragment=new FoodFragment();
                transaction.replace(R.id.frame,foodFragment);
                transaction.commit();
            }
        });

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),select_basket.class);
                startActivity(intent);
            }
        });



    }

}

