package com.example.warungtepatfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.warungtepatfinalproject.R;
import com.example.warungtepatfinalproject.utils.SharedPrefManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginSupplier extends AppCompatActivity {



    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_supplier);
        getSupportActionBar().hide();


        TextInputEditText textInputNoTelp = findViewById(R.id.inputNomorTelp);
        TextInputEditText textInputPassword = findViewById(R.id.inputPassword);
        Button btnMasuk = findViewById(R.id.btn_masuk);


        btnMasuk.setOnClickListener(view -> {
//            String nomorTelepon = textInputNoTelp.getText().toString();
//            String password = textInputPassword.getText().toString();
//
//            if (nomorTelepon.isEmpty() || password.isEmpty()) {
//                showSnackbar(view, "Email & Password is Required", Snackbar.LENGTH_SHORT);
//            } else {
//                supplierLoginData(nomorTelepon, password);
//                Log.d("Nomor Telepon : ", "Nomor Telepon : " + nomorTelepon);
//                Log.d("Password : ", "Password : " + password);
//            }
        });

    }

    private void showSnackbar(View view, String message, int duration) {

        final Snackbar snackBar = Snackbar.make(view, message, duration);
        snackBar.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }
}