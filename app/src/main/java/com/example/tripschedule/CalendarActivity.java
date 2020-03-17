package com.example.tripschedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
    private TextView textView;
    private CheckBox carButton;
    private CheckBox transportButton;
    static public String arrivaltime;
    static public String sendStartDate;
    static public String sendFinishDate;
    static public int dateNum;
    static public int transport=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendar=findViewById(R.id.calendarView);
        btn_start=findViewById(R.id.btn_start);
        btn_finish=findViewById(R.id.btn_finish);
        btn_re=findViewById(R.id.btn_re);
        btn_complete=findViewById(R.id.btn_complete);
        textView=findViewById(R.id.textView2);
        carButton=findViewById(R.id.cb_car);
        transportButton=findViewById(R.id.cb_transport);

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
                        textView.setText("도착날짜 클릭 후 날짜를 선택해주세요");
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

                        if(Integer.valueOf(sendFinishDate)>Integer.valueOf(sendStartDate)){
                            finishdate=year+"/"+(month+1)+"/"+dayOfMonth;
                            btn_finish.setText(finishdate);
                            if(!carButton.isChecked() && !transportButton.isChecked()){
                                textView.setText("교통수단을 선택해주세요");
                            }
                            else{
                                textView.setText("결정완료 버튼을 눌러주세요");
                            }

                        }
                        else{
                            Toast.makeText(CalendarActivity.this,"출발날짜보다 도착날짜가 더 빠릅니다!",Toast.LENGTH_SHORT).show();
                        }



                    }
                });
            }
        });
        carButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transportButton.setChecked(false);
                transport=1;
            }
        });
        transportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carButton.setChecked(false);
            }
        });

        btn_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_start.setText("출발날짜");
                btn_finish.setText("도착날짜");
                textView.setText("출발날짜 클릭 후 날짜를 선택해주세요");
            }
        });

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startdate=btn_start.getText().toString();
                finishdate=btn_finish.getText().toString();
                if(carButton.isChecked()){
                    TimePickerDialog dialog = new TimePickerDialog(CalendarActivity.this, listener, 12, 00, true);
                    dialog.show();


                }
                else{
                    Intent intent=new Intent(getApplicationContext(),TransportActivity.class);
                    intent.putExtra("startdate",startdate);
                    intent.putExtra("finishdate",finishdate);
                    startActivity(intent);
                }

            }
        });

    }
    private TimePickerDialog.OnTimeSetListener listener=new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            arrivaltime = String.valueOf(hourOfDay * 100 + minute);
            if( arrivaltime.length()==3){
                arrivaltime="0"+arrivaltime;
            }
            Log.d("dong", arrivaltime);
            Intent intent = new Intent(getApplicationContext(),SelectLocation.class);
            intent.putExtra("startdate",startdate);
            intent.putExtra("finishdate",finishdate);
            startActivity(intent);
        }
    };

}
