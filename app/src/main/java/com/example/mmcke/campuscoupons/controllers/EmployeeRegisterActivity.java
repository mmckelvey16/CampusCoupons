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
import com.example.mmcke.campuscoupons.model.EmployeeUser;
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

public class EmployeeRegisterActivity extends AppCompatActivity {

    private EditText mEmployeeID;
    private EditText mAddressOne;
    private EditText mAddressTwo;
    private EditText mCity;
    private EditText mZip;
    private Spinner mCountry;
    private Spinner mState;
    private final Model model = Model.getInstance();
    private FirebaseAuth mAuth;
    private final ArrayList<String> states = new ArrayList<String>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);

        mAuth = FirebaseAuth.getInstance();
        mEmployeeID = findViewById(R.id.idNumber);
        mAddressOne = findViewById(R.id.addressOne);
        mAddressTwo = findViewById(R.id.addressTwo);
        mCity = findViewById(R.id.city);
        mZip = findViewById(R.id.zipCode);
        mCountry = findViewById(R.id.countryChoose);
        mState = findViewById(R.id.stateChoose);

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
                String email = model.getCurrentUser().getEmail();
                String password = model.getCurrentUser().getPassword();
                String address = mAddressOne.getText().toString() + mAddressTwo.getText().toString();
                String country = mCountry.getSelectedItem().toString();
                String state = mState.getSelectedItem().toString();
                String city = mCity.getText().toString();
                String zip = mZip.getText().toString();
                EmployeeUser curUser = (EmployeeUser) model.getCurrentUser();
                curUser.setAddress(address);
                curUser.setCountry(country);
                curUser.setState(state);
                curUser.setCity(city);
                curUser.setZip(zip);
                createNewEmployeeUser(email, password);
            }
        });
    }

    public void createNewEmployeeUser(String email, String password) {
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Registered", model.getCurrentUser().toString());
                    String address = mAddressOne.getText().toString() + mAddressTwo.getText().toString();
                    String country = mCountry.getSelectedItem().toString();
                    String state = mState.getSelectedItem().toString();
                    String city = mCity.getText().toString();
                    String zip = mZip.getText().toString();
                    EmployeeUser curUser = (EmployeeUser) model.getCurrentUser();
                    curUser.setAddress(address);
                    curUser.setCountry(country);
                    curUser.setState(state);
                    curUser.setCity(city);
                    curUser.setZip(zip);
                    curUser.setEmployeeIDNum(mEmployeeID.getText().toString());
                    EmployeeUser newUser = new EmployeeUser(curUser.getFirstName(), curUser.getLastName(), curUser.getEmail(),
                            curUser.getPassword(), curUser.getPhoneNumber(), curUser.getSchoolName(), curUser.getEmployeeIDNum(), address,
                            country, state, state, zip);
                    model.setCurrentUser(newUser);
                    AddFirebaseUser(newUser);
                    Intent intent = new Intent(getBaseContext(), PlanChooseActivity.class);
                    startActivity(intent);
                } else {
                    Log.w("Failure", "Registration failure");
                    Log.e("Failure", task.getException().getMessage());

                }
            }
        });
    }

    private void AddFirebaseUser(EmployeeUser user) {
        db.collection("users").document(user.getEmail()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Main", "DocumentSnapshot added successfully");
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
        Editable employeeIDViewEditable = mEmployeeID.getText();
        final String employeeIDViewString = employeeIDViewEditable.toString();
        Editable addressViewEditable = mAddressOne.getText();
        final String addressViewString = addressViewEditable.toString();
        Editable cityViewEditable = mCity.getText();
        final String cityViewString = cityViewEditable.toString();
        Editable zipViewEditable = mZip.getText();
        final String zipViewString = zipViewEditable.toString();

        if (TextUtils.isEmpty(employeeIDViewString)) {
            mEmployeeID.setError("Required.");
            valid = false;
        } else {
            mEmployeeID.setError(null);
        }

        if (TextUtils.isEmpty(addressViewString)) {
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
