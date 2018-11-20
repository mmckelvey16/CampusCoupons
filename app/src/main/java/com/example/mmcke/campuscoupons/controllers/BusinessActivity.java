package com.example.mmcke.campuscoupons.controllers;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mmcke.campuscoupons.R;
import com.example.mmcke.campuscoupons.databinding.ActivityBusinessBinding;
import com.example.mmcke.campuscoupons.model.BusinessUser;
import com.example.mmcke.campuscoupons.model.Model;

public class BusinessActivity extends AppCompatActivity {

    private final Model model = Model.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBusinessBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_business);

        BusinessUser curUser = (BusinessUser)model.getCurrentUser();
        binding.setUser(curUser);

        Button AddCoupon = findViewById(R.id.addCoupon);
        AddCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddCouponActivity.class);
                startActivity(intent);
            }
        });

        Button LogOut = findViewById(R.id.logout);
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), OpeningActivity.class);
                startActivity(intent);
            }
        });

    }
}
