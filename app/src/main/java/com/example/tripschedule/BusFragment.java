package com.example.tripschedule;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BusFragment extends Fragment implements TextWatcher {

    public static boolean check_bus;
    public static boolean check_speedbus;
    private RecyclerView recyclerView;
    private BusAdapter adapter;
    private BusAdapter adapter_speed;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<BusStation> arrayList;
    private ArrayList<BusStation> arrayList_speed;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference_speed;
    private EditText editText;
    private CheckBox cb_speedBus,cb_bus;

    public BusFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.bus,container,false);
        recyclerView=v.findViewById(R.id.BusRecyclerView);

        editText = v.findViewById(R.id.editTextFilter);
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존성능 강화
        cb_bus=v.findViewById(R.id.cb_bus);
        cb_speedBus=v.findViewById(R.id.cb_speedBus);
        arrayList=new ArrayList<>(); // BusStation 객체 담을 어레이 리스트
        arrayList_speed=new ArrayList<>();
        editText.addTextChangedListener(this);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동




        cb_bus.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_speedBus.setChecked(false);
                check_bus=true;
                check_speedbus=false;
                databaseReference=database.getReference("transport/busstation");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                        arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){ //반복문으로 데이터 List 추출해냄
                            BusStation busStation = snapshot.getValue(BusStation.class); // 만들어뒀던 BusStation 객체에 데이터를 담는다.
                            arrayList.add(busStation); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                        }
                        adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // 디비를 가져오던 중 에러 발생 시
                        Log.e("BusFragment", String.valueOf(databaseError.toException()));
                    }

                });
                adapter = new BusAdapter(arrayList, getActivity());
                recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결

            }
        });
        cb_speedBus.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_bus.setChecked(false);
                check_bus=false;
                check_speedbus=true;
                databaseReference_speed=database.getReference("transport/speedbusstation");
                databaseReference_speed.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //파이어베이스 데이터베이스의 데이터를 받아오는 곳

                        arrayList_speed.clear(); // 기존 배열리스트가 존재하지않게 초기화
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){ //반복문으로 데이터 List 추출해냄
                            BusStation busStation_speed= snapshot.getValue(BusStation.class); // 만들어뒀던 BusStation 객체에 데이터를 담는다.
                            arrayList_speed.add(busStation_speed); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                        }
                        adapter_speed.notifyDataSetChanged(); // 리스트 저장 및 새로고침
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // 디비를 가져오던 중 에러 발생 시
                        Log.e("BusFragment", String.valueOf(databaseError.toException()));
                    }

                });
                adapter_speed = new BusAdapter(arrayList_speed, getActivity());
                recyclerView.setAdapter(adapter_speed); //리사이클러뷰에 어댑터 연결
            }
        });
        return v;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
