package com.example.tripschedule.Schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.tripschedule.R;
import com.example.tripschedule.Schedule.Schedule2Activity;
import com.example.tripschedule.Schedule.ScheduleActivity;

public class PlanActivity extends AppCompatActivity {

    ImageButton btn_plan1,btn_plan2;

    @SuppressLint({"SetTextI18n", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        btn_plan1=findViewById(R.id.btn_plan1);
        btn_plan2=findViewById(R.id.btn_plan2);
        btn_plan1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                ScheduleActivity ScheduleActivity=new ScheduleActivity();
                transaction.replace(R.id.frame,ScheduleActivity);
                transaction.commit();
            }
        });


        btn_plan2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Schedule2Activity Schedule2Activity=new Schedule2Activity();
                transaction.replace(R.id.frame,Schedule2Activity);
                transaction.commit();
            }
        });


    }

}
