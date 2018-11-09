package com.example.mmcke.campuscoupons.controllers;

import android.content.Intent;
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
import com.example.mmcke.campuscoupons.model.StudentUser;
import com.example.mmcke.campuscoupons.model.ParentUser;
import com.google.firebase.auth.FirebaseAuth;
import com.example.mmcke.campuscoupons.model.School;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static java.lang.String.valueOf;

public class RegisterActivity extends AppCompatActivity {

    private final Model model = Model.getInstance();
    private FirebaseAuth mAuth;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPhone;
    private EditText mPass;
    private EditText mConfirmPass;
    private Spinner mSchools;
    private final ArrayList<String> schools = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mEmail = findViewById(R.id.email);
        mPass = findViewById(R.id.password);
        mConfirmPass = findViewById(R.id.confirmPassword);
        mPhone = findViewById(R.id.phoneNumber);
        mSchools = findViewById(R.id.schoolList);

        this.readEnumSchools();

        final ArrayAdapter<String> schoolAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, schools);
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSchools.setAdapter(schoolAdapter);

        Button ParentRegister = findViewById(R.id.parentButton);
        ParentRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable fNameEditable = mFirstName.getText();
                String fName = fNameEditable.toString();
                Editable lNameEditable = mLastName.getText();
                String lName = lNameEditable.toString();
                Editable emailEditable = mEmail.getText();
                String email = emailEditable.toString();
                Editable phoneEditable = mPhone.getText();
                String phone = phoneEditable.toString();
                Editable passEditable = mPass.getText();
                String pass = passEditable.toString();
                Editable passConfEditable = mConfirmPass.getText();
                String confirmPass = passConfEditable.toString();
                String school = mSchools.getSelectedItem().toString();

                if (!pass.equals(confirmPass)) {
                    mPass.setError("Passwords must match");
                } else {
                    createNewParent(fName, lName, email, pass, phone, school);
                }
            }
        });

        Button StudentRegister = findViewById(R.id.studentButton);
        StudentRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable fNameEditable = mFirstName.getText();
                String fName = fNameEditable.toString();
                Editable lNameEditable = mLastName.getText();
                String lName = lNameEditable.toString();
                Editable emailEditable = mEmail.getText();
                String email = emailEditable.toString();
                Editable phoneEditable = mPhone.getText();
                String phone = phoneEditable.toString();
                Editable passEditable = mPass.getText();
                String pass = passEditable.toString();
                Editable passConfEditable = mConfirmPass.getText();
                String confirmPass = passConfEditable.toString();
                String school = mSchools.getSelectedItem().toString();

                if (!pass.equals(confirmPass)) {
                    mPass.setError("Passwords must match");
                } else {
                    createNewStudent(fName, lName, email, pass, phone, school);
                }
            }
        });

        Button EmployeeRegister = findViewById(R.id.employeeButton);
        EmployeeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable fNameEditable = mFirstName.getText();
                String fName = fNameEditable.toString();
                Editable lNameEditable = mLastName.getText();
                String lName = lNameEditable.toString();
                Editable emailEditable = mEmail.getText();
                String email = emailEditable.toString();
                Editable phoneEditable = mPhone.getText();
                String phone = phoneEditable.toString();
                Editable passEditable = mPass.getText();
                String pass = passEditable.toString();
                Editable passConfEditable = mConfirmPass.getText();
                String confirmPass = passConfEditable.toString();
                String school = mSchools.getSelectedItem().toString();

                if (!pass.equals(confirmPass)) {
                    mPass.setError("Passwords must match");
                } else {
                    createNewEmployee(fName, lName, email, pass, phone, school);
                }
            }
        });
    }

    public void createNewParent(String fName, String lName, String email, String password, String phone, String school) {
        if (!validateForm()) {
            return;
        }
        ParentUser curUser = new ParentUser(fName, lName, email, password, phone, school, "", "",
                "", "", "", "", "", "", "");
        model.setCurrentUser(curUser);
        Log.d("Register", curUser.toString());
        Intent intent = new Intent(getBaseContext(), ParentRegisterActivity.class);
        startActivity(intent);
    }

    public void createNewStudent(String fName, String lName, String email, String password, String phone, String school) {
        if (!validateForm()) {
            return;
        }
        StudentUser curUser = new StudentUser(fName, lName, email, password, phone, school, true, "",
                "", "", "", "", "");
        model.setCurrentUser(curUser);
        Log.d("Register", curUser.toString());
        Intent intent = new Intent(getBaseContext(), StudentRegisterActivity.class);
        startActivity(intent);
    }

    public void createNewEmployee(String fName, String lName, String email, String password, String phone, String school) {
        if (!validateForm()) {
            return;
        }
        EmployeeUser curUser = new EmployeeUser(fName, lName, email, password, phone, school, "",
                "", "", "", "", "");
        model.setCurrentUser(curUser);
        Log.d("Register", curUser.toString());
        Intent intent = new Intent(getBaseContext(), EmployeeRegisterActivity.class);
        startActivity(intent);
    }

    private boolean validateForm() {
        boolean valid = true;
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

    public void readEnumSchools() {
        for (School school: School.values()) {
            schools.add(school.getTitle());
        }
    }

    public void readSchools() {
        try {
            InputStream is = getResources().openRawResource(R.raw.schools);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            br.readLine();
            String line = br.readLine();

            final String[] tokens = line.split("#");
            for (int i = 0; i < 1; i++) {
                String curSchool = tokens[i];
                //schools.add(curSchool);
            }
            br.close();
        } catch (IOException e) {
            Log.e("Main", "error reading assets", e);
        }
    }

    public static <School extends Enum<School>> School getEnumFromString (Class<School> c, String string) {
        if (c != null && string != null) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {

            }
        }
        return null;
    }
}
