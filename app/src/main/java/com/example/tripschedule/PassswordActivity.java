package com.example.tripschedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PassswordActivity extends AppCompatActivity {
    private EditText email;
    private Button send_button;
    private Button Cancel_button;
    private FirebaseAuth auth;// 파이어 베이스 인증 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        auth = FirebaseAuth.getInstance(); // 파이어베이스 인증 객체 초기화
        email = findViewById(R.id.EmailPassword);
        send_button = findViewById(R.id.send_button);
        Cancel_button = findViewById(R.id.Cancel_button1);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Send();
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

    private void Send() {
        String EmailPassword1 = ((EditText) findViewById(R.id.EmailPassword)).getText().toString();

        if (EmailPassword1.length() > 0) {

            auth.sendPasswordResetEmail(EmailPassword1)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PassswordActivity.this, "메일을 발송하였습니다.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        } else {
            Toast.makeText(this, "이메일을 입력해 주세요", Toast.LENGTH_SHORT).show();
        }

    }
}
