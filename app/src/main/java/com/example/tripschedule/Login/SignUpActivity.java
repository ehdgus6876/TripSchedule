package com.example.tripschedule.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tripschedule.MainActivity;
import com.example.tripschedule.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText passwordCheck;
    private ImageButton signup_button;
    private ImageButton Cancel_button;
    private FirebaseAuth auth;// 파이어 베이스 인증 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance(); // 파이어베이스 인증 객체 초기화
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        passwordCheck = findViewById(R.id.PasswordCheck);
        signup_button = findViewById(R.id.send_button);
        Cancel_button = findViewById(R.id.Cancle_button);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp(email.getText().toString(), password.getText().toString());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


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

    private void SignUp(String email, String password) { //회원가입 했을때 일어나는 것
        String email1 = ((EditText) findViewById(R.id.Email)).getText().toString();
        String password1 = ((EditText) findViewById(R.id.Password)).getText().toString();
        String passwordcheck1 = ((EditText) findViewById(R.id.PasswordCheck)).getText().toString();

        if (email1.length()>0 && password1.length()>0 && passwordcheck1.length()>0) {
            if (password1.equals(passwordcheck1)) {
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(SignUpActivity.this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    FirebaseUser user = auth.getCurrentUser();
                                } else {
                                    if (task.getException() != null) {
                                        Toast.makeText(SignUpActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            } else {
                Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
            else{
                Toast.makeText(this,"이메일 또는 비밀번호를 입력해 주세요",Toast.LENGTH_SHORT).show() ;
            }

    }
}