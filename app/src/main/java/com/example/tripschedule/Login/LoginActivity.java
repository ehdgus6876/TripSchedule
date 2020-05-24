package com.example.tripschedule.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.tripschedule.MainActivity;
import com.example.tripschedule.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.WebMercatorCoord;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private SignInButton btn_google;// 구글 로그인 버튼
    private FirebaseAuth auth;// 파이어 베이스 인증 객체
    private GoogleApiClient googleApiClient; // 구글 API 클라이언트 객체
    private static final int REQ_SIGN_GOOGLE = 100; //구글 로그인 결과 코드
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ImageButton login_button,password_button;
    BufferedReader br;
    StringBuilder searchResult;
    public static int naver;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        auth = FirebaseAuth.getInstance(); // 파이어베이스 인증 객체 초기화


        btn_google = findViewById(R.id.btn_google);
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REQ_SIGN_GOOGLE);
            }
        });

        editTextEmail=findViewById(R.id.editText_email);
        editTextPassword=findViewById(R.id.editText_Passowrd);
        ImageButton emailLogin = findViewById(R.id.signup_button);
        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
        login_button=findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(editTextEmail.getText().toString(),editTextPassword.getText().toString());
            }
        });
        password_button=findViewById(R.id.password_button);
        password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), PassswordActivity.class);
                startActivity(intent);

            }
        });
        Log.d("dong 결과",String.valueOf(searchNaver()));


    }



    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
    private void Login(String email, String password) { //회원가입 했을때 일어나는 것
        String email1 = ((EditText) findViewById(R.id.editText_email)).getText().toString();
        String password1 = ((EditText) findViewById(R.id.editText_Passowrd)).getText().toString();


        if (email1.length()>0 && password1.length()>0 ) {
            {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = auth.getCurrentUser();
                                    Toast.makeText(LoginActivity.this, "로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                    MyStartActivity(MainActivity.class);
                                } else {
                                    if(task.getException()!=null){
                                        Toast.makeText(LoginActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        }
        else{
            Toast.makeText(this,"이메일 또는 비밀번호를 입력해 주세요",Toast.LENGTH_SHORT).show() ;
        }
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data)
    { // 구글 로그인 인증을 요청 했을 때 결과 값을 되돌려 받는 곳
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) { // 인증결과가 성공적이면
                GoogleSignInAccount account = result.getSignInAccount(); // account 라는 데이터는 구글로그인 정보를 담고있다. (닉네임,프로필사진Uri,이메일주소...등)
                resultLogin(account); // 로그인 결과 값 출력 수행하라는 메소드
            }
        }
    }

    private void resultLogin ( final GoogleSignInAccount account){
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { // 로그인 성공했으면
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("nickName", account.getDisplayName());
                            intent.putExtra("photoUri", String.valueOf(account.getPhotoUrl()));
                            startActivity(intent);
                        } else { // 로그인 실패했으면
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT);
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed (@NonNull ConnectionResult connectionResult){

    }

    private void MyStartActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public int searchNaver() { // 검색어 = searchObject로 ;
        int result1=0;
        final String clientId = "7e7cc797q1";
        final String clientSecret = "MrCQ9XX0nfNXHb1vw45otjiB7xGay2rxUC2q5jhu";
        String result = "";

        // 네트워크 연결은 Thread 생성 필요
        ExecutorService mPool = Executors.newFixedThreadPool(5);
        Future<Integer> mFuture = mPool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                WebMercatorCoord webMercatorCoord = new WebMercatorCoord(14387795.3645812, 4238022.0645391);
                LatLng location = webMercatorCoord.toLatLng();
                WebMercatorCoord webMercatorCoord1 = new WebMercatorCoord(14396665.8571710, 4237453.8689881);
                LatLng location1 = webMercatorCoord1.toLatLng();

                String apiURL = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?" +
                        "start=" + location.longitude + "," + location.latitude + "&goal=" + location1.longitude + "," + location1.latitude + "&option=trafast"; // json 결과
                // Json 형태로 결과값을 받아옴.

                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
                con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
                con.connect();

                int responseCode = con.getResponseCode();


                if (responseCode == 200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }

                searchResult = new StringBuilder();
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    searchResult.append(inputLine + "\n");

                }

                br.close();
                con.disconnect();
                JSONObject json = new JSONObject(String.valueOf(searchResult));
                JSONObject json1 = json.getJSONObject("route");
                JSONArray json2 = json1.getJSONArray("trafast");
                JSONObject json3 = json2.getJSONObject(0);
                JSONObject json4 = json3.getJSONObject("summary");
                naver = json4.getInt("distance");
                Log.d("dong : distance", String.valueOf(json4.getInt("distance")));
                return json4.getInt("distance");
            }
        });
        try{
            result1=mFuture.get();
            Log.d("dong result1",String.valueOf(result1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result1;
    }
}

