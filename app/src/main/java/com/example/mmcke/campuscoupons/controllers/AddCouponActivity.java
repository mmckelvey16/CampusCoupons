package com.example.mmcke.campuscoupons.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mmcke.campuscoupons.R;
import com.example.mmcke.campuscoupons.model.BusinessUser;
import com.example.mmcke.campuscoupons.model.Coupon;
import com.example.mmcke.campuscoupons.model.Model;
import com.example.mmcke.campuscoupons.model.School;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddCouponActivity extends AppCompatActivity {

    private EditText name;
    private EditText details;
    private final Model model = Model.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon);

        name = findViewById(R.id.name);
        details = findViewById(R.id.details);

        Button Add = findViewById(R.id.add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable mName = name.getText();
                String mNameView = mName.toString();
                Editable mDetails = details.getText();
                String mDetailsView = mDetails.toString();

                BusinessUser user = (BusinessUser)model.getCurrentUser();
                School school = getEnumFromString(School.class, user.getSchoolName());
                Log.d("Main", "School name is " + user.getSchoolName());
                Log.d("Main", "enum from string gives" + getEnumFromString(School.class, user.getSchoolName()));
                Log.d("Main", "School is " + school);

                Coupon coupon = new Coupon(school, user.getBusName(), false, mNameView, mDetailsView);
                school.addCoupon(coupon);
                db.collection("coupons").document(mNameView).set(coupon);
                Log.d("Main", school.getCoupons().toString());
                Intent intent = new Intent(getBaseContext(), BusinessActivity.class);
                startActivity(intent);
            }
        });
    }

    public static <School extends Enum<School>> School getEnumFromString (Class<School> c, String string) {
        if (c != null && string != null) {
            try {
                return Enum.valueOf(c, string.replaceAll("\\s+", "").toUpperCase());
            } catch (IllegalArgumentException ex) {

            }
        }
        return null;
    }
}
