package com.example.mmcke.campuscoupons.controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.mmcke.campuscoupons.R;
import com.example.mmcke.campuscoupons.model.EmployeeUser;
import com.example.mmcke.campuscoupons.model.Model;
import com.example.mmcke.campuscoupons.model.ParentUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PaymentActivity extends AppCompatActivity {

    private EditText cardName;
    private EditText cardNumber;
    private EditText expirationDate;
    private EditText crn;
    private CheckBox agreeCheck;

    private final Model model = Model.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        cardName = findViewById(R.id.cardNameEdit);
        cardNumber = findViewById(R.id.cardNumberEdit);
        expirationDate = findViewById(R.id.expirationDateEdit);
        crn = findViewById(R.id.crnEdit);
        agreeCheck = findViewById(R.id.checkBoxAgreement);

        Button BackButton = findViewById(R.id.backButton);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PlanChooseActivity.class);
                startActivity(intent);
            }
        });

        Button SubmitButton = findViewById(R.id.submitButton);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!agreeCheck.isChecked()) {
                    agreeCheck.setError("Must agree to terms and conditions.");
                } else if (model.getCurrentUser() instanceof ParentUser) {
                    updateParentUser((ParentUser)model.getCurrentUser());
                } else {
                    updateEmployeeUser((EmployeeUser)model.getCurrentUser());
                }
            }
        });

    }

    public void updateParentUser(ParentUser user) {
        if (!validateForm()) {
            return;
        }
        user.setCardName(cardName.getText().toString());
        user.setCardNum(cardNumber.getText().toString());
        user.setExpirationDate(expirationDate.getText().toString());
        user.setCrn(Integer.parseInt(crn.getText().toString()));
        DocumentReference userSnap = db.collection("parentUsers").document(user.getEmail());
        userSnap.update("crn", user.getCrn(), "cardName", user.getCardName(), "cardNum", user.getCardNum(),
                "expirationDate", user.getExpirationDate()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Main", "DocumentSnapshot successfully updated.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Main", "Error updating DocumentSnapshot");
            }
        });
        model.setCurrentUser(user);
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public void updateEmployeeUser(EmployeeUser user) {
        if (!validateForm()) {
            return;
        }
        user.setCardName(cardName.getText().toString());
        user.setCardNum(cardNumber.getText().toString());
        user.setExpirationDate(expirationDate.getText().toString());
        user.setCrn(Integer.parseInt(crn.getText().toString()));
        DocumentReference userSnap = db.collection("employeeUsers").document(user.getEmail());
        userSnap.update("crn", user.getCrn(), "cardName", user.getCardName(), "cardNum", user.getCardNum(),
                "expirationDate", user.getExpirationDate()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Main", "DocumentSnapshot successfully updated.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Main", "Error updating DocumentSnapshot");
            }
        });
        model.setCurrentUser(user);
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public boolean validateForm() {
        boolean valid = true;
        Editable cardNameEditable = cardName.getText();
        final String cardNameView = cardNameEditable.toString();
        Editable cardNumberEditable = cardNumber.getText();
        final String cardNumberView = cardNumberEditable.toString();
        Editable expirationDateEditable = expirationDate.getText();
        final String expirationDateView = expirationDateEditable.toString();
        Editable crnEditable = crn.getText();
        final String crnView = crnEditable.toString();

        if (TextUtils.isEmpty(cardNameView)) {
            cardName.setError("Required.");
            valid = false;
        } else {
            cardName.setError(null);
        }

        if (TextUtils.isEmpty(cardNumberView)) {
            cardNumber.setError("Required.");
            valid = false;
        } else {
            cardNumber.setError(null);
        }

        if (TextUtils.isEmpty(expirationDateView)) {
            expirationDate.setError("Required.");
            valid = false;
        } else {
            expirationDate.setError(null);
        }

        if (TextUtils.isEmpty(crnView)) {
            crn.setError("Required.");
            valid = false;
        } else {
            crn.setError(null);
        }

        return valid;
    }
}
