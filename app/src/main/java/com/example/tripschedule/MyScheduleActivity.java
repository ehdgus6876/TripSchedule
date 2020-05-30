package com.example.tripschedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tripschedule.Schedule.ItemTouchHelperCallback;
import com.example.tripschedule.SelectLocation.SelectItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MyScheduleActivity extends AppCompatActivity {
    ImageButton btn_save,btn_back,btn_del;
    MyScheduleadapter adapter=new MyScheduleadapter();
    ItemTouchHelper helper;
    ArrayList<SelectItem> selectItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String start = intent.getExtras().getString("start");
        String end = intent.getExtras().getString("end");
        final String id= intent.getExtras().getString("id");
        setContentView(R.layout.activity_my_schedule);
        final RecyclerView recyclerView = findViewById(R.id.scheduleRecycle);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter)); //RecyclerView에 ItemTouchHelper 붙이기
        helper.attachToRecyclerView(recyclerView);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("schedule")
                .whereEqualTo("publisher",user.getUid())
                .whereEqualTo("startdate",start)
                .whereEqualTo("enddate",end)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("성공", document.getId() + " => " + document.getData());
                                List list = (List) document.getData().get("plan");

                                for(int i=0;i<list.size();i++) {

                                    HashMap map = (HashMap) list.get(i);
                                    if (!map.get("title").toString().contains("일차")) {
                                        adapter.addItem(new SelectItem(map.get("title").toString(), "",
                                                "", "", "",
                                                map.get("mapx").toString(), map.get("mapy").toString(), Integer.parseInt(map.get("code").toString())));
                                    } else {
                                        adapter.addItem(new SelectItem(map.get("title").toString(), "",
                                                "", "", "",
                                                "", "", Integer.parseInt(map.get("code").toString())));
                                    }
                                }
                                recyclerView.setAdapter(adapter);
                            }
                        } else {
                            Log.d("실패", "Error getting documents: ", task.getException());
                        }
                    }
                });
        btn_save=findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItems.addAll(adapter.getArray());
                DocumentReference washingtonRef = db.collection("schedule").document(id);
                washingtonRef
                        .update("plan", selectItems)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startToast("여행일정을 저장하였습니다.");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);



            }
        });
        btn_del=findViewById(R.id.btn_del);
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("schedule").
                        document(id)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startToast("여행일정이 삭제 되었습니다.");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });

    }
    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


}
