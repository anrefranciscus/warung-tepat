
package com.example.warungtepatfinalproject.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.warungtepatfinalproject.R;
import com.example.warungtepatfinalproject.enums.Apis;
import com.example.warungtepatfinalproject.utils.SharedPrefManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailProduct extends AppCompatActivity {

    ProgressDialog progressDialog;
    SharedPrefManager sharedPrefManager;
    String idSupplier, idProd, namaProduk, harga, deskripsi, gambar, stok, idTransaksi, quantitas, total, hargaTotal, nikKtp;
    TextView detailNamaProduk, detailDeskripsiProduk, detailHargaProduk, textQuantity, detailStok;
    Button tambahQuantity, kurangQuantity, beliProduk;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sharedPrefManager = new SharedPrefManager(this);
        detailNamaProduk = findViewById(R.id.detailNamaProduk);
        detailDeskripsiProduk = findViewById(R.id.detailDeskripsiProduk);
        detailHargaProduk = findViewById(R.id.detailHargaProduk);
        detailStok = findViewById(R.id.detail_stok);
        tambahQuantity = findViewById(R.id.btnTambahQuantity);
        kurangQuantity = findViewById(R.id.btnKurangQuantity);
        textQuantity = findViewById(R.id.text_kuantitas);
        beliProduk = findViewById(R.id.pesanProduk);
        imageView = findViewById(R.id.gambarProduk);

        getDetailProduct();
        tambahQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tambahQuantity = textQuantity.getText().toString();
                int quantity = Integer.parseInt(tambahQuantity);
                quantity++;
                textQuantity.setText(String.valueOf(quantity));
            }
        });

        kurangQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tambahQuantity = textQuantity.getText().toString();
                int quantity = Integer.parseInt(tambahQuantity);
                quantity--;
                textQuantity.setText(String.valueOf(quantity));
            }
        });


        beliProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kuantitas = textQuantity.getText().toString();

                nikKtp = sharedPrefManager.getSpNikKtpAgen();
                idSupplier = sharedPrefManager.getSpIdSupplier();
                Log.d("idSupplier", "onClick: " + idSupplier);
                beliProduk(idProd, idSupplier, nikKtp, harga, kuantitas);
            }
        });

    }

    private void dialog() {
        progressDialog = new ProgressDialog(DetailProduct.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void getDetailProduct() {
        dialog();
        Intent intent = getIntent();
        String idProduk = intent.getStringExtra("idProduk");

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(Apis.API_GET_DETAIL_PRODUK)
                .addPathParameter("idProduk", idProduk)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            idProd = response.getString("idProduk");
                            namaProduk = response.getString("namaProduk");
                            harga = formatRupiah(Double.parseDouble(response.getString("harga").replaceAll("[Rp. ]", " ")));
                            deskripsi = response.getString("deskripsi");
                            gambar = response.getString("gambar");
                            stok = Integer.toString(Integer.parseInt(response.getString("stok")));
                            idSupplier = response.getString("idSupplier");

                            sharedPrefManager.saveSPString(SharedPrefManager.SP_ID_SUPPLIER, idSupplier);
                            detailNamaProduk.setText(namaProduk);
                            detailDeskripsiProduk.setText(deskripsi);
                            detailHargaProduk.setText(harga);
                            detailStok.setText(stok);
                            Picasso.get().load(gambar).into(imageView);
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Error Exeception", "onResponse: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error  ", "onError: " + anError.getMessage());
                    }
                });
    }

    public void beliProduk(String idProduk, String idSupplier, String nikKtp, String harga, String kuantitas) {

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.post(Apis.API_PESAN_PRODUK)
                .addBodyParameter("idProduk", idProduk)
                .addBodyParameter("idSupplier", idSupplier)
                .addBodyParameter("nikKtp", nikKtp)
                .addBodyParameter("harga", harga)
                .addBodyParameter("kuantitas", kuantitas)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("beli produk", "produk: " + response);
                        try {

                            JSONObject payload = response.getJSONObject("payload");
                            idTransaksi = payload.getString("idTransaksi");
                            quantitas = Integer.toString(Integer.parseInt(payload.getString("kuantitas")));
                            hargaTotal = formatRupiah(Double.parseDouble(payload.getString("harga").replaceAll("[Rp. ]", " ")));
                            total = formatRupiah(Double.parseDouble(payload.getString("total").replaceAll("[Rp. ]", " ")));

                            Intent intent = new Intent(getApplicationContext(), BayarProduk.class);
                            intent.putExtra("idSupplier", idSupplier);
                            intent.putExtra("idProduk", idProd);
                            intent.putExtra("idTransaksi", idTransaksi);
                            intent.putExtra("kuantitas", quantitas);
                            intent.putExtra("harga", hargaTotal);
                            intent.putExtra("total", total);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println(anError.getMessage());
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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