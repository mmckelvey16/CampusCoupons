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
import com.example.mmcke.campuscoupons.model.Model;
import com.example.mmcke.campuscoupons.model.ParentUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ParentRegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private final Model model = Model.getInstance();

    private EditText mStudNameView;
    private EditText mStudEmailView;
    private EditText mStudPhoneView;
    private EditText mStudIDView;
    private Spinner mCountry;
    private Spinner mState;
    private EditText mAddressOne;
    private EditText mAddressTwo;
    private EditText mCity;
    private EditText mZip;
    private final ArrayList<String> states = new ArrayList<String>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_register);
        mAuth = FirebaseAuth.getInstance();

        mStudNameView = findViewById(R.id.studentName);
        mStudEmailView = findViewById(R.id.studentEmail);
        mStudPhoneView = findViewById(R.id.studentPhone);
        mStudIDView = findViewById(R.id.studentID);
        mCountry = findViewById(R.id.countryChoose);
        mState = findViewById(R.id.stateChoose);
        mAddressOne = findViewById(R.id.addressOne);
        mAddressTwo = findViewById(R.id.addressTwo);
        mCity = findViewById(R.id.city);
        mZip = findViewById(R.id.zip);

        this.readStates();

        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);

        final ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountry.setAdapter(countryAdapter);

        final ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, states);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mState.setAdapter(stateAdapter);

        Button SubmitButton = findViewById(R.id.submitButton);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = model.getCurrentUser().getEmail();
                String password = model.getCurrentUser().getPassword();
                createNewParentUser(email, password);
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void createNewParentUser(String email, String password) {
        if (!validateForm()) {
            return;
        }

        Log.d("Main", "got this far");

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Registered", model.getCurrentUser().toString());
                    ParentUser curUser = (ParentUser) model.getCurrentUser();
                    curUser.setAddress(mAddressOne.getText().toString() + mAddressTwo.getText().toString());
                    curUser.setCountry(mCountry.getSelectedItem().toString());
                    curUser.setState(mState.getSelectedItem().toString());
                    curUser.setCity(mCity.getText().toString());
                    curUser.setZip(mZip.getText().toString());
                    curUser.setStudEmail(mStudEmailView.getText().toString());
                    curUser.setStudIDNum(mStudIDView.getText().toString());
                    curUser.setStudName(mStudNameView.getText().toString());
                    curUser.setStudPhone(mStudPhoneView.getText().toString());
                    ParentUser newUser = new ParentUser(curUser.getFirstName(), curUser.getLastName(), curUser.getEmail(),
                            curUser.getPassword(), curUser.getPhoneNumber(), curUser.getSchoolName(), curUser.getStudName(), curUser.getStudEmail(),
                            curUser.getStudPhone(), curUser.getStudIDNum(), curUser.getAddress(), curUser.getCountry(),
                            curUser.getState(), curUser.getCity(), curUser.getZip());
                    model.setCurrentUser(newUser);
                    AddFirebaseUser((ParentUser)newUser);
                    Intent intent = new Intent(getBaseContext(), PlanChooseActivity.class);
                    startActivity(intent);
                } else {
                    mStudNameView.setError("Registration failure");
                    Log.w("Failure", "Registration failure");
                    Log.e("Failure", task.getException().getMessage());
                }
            }
        });

    }

    private void AddFirebaseUser(ParentUser user) {
        db.collection("users").document(user.getEmail()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Main", "DocumentSnapshot added successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Main", "Error adding document", e);
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;
        Editable studNameViewEditable = mStudNameView.getText();
        final String studNameViewString = studNameViewEditable.toString();
        Editable studEmailViewEditable = mStudEmailView.getText();
        final String studEmailViewString = studEmailViewEditable.toString();
        Editable studPhoneViewEditable = mStudPhoneView.getText();
        final String studPhoneViewString = studPhoneViewEditable.toString();
        Editable studIDViewEditable = mStudIDView.getText();
        final String studIDViewString = studIDViewEditable.toString();

        if (TextUtils.isEmpty(studNameViewString)) {
            mStudNameView.setError("Required.");
            valid = false;
        } else {
            mStudNameView.setError(null);
        }

        if (TextUtils.isEmpty(studEmailViewString)) {
            mStudEmailView.setError("Required.");
            valid = false;
        } else {
            mStudEmailView.setError(null);
        }

        if (TextUtils.isEmpty(studPhoneViewString)) {
            mStudPhoneView.setError("Required.");
            valid = false;
        } else {
            mStudPhoneView.setError(null);
        }

        if (TextUtils.isEmpty(studIDViewString)) {
            mStudIDView.setError("Required.");
            valid = false;
        } else {
            mStudIDView.setError(null);
        }
        return valid;
    }

    private void readStates() {

        try {
            InputStream is = getResources().openRawResource(R.raw.states);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            br.readLine();
            String line = br.readLine();

            final String[] tokens = line.split(("#"));
            for (int i = 0; i < 50; i++) {
                String curState = tokens[i];
                states.add(curState);
            }
            br.close();
        } catch (IOException e) {
            Log.e("Main", "error reading assets", e);
        }
    }
}
