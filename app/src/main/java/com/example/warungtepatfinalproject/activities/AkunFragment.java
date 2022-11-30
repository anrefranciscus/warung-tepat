package com.example.warungtepatfinalproject.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.warungtepatfinalproject.R;
import com.example.warungtepatfinalproject.enums.Apis;
import com.example.warungtepatfinalproject.utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

public class AkunFragment extends Fragment {

    SharedPrefManager sharedPrefManager;

    TextView textNama, textEmail, texAlamat, textSaldo, textNomorRekening, textNik, textPekerjaan, textNomorTelepon;
    ImageView btnLogOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.akun_fragment, container, false);

        sharedPrefManager = new SharedPrefManager(getContext());


        textNama = view.findViewById(R.id.textNamaAgen);
        textEmail = view.findViewById(R.id.textEmail);
        texAlamat = view.findViewById(R.id.tv_address);
        textSaldo = view.findViewById(R.id.textSaldo);
        textNomorRekening = view.findViewById(R.id.textNomorRekening);
        textNik = view.findViewById(R.id.textNIK);
        textPekerjaan = view.findViewById(R.id.textPekerjaan);
        textNomorTelepon = view.findViewById(R.id.textNomorTelepon);
        btnLogOut = view.findViewById(R.id.btnLogout);

        getDetailAgen();
        textNik.setText(sharedPrefManager.getSpNikKtpAgen());

        btnLogOut.setOnClickListener(view1 -> {
            onCreateDialog();
        });


        return view;
    }


    public void getDetailAgen() {
        System.out.println(sharedPrefManager.getSpNikKtpAgen());
        AndroidNetworking.initialize(getContext());
        AndroidNetworking.get(Apis.API_DETAIL_AGEN)
                .addPathParameter("nikKtp", sharedPrefManager.getSpNikKtpAgen())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("response" + response);
                        try {
                            JSONObject payload = response.getJSONObject("payload");
                            String namaAgen = payload.getString("nama");
                            String nikKtp = payload.getString("nikKtp");
                            String email = payload.getString("email");
                            String nomorHp = payload.getString("noHP");
                            String pekerjaan = payload.getString("pekerjaan");
                            String alamat = payload.getString("alamat");
                            String saldo = payload.getString("saldo");
                            String nomorRekening = payload.getString("noRekening");

                            textNama.setText(namaAgen);
                            textEmail.setText(email);
                            texAlamat.setText(alamat);
                            textSaldo.setText(saldo);
                            textNomorRekening.setText(nomorRekening);
                            textPekerjaan.setText(pekerjaan);
                            textNomorTelepon.setText(nomorHp);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("Cek Response ", "Response Login : " + error.getMessage());
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onCreateDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Logout");

        alertDialog.setMessage("Apakah Anda Ingin Keluar ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        logOut();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();

    }

    private String formatRupiah(Double number) {
        Locale locale = new Locale("IND", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        String formatRupiah = numberFormat.format(number);
        String[] split = formatRupiah.split(",");
        int length = split[0].length();
        String formatRupiahString = split[0].substring(0, 2) + " " + split[0].substring(2, length);
        return formatRupiahString;
    }

    public void logOut() {
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_STATUS_LOGIN, false);
        sharedPrefManager.removeData();
        startActivity(new Intent(getActivity(), LoginAgen.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    }


}

