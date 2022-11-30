package com.example.warungtepatfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.warungtepatfinalproject.R;
import com.example.warungtepatfinalproject.enums.Apis;
import com.example.warungtepatfinalproject.utils.SharedPrefManager;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

public class BayarProduk extends AppCompatActivity {


    SharedPrefManager sharedPrefManager;
    TextView textIdTransaksi, textIdProduk, textHarga, textQuantitas, textTotal;
    Button bayarProduk;
    String idTransaksi;
    String idProduk;
    String harga;
    int kuantitas;
    String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_produk);

        sharedPrefManager = new SharedPrefManager(this);

        textIdTransaksi = findViewById(R.id.idTransaksi);
        textIdProduk = findViewById(R.id.id_produk_bayar);
        textTotal = findViewById(R.id.total_harga);
        textHarga = findViewById(R.id.harga_produk);
        textQuantitas = findViewById(R.id.jumlah_kuantitas);
        bayarProduk = findViewById(R.id.btn_konfirmasi_bayar_produk);

        Intent intent = getIntent();
        idTransaksi = intent.getStringExtra("idTransaksi");
        idProduk = intent.getStringExtra("idProduk");
        harga = intent.getStringExtra("harga").replaceAll("[Rp. ]", " ");
        kuantitas = Integer.parseInt(intent.getStringExtra("kuantitas"));
        total = formatRupiah(Double.parseDouble(intent.getStringExtra("total").replaceAll("[Rp. ]", " ")));

        Log.d("idTransaksi ", "onCreate: " + idTransaksi);
        Log.d("idProduk ", "onCreate: " + idProduk);
        Log.d("harga", "onCreate: " + harga);
        Log.d("kuantitas", "onCreate: " + kuantitas);
        Log.d("total ", "onCreate: " + total);

        textIdTransaksi.setText(idTransaksi);
        textIdProduk.setText(idProduk);
        textHarga.setText(harga);
        textQuantitas.setText(kuantitas);
        textTotal.setText(total);

        bayarProduk.setOnClickListener(view -> {
            konfirmasiBayarProduk(idTransaksi);
        });

    }

    public void konfirmasiBayarProduk(String Idtransaksi) {
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.put(Apis.API_BAYAR_PRODUK)
                .addBodyParameter("idTransaksi", Idtransaksi)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", "onResponse: halooo" + response);
//                        startActivity(new Intent(this, TransaksiFragment.class));
//                        getSupportFragmentManager().beginTransaction().replace(R.id.listTransaksiAgen, new TransaksiFragment()).commit();
//
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println(anError.getMessage());
                    }
                });
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
}