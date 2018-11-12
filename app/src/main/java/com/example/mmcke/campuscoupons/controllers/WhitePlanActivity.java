package com.example.mmcke.campuscoupons.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mmcke.campuscoupons.R;

public class WhitePlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_plan);

        Button BackButton = findViewById(R.id.backButton);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PlanChooseActivity.class);
                startActivity(intent);
            }
        });

        Button SelectButton = findViewById(R.id.selectButton);
        SelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });
    }
}
