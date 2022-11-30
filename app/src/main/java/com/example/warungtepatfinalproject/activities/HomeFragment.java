package com.example.warungtepatfinalproject.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.warungtepatfinalproject.R;
import com.example.warungtepatfinalproject.adapter.ListProductAdapter;
import com.example.warungtepatfinalproject.enums.Apis;
import com.example.warungtepatfinalproject.utils.SharedPrefManager;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class HomeFragment extends Fragment {

    ProgressDialog progressDialog;
    GridView gridView;

    SearchView searchBox;
    String idProduk, namaProduk, harga, deskripsi, gambar, stok, status, idSupplier;

    SharedPrefManager sharedPrefManager;
    ArrayList<HashMap<String, ?>> listProduct;
    ListProductAdapter listProductAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        gridView = view.findViewById(R.id.listProduk);
        listProduct = new ArrayList<>();

        sharedPrefManager = new SharedPrefManager(getActivity());
        searchBox = view.findViewById(R.id.search_product);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String idProduk = (String) listProduct.get(i).get("idProduk");
                Log.d("Produk", "onItemClick: " + listProduct.get(i));
                Intent intent = new Intent(getActivity(), DetailProduct.class);
                intent.putExtra("idProduk", idProduk);
                startActivity(intent);
            }
        });
        getAllProduct();


        return view;
    }

    private void dialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void getAllProduct() {
        dialog();
        AndroidNetworking.initialize(getContext());
        AndroidNetworking.get(Apis.API_ALL_PRODUCT)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject res = response.getJSONObject(i);
                                Log.d("cek produk", "onResponse: " + res);
                                idProduk = res.getString("idProduk");
                                namaProduk = res.getString("namaProduk");
                                harga = formatRupiah(Double.parseDouble(res.getString("harga").replaceAll("[Rp. ]", " ")));
                                deskripsi = res.getString("deskripsi");
                                gambar = res.getString("gambar");
                                stok = Integer.toString(Integer.parseInt(res.getString("stok")));
                                idSupplier = res.getString("idSupplier");

                                sharedPrefManager.saveSPString(SharedPrefManager.SP_ID_PRODUK, idProduk);
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_ID_PRODUK, idSupplier);
                                HashMap<String, Object> listProducts = new HashMap<>();
                                listProducts.put("idProduk", idProduk);
                                listProducts.put("idSupplier", idSupplier);
                                listProducts.put("namaProduk", namaProduk);
                                listProducts.put("harga", harga);
                                listProducts.put("deskripsi", deskripsi);
                                listProducts.put("gambar", gambar);
                                listProducts.put("stok", stok);

                                listProduct.add(listProducts);

                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                            listProductAdapter = new ListProductAdapter(getActivity(), listProduct,
                                    R.layout.layout_rec_produk, new String[]{}, new int[]{});

                            gridView.setAdapter(listProductAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

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
