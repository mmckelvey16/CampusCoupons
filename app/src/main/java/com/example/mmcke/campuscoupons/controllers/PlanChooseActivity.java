package com.example.mmcke.campuscoupons.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mmcke.campuscoupons.R;

public class PlanChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_choose);

        Button WhiteButton = findViewById(R.id.whiteButton);
        WhiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), WhitePlanActivity.class);
                startActivity(intent);
            }
        });

        Button BlueButton = findViewById(R.id.blueButton);
        BlueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), BluePlanActivity.class);
                startActivity(intent);
            }
        });

        Button GoldButton = findViewById(R.id.goldButton);
        GoldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), GoldPlanActivity.class);
                startActivity(intent);
            }
        });
    }
}
