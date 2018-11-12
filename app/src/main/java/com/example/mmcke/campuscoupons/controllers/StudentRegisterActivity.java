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
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.mmcke.campuscoupons.R;
import com.example.mmcke.campuscoupons.model.StudentUser;
import com.example.mmcke.campuscoupons.model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class StudentRegisterActivity extends AppCompatActivity {

    private RadioButton mOnCampus;
    private RadioButton mOffCampus;
    private EditText mIDNum;
    private EditText mAddressOne;
    private EditText mAddressTwo;
    private Spinner mCountry;
    private Spinner mState;
    private EditText mCity;
    private EditText mZip;
    private final Model model = Model.getInstance();
    private FirebaseAuth mAuth;
    private final ArrayList<String> states = new ArrayList<String>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        mOnCampus = findViewById(R.id.onCampus);
        mOffCampus = findViewById(R.id.offCampus);
        mIDNum = findViewById(R.id.idNumber);
        mAddressOne = findViewById(R.id.addressOne);
        mAddressTwo = findViewById(R.id.addressTwo);
        mCountry = findViewById(R.id.countryChoose);
        mState = findViewById(R.id.stateChoose);
        mCity = findViewById(R.id.city);
        mZip = findViewById(R.id.zipCode);
        mAuth = FirebaseAuth.getInstance();

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

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button SubmitButton = findViewById(R.id.submitButton);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(mOnCampus.isChecked() || mOffCampus.isChecked())) {
                    mOnCampus.setError("Please specify if you live on campus or not.");
                } else {
                    mOnCampus.setError(null);
                    String email = model.getCurrentUser().getEmail();
                    String password = model.getCurrentUser().getPassword();
                    Log.d("Registering", "user");
                    createNewStudentUser(email, password);
                }
            }
        });
    }

    public void createNewStudentUser(String email, String password) {
        if (!validateForm()) {
            return;
        }

        Log.d("Main", "got this far");

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Registered", model.getCurrentUser().toString());
                    StudentUser curUser = (StudentUser)model.getCurrentUser();
                    curUser.setOnCampus(mOnCampus.isChecked());
                    curUser.setAddress(mAddressOne.getText().toString() + mAddressTwo.getText().toString());
                    curUser.setCountry(mCountry.getSelectedItem().toString());
                    curUser.setState(mState.getSelectedItem().toString());
                    curUser.setCity(mCity.getText().toString());
                    curUser.setStudIDNum(mIDNum.getText().toString());
                    curUser.setZip(mZip.getText().toString());
                    StudentUser newUser = new StudentUser(curUser.getFirstName(), curUser.getLastName(),
                            curUser.getEmail(), curUser.getPassword(), curUser.getPhoneNumber(), curUser.getSchoolName(), curUser.getOnCampus(),
                            curUser.getStudIDNum(), curUser.getAddress(), curUser.getCountry(), curUser.getState(),
                            curUser.getCity(), curUser.getZip());
                    model.setCurrentUser(newUser);
                    AddFirebaseUser(newUser);
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void AddFirebaseUser(StudentUser user) {
        db.collection("studentUsers").document(user.getEmail()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Main", "DataSnapshot added successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Main", "Error adding document");
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;
        Editable idNumViewEditable = mIDNum.getText();
        final String idNumViewString = idNumViewEditable.toString();
        Editable addressOneViewEditable = mAddressOne.getText();
        final String addressOneViewString = addressOneViewEditable.toString();
        Editable addressTwoViewEditable = mAddressTwo.getText();
        final String addressTwoViewString = addressTwoViewEditable.toString();
        Editable cityViewEditable = mCity.getText();
        final String cityViewString = cityViewEditable.toString();
        Editable zipViewEditable = mZip.getText();
        final String zipViewString = zipViewEditable.toString();

        if (TextUtils.isEmpty(idNumViewString)) {
            mIDNum.setError("Required.");
            valid = false;
        } else {
            mIDNum.setError(null);
        }

        if (TextUtils.isEmpty(addressOneViewString)) {
            mAddressOne.setError("Required.");
            valid = false;
        } else {
            mAddressOne.setError(null);
        }

        if (TextUtils.isEmpty(cityViewString)) {
            mCity.setError("Required.");
            valid = false;
        } else {
            mCity.setError(null);
        }

        if (TextUtils.isEmpty(zipViewString)) {
            mZip.setError("Required.");
            valid = false;
        } else {
            mZip.setError(null);
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
