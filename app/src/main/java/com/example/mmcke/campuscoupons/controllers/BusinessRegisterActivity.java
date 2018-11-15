package com.example.mmcke.campuscoupons.controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mmcke.campuscoupons.R;
import com.example.mmcke.campuscoupons.model.BusinessUser;
import com.example.mmcke.campuscoupons.model.School;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BusinessRegisterActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mPhone;
    private EditText mAddressOne;
    private EditText mAddressTwo;
    private Spinner mSchools;
    private EditText mPass;
    private EditText mConfirmPass;
    private EditText mEmail;
    private EditText mFirstName;
    private EditText mLastName;
    private final ArrayList<String> schools = new ArrayList<String>();
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_register);

        mName = findViewById(R.id.businessName);
        mFirstName = findViewById(R.id.fName);
        mLastName = findViewById(R.id.lName);
        mPhone = findViewById(R.id.phone);
        mAddressOne = findViewById(R.id.addressOne);
        mAddressTwo = findViewById(R.id.addressTwo);
        mSchools = findViewById(R.id.selectSchool);
        mPass = findViewById(R.id.password);
        mConfirmPass = findViewById(R.id.confirmPassword);
        mEmail = findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();

        this.readEnumSchools();

        final ArrayAdapter<String> schoolAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, schools);
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSchools.setAdapter(schoolAdapter);

        Button SubmitButton = findViewById(R.id.submitButton);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Editable passEditable = mPass.getText();
                String pass = passEditable.toString();
                Editable confirmPassEditable = mConfirmPass.getText();
                String confirmPass = confirmPassEditable.toString();
                Editable emailEditable = mEmail.getText();
                String email = emailEditable.toString();

                if (!pass.equals(confirmPass)) {
                    mPass.setError("Passwords must match");
                } else {
                    createNewBusiness(email, pass);
                }
            }
        });
    }

    public void readEnumSchools() {
        for (School school: School.values()) {
            schools.add(school.getTitle());
        }
    }

    private void createNewBusiness(String email, String pass) {
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Editable nameEditable = mName.getText();
                String name = nameEditable.toString();
                Editable fNameEditable = mFirstName.getText();
                String fName = fNameEditable.toString();
                Editable lNameEditable = mLastName.getText();
                String lName = lNameEditable.toString();
                Editable phoneEditable = mPhone.getText();
                String phone = phoneEditable.toString();
                Editable emailEditable = mEmail.getText();
                String email = emailEditable.toString();
                Editable passEditable = mPass.getText();
                String pass = passEditable.toString();
                String school = mSchools.getSelectedItem().toString();

                if (task.isSuccessful()) {
                    Log.d("Main", "registered business");
                    BusinessUser curUser = new BusinessUser(fName, lName, name, email, phone,
                            mAddressOne.getText().toString() + mAddressTwo.getText().toString(), pass, school);
                    addFirebaseUser(curUser);
                    Intent intent = new Intent(getBaseContext(), BusinessActivity.class);
                    startActivity(intent);
                } else {
                    mName.setError("Error registering business");
                    Log.e("Main", "Error registering business");
                }
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;
        Editable nameViewEditable = mName.getText();
        final String nameViewString = nameViewEditable.toString();
        Editable fNameViewEditable = mFirstName.getText();
        final String fNameViewString = fNameViewEditable.toString();
        Editable lNameViewEditable = mLastName.getText();
        final String lNameViewString = lNameViewEditable.toString();
        Editable passViewEditable = mPass.getText();
        final String passViewString = passViewEditable.toString();
        Editable emailViewEditable = mEmail.getText();
        final String emailViewString = emailViewEditable.toString();
        Editable phoneViewEditable = mPhone.getText();
        final String phoneViewString = phoneViewEditable.toString();

        if (TextUtils.isEmpty(nameViewString)) {
            mName.setError("Required.");
            valid = false;
        } else {
            mName.setError(null);
        }

        if (TextUtils.isEmpty(fNameViewString)) {
            mFirstName.setError("Required.");
            valid = false;
        } else {
            mFirstName.setError(null);
        }

        if (TextUtils.isEmpty(lNameViewString)) {
            mLastName.setError("Required.");
            valid = false;
        } else {
            mLastName.setError(null);
        }

        if (TextUtils.isEmpty(passViewString)) {
            mPass.setError("Required.");
            valid = false;
        } else {
            mPass.setError(null);
        }

        if (TextUtils.isEmpty(emailViewString)) {
            mEmail.setError("Required.");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        if (TextUtils.isEmpty(phoneViewString)) {
            mPhone.setError("Required.");
            valid = false;
        } else {
            mPhone.setError(null);
        }
        return valid;
    }

    private void addFirebaseUser(BusinessUser user) {
        db.collection("businesses").document(user.getBusName()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Main", "Added user");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Main", "Error adding user");
            }
        });
    }
}
