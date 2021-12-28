package com.nomadlab.mysharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnSave;
    Button btnLoad;
    Button btnDelete;
    Button btnDeleteAll;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initData();
        addEventListener();

        // SharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences("sp1", MODE_PRIVATE);
        // Mode
        // - MODE_PRIVATE : 생성한 어플리케이션에서만 사용 가능
        // - MODE_WORLD_READABLE : 다른 앱에서 사용 가능 -> 읽을수만 있다
        // - MODE_WORLD_WRITEABLE : 다른 앱에서 사용 가능 -> 기록도 가능
        // - MODE_MULTI_PROCESS : 이미 호출되어 사용중인지 체크
        // - MODE_APPEND : 기존 preference 에 신규로 추가
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", "홍길동");
        editor.commit();

        //
        // apply() 와 commit() 차이
        // apply() - 스레드를 블록 시키지 않는다.
        //         - 비동기 방식
        //         - 저장 성공여부 true, false 값을 주지 않는다.

        // commit() - 스레드가를 블록된다.
        //          - 동기 방식
        //          - commit 는 (저장이) 성공하면 boolean 타입인 true 값을 반환

//        clear()
//        boolean commit ()
//        putBoolean(String key, boolean value)
//        putFloat(String key, float value)
//        putInt(String key, int value)
//        putLong(String key, long value)
//        putString(String key, String value)
//        putStringSet(String key, Set < String > values)
//        remove(String key)

        // sp1 -> SharedPreference
        //        (key, value) -> ("name", "홍길동")
        // sp2 ->SharedPreference
        //        (key, value) -> ("name", "김철수")

    }

    private void addEventListener() {
        btnSave.setOnClickListener(view -> {
            SharedPreferences.Editor editor = getShared("sp1").edit();
            editor.putString("name", "홍길동");
            editor.putInt("age", 20);
            editor.putBoolean("isMarried", true);
            editor.apply();
        });

        btnLoad.setOnClickListener(view -> {
            SharedPreferences preferences = getShared("sp1");
            String name = preferences.getString("name", "");
            int age = preferences.getInt("age", 0);
            boolean isMarried = preferences.getBoolean("isMarried", false);
            String tempStr = name + " : " + age + " :  " + isMarried;
            Toast.makeText(this, tempStr, Toast.LENGTH_SHORT).show();
        });

        btnDelete.setOnClickListener(view -> {
            getShared("sp1").edit().remove("age").apply();

            btnLoad.callOnClick();
        });
        btnDeleteAll.setOnClickListener(view -> {
            getShared("sp1").edit().clear().apply();

            btnLoad.callOnClick();
        });
    }

    private SharedPreferences getShared(String name) {
        return getSharedPreferences(name, MODE_PRIVATE);
    }

    private void initData() {
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);
        btnDelete = findViewById(R.id.btnDelete);
        btnDeleteAll = findViewById(R.id.btnDeleteAll);
    }
}