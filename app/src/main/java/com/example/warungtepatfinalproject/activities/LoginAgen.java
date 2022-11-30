package com.example.warungtepatfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.warungtepatfinalproject.R;
import com.example.warungtepatfinalproject.enums.Apis;
import com.example.warungtepatfinalproject.enums.Params;
import com.example.warungtepatfinalproject.utils.SharedPrefManager;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginAgen extends AppCompatActivity {

    private ProgressDialog progressDialog;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        sharedPrefManager = new SharedPrefManager(this);
        TextInputEditText inputNomorHandphone = findViewById(R.id.inputNomorTelpAgen);
        TextInputEditText inputPassword = findViewById(R.id.inputPasswordAgen);
        Button btnLogin = findViewById(R.id.btnLoginAgen);

        btnLogin.setOnClickListener(view -> {
            String nomorTelepon = inputNomorHandphone.getText().toString();
            String password = inputPassword.getText().toString();

            if (nomorTelepon.isEmpty()) {
                inputNomorHandphone.setError("Silahkan Masukkan Nomor Telepon");
                inputNomorHandphone.requestFocus();
                showSnackbar(view, "Nomor Telepon & Password is required", Snackbar.LENGTH_SHORT);
                return;
            }
            if (password.isEmpty()) {
                inputPassword.setError("Silahkan Masukkan Password Anda");
                inputPassword.requestFocus();
                return;
            }

            if (!Patterns.PHONE.matcher(nomorTelepon).matches()){
                inputNomorHandphone.setError("Nomor Telepon Tidak Valid");
                return;
            }

            loginAgenWarungTepat(nomorTelepon, password);
        });

        if (sharedPrefManager.getSpStatusLogin()) {
            startActivity(new Intent(LoginAgen.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }


    public void showSnackbar(View view, String message, int lengthShort) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(Color.parseColor("#F57C00"))
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                .setAction("Oke", view1 -> {
                }).setActionTextColor(Color.parseColor("#FFFFFFFF"));
        snackbar.show();
    }
    public void loginAgenWarungTepat(String nomorTelepon, String password) {
        progressDialog = new ProgressDialog(LoginAgen.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.post(Apis.API_LOGIN)
                .addBodyParameter(Params.REQUEST_NO_HP, nomorTelepon)
                .addBodyParameter(Params.REQUEST_PASSWORD, password)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean status = Params.RESPONSE_STATUS;
                        try {
                            status = response.getBoolean("status");
                            if (status == Params.RESPONSE_STATUS) {
                                progressDialog.dismiss();
                                JSONObject payload = new JSONObject(response.getString("payload"));
                                payload.getInt("id");
                                String nikKtp = payload.getString("nikKtp");
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_NIK_KTP_AGEN, nikKtp);
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_STATUS_LOGIN, true);
                                startActivity(new Intent(LoginAgen.this, MainActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("Cek Response ", "Response Login : " + error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}