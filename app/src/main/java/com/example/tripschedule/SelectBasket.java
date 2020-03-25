package com.example.tripschedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SelectBasket extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SelectAdapter adapter;
    private Button btn_select,btn_map;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_basket);
        btn_select=findViewById(R.id.btn_select);
        btn_map=findViewById(R.id.btn_map);

        recyclerView=findViewById(R.id.rv_speedBus);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView=findViewById(R.id.rv_speedBus);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter=new SelectAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        for(int i=0;i <FoodAdapter.selectItems.size();i++){
            if(FoodAdapter.selectItems.get(i).getCode()==4){
                count++;
            }
        }

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MapActivity.class);
                startActivity(intent);
            }
        });
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==0){
                    Toast.makeText(getApplicationContext(),"숙소를 최소 1개이상을 선택해주세요",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent1 = new Intent(getApplicationContext(), ScheduleActivity.class);
                    startActivity(intent1);
                }
            }
        });

    }
}
