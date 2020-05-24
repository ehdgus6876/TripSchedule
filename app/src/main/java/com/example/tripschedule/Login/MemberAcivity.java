package com.example.tripschedule.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tripschedule.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MemberAcivity extends AppCompatActivity {
    private EditText Name;
    private EditText Telphone;
    private EditText Birthday;
    private EditText Address;
    private Button Check_button;
    private Button Cancel_button;
    private FirebaseAuth auth;// 파이어 베이스 인증 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        Name = findViewById(R.id.Name);
        Telphone= findViewById(R.id.Password);
        Birthday = findViewById(R.id.Birthday);
        Address = findViewById(R.id.Address);
        Check_button = findViewById(R.id.Check_button);
        Cancel_button = findViewById(R.id.Cancel_button2);



        Check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileUpdate();
            }
        });
        Cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }


    private void profileUpdate() { //회원가입 했을때 일어나는 것
        String name = ((EditText) findViewById(R.id.Name)).getText().toString();
        String Address = ((EditText) findViewById(R.id.Address)).getText().toString();
        String Telphone = ((EditText) findViewById(R.id.Telphone)).getText().toString();
        String Birthday = ((EditText) findViewById(R.id.Birthday)).getText().toString();


        if (name.length()>0 && Telphone.length()>10 && Birthday.length()>7 && Address.length()>0) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            MemberInfo memberInfo = new MemberInfo(name,Address,Telphone,Birthday);
            if(user != null){

                db.collection("users").document(user.getUid()).set(memberInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startToast("회원정보 등록을 성공하였습니다.");
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startToast("회원정보 등록을 실패하였습니다.");

                            }
                        });

            }

        } else {
                Toast.makeText(this, "회원정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
        private void startToast(String msg){
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        }
        private void MyStartActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        }

}


