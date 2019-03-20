package com.example.android.rodar.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.android.rodar.FragmentInicial;
import com.example.android.rodar.R;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportFragmentManager().beginTransaction().replace(R.id.inicial_fragment_container,new FragmentInicial()).commit();
    }





}
