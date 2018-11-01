package com.example.mmcke.campuscoupons.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.mmcke.campuscoupons.R;
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button ParentRegister = findViewById(R.id.parentButton);
        ParentRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ParentRegisterActivity.class);
                startActivity(intent);
            }
        });

        Button StudentRegister = findViewById(R.id.studentButton);
        StudentRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), StudentRegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
