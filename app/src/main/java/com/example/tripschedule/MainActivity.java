package com.example.tripschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class MainActivity extends AppCompatActivity {

    private TextView tv_result; // 닉네임 text
    private ImageView iv_profile; // 이미지 뷰
    private ImageButton btn_myschedule;
    private ImageButton btn_plsnstart;
    private Button logout_button;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String nickName=intent.getStringExtra("nickName");
        String photoUrl=intent.getStringExtra("photoUrl");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user==null){
                MyStartActivity(LoginActivity.class);
        }else {
            for (UserInfo profile : user.getProviderData()) {
                String name = profile.getDisplayName();

                if (name == null) {
                        MyStartActivity(MemberAcivity.class);

                }
            }
        }

        tv_result = findViewById(R.id.tv_result);
        tv_result.setText(nickName);

        iv_profile=findViewById(R.id.iv_profile);
        Glide.with(this).load(photoUrl).into(iv_profile); // 프로필 url를 이미지 뷰에 세팅

        btn_myschedule=findViewById(R.id.btn_myschedule);
        btn_myschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_plsnstart=findViewById(R.id.btn_planstart);
        btn_plsnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),CalendarActivity.class);
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
