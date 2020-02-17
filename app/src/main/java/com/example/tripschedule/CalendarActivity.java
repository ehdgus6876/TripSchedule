package com.example.tripschedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {
    private CalendarView calendar;
    private Button btn_start;
    private Button btn_finish;
    private Button btn_re;
    private Button btn_complete;
    private String startdate;
    private String finishdate;
    static public String sendStartDate;
    static public String sendFinishDate;
    static public int dateNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendar=findViewById(R.id.calendarView);
        btn_start=findViewById(R.id.btn_start);
        btn_finish=findViewById(R.id.btn_finish);
        btn_re=findViewById(R.id.btn_re);
        btn_complete=findViewById(R.id.btn_complete);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        if((month+1)<10&&dayOfMonth>10){
                            sendStartDate= String.valueOf(year)+ 0 + (month + 1) + dayOfMonth;
                        }
                        else if((month+1)>10&&dayOfMonth<10){
                            sendStartDate= String.valueOf(year)+(month + 1) + 0+dayOfMonth;
                        }
                        else if((month+1)<10&&dayOfMonth<10){
                            sendStartDate=String.valueOf(year)+0+(month+1)+0+dayOfMonth;
                        }
                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                        try {
                            Date date = dateFormat.parse(sendStartDate);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            dateNum= cal.get(Calendar.DAY_OF_WEEK);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        startdate=year+"/"+(month+1)+"/"+dayOfMonth;
                        btn_start.setText(startdate);

                    }
                });
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        if((month+1)<10&&dayOfMonth>10){
                            sendFinishDate= String.valueOf(year)+ 0 + (month + 1) + dayOfMonth;
                        }
                        else if((month+1)>10&&dayOfMonth<10){
                            sendFinishDate= String.valueOf(year)+(month + 1) + 0+dayOfMonth;
                        }
                        else if((month+1)<10&&dayOfMonth<10){
                            sendFinishDate=String.valueOf(year)+0+(month+1)+0+dayOfMonth;
                        }

                        finishdate=year+"/"+(month+1)+"/"+dayOfMonth;
                        btn_finish.setText(finishdate);

                    }
                });
            }
        });

        btn_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_start.setText("출발날짜");
                btn_finish.setText("도착날짜");
            }
        });

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startdate=btn_start.getText().toString();
                finishdate=btn_finish.getText().toString();
                Intent intent=new Intent(getApplicationContext(),TransportActivity.class);
                intent.putExtra("startdate",startdate);
                intent.putExtra("finishdate",finishdate);
                startActivity(intent);
            }
        });
    }

}
