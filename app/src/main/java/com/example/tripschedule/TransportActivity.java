package com.example.tripschedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class TransportActivity extends AppCompatActivity {

    ImageButton btn_bus,btn_air,btn_train;
    private String startdate;
    private String finishdate;
    public TextView tv_date;

    @SuppressLint({"SetTextI18n", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);

        tv_date=findViewById(R.id.tv_date);

        Intent intent=getIntent();
        startdate=intent.getStringExtra("startdate");
        finishdate=intent.getStringExtra("finishdate");
        tv_date.setText(startdate+"->"+finishdate);

        btn_bus= (ImageButton) findViewById(R.id.bus);
        btn_air= (ImageButton) findViewById(R.id.airplane);
        btn_train= (ImageButton) findViewById(R.id.train);



        btn_bus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                BusFragment busfragment=new BusFragment();
                transaction.replace(R.id.frame,busfragment);
                transaction.commit();
            }
        });

        btn_air.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                AirFragment airfragment=new AirFragment();
                transaction.replace(R.id.frame,airfragment);
                transaction.commit();
            }
        });

        btn_train.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                TrainFragment trainfragment=new TrainFragment();
                transaction.replace(R.id.frame,trainfragment);
                transaction.commit();
            }
        });


    }
}
