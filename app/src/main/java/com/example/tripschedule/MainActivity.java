package com.example.tripschedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.tripschedule.Calendar.CalendarActivity;
import com.example.tripschedule.Login.LoginActivity;
import com.example.tripschedule.Login.MemberAcivity;
import com.example.tripschedule.MySchedule.OnlyDateAdapter;
import com.example.tripschedule.MySchedule.OnlyDateItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "로그";
    private ImageButton btn_plsnstart;
    private ImageButton logout_button;
    private RecyclerView rv;
    private OnlyDateAdapter onlyDateAdapter=new OnlyDateAdapter();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv= findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("hh",String.valueOf(user));
        if(user==null){
                MyStartActivity(LoginActivity.class);
        }else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(user.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if(document !=null){
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                            } else {
                                Log.d(TAG, "No such document");
                                MyStartActivity(MemberAcivity.class);
                            }
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());

                    }
                }
            });

        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("schedule")
                .whereEqualTo("publisher",user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                onlyDateAdapter.addItem(new OnlyDateItem(document.getData().get("startdate").toString(),
                                        document.getData().get("enddate").toString(),document.getId()));

                            }
                            rv.setAdapter(onlyDateAdapter);
                        } else {
                            Log.d("실패", "Error getting documents: ", task.getException());
                        }

                    }
                });


        btn_plsnstart=findViewById(R.id.btn_planstart);
        btn_plsnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent1);

            }
        });

        logout_button=findViewById(R.id.logout_button);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent1);
            }
        });

    }
    private void MyStartActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
