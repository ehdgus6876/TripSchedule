package com.example.tripschedule;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.SearchView;

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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TrainFragment extends Fragment implements TextWatcher {

    private RecyclerView recyclerView;
    private TrainAdapter adapter1;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<TrainStation> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private EditText search;
    private CheckBox cb_Ulsan,cb_TaeHwaKang;
    static public String Station;


    public TrainFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.train,container,false);
        recyclerView=v.findViewById(R.id.TrainRecyclerView);
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>(); // TrainStation 객체 담을 어레이 리스트
        //searchView=v.findViewById(R.id.searchView);
        search=v.findViewById(R.id.search);
        search.addTextChangedListener(this);
        cb_TaeHwaKang=v.findViewById(R.id.cb_TaeHwaKang);
        cb_Ulsan=v.findViewById(R.id.cb_Ulsan);

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        cb_Ulsan.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                CalendarActivity.transport=4;
                Station="3300300";
                cb_TaeHwaKang.setChecked(false);
                databaseReference=database.getReference("transport/train/Ulsan");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                        arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){ //반복문으로 데이터 List 추출해냄
                            TrainStation TrainStation = snapshot.getValue(TrainStation.class); // 만들어뒀던 TrainStation 객체에 데이터를 담는다.
                            arrayList.add(TrainStation); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                        }
                        adapter1.notifyDataSetChanged();
                        // 리스트 저장 및 새로고침
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // 디비를 가져오던 중 에러 발생 시
                        Log.e("TrainFragment", String.valueOf(databaseError.toException()));
                    }
                });

                Log.d("station",Station);
            }
        });
        cb_TaeHwaKang.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                CalendarActivity.transport=5;
                Station="3300200";
                Log.d("station",Station);
                databaseReference=database.getReference("transport/train/TaeHwaGang");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                        arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){ //반복문으로 데이터 List 추출해냄
                            TrainStation TrainStation = snapshot.getValue(TrainStation.class); // 만들어뒀던 TrainStation 객체에 데이터를 담는다.
                            arrayList.add(TrainStation); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                        }
                        adapter1.notifyDataSetChanged();
                        // 리스트 저장 및 새로고침
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // 디비를 가져오던 중 에러 발생 시
                        Log.e("TrainFragment", String.valueOf(databaseError.toException()));
                    }
                });

                cb_Ulsan.setChecked(false);
            }
        });
        adapter1 = new TrainAdapter(arrayList,getContext());
        recyclerView.setAdapter(adapter1); //리사이클러뷰에 어댑터 연결
        return v;

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter1.getFilter().filter(s);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}


