package com.example.warungtepatfinalproject.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.warungtepatfinalproject.R;

import com.example.warungtepatfinalproject.enums.Apis;
import com.example.warungtepatfinalproject.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class TransaksiFragment extends Fragment {
    ListView listView;
    SimpleAdapter adapter;

    String idTransaksi, namaProduk, namaSupplier, kuantitas, total, statusPesanan;
    ArrayList<HashMap<String, ?>> listTransaksi;

    ProgressDialog progressDialog;
    SharedPrefManager sharedPrefManager;
    TextView buttonTerimaPesanan;
    View buttonView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaksi_fragment, container, false);
        View buttonView = inflater.inflate(R.layout.list_transaksi_agen, null);
        listView = view.findViewById(R.id.listTransaksiAgen);

        buttonTerimaPesanan = buttonView.findViewById(R.id.btn_terima_pesanan);

        sharedPrefManager = new SharedPrefManager(getActivity());
        listTransaksi = new ArrayList<>();

        String nikKtp = sharedPrefManager.getSpNikKtpAgen();
        getTransaksiAgen(nikKtp);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //  String pos = listView.getAdapter().getItem(i).toString();
                String idTransaksi = sharedPrefManager.getSpIdTransaksi();
                Log.d("TEST", "onItemClick: " + idTransaksi);
//                pesananDiterima(idTransaksi);
            }
        });

        return view;
    }

    private void dialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void getTransaksiAgen(String ktp) {
        AndroidNetworking.initialize(getContext());
        AndroidNetworking.get(Apis.API_TRANSAKSI_AGEN)
                .addPathParameter("nikKtp", ktp)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject res = response.getJSONObject(i);
                                idTransaksi = res.getString("idTransaksi");
                                namaSupplier = res.getString("namaSupplier");
                                namaProduk = res.getString("namaProduk");
                                kuantitas = Integer.toString(Integer.parseInt(res.getString("kuantitas")));
                                total = formatRupiah(Double.parseDouble(res.getString("total").replaceAll("[Rp. ]", " ")));
                                statusPesanan = res.getString("statusPesanan");

                                HashMap<String, Object> transaksi = new HashMap<>();
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_ID_TRANSAKSI, idTransaksi);
                                transaksi.put("idTransaksi", idTransaksi);
                                transaksi.put("namaSupplier", namaSupplier);
                                transaksi.put("namaProduk", namaProduk);
                                transaksi.put("kuantitas", kuantitas);
                                transaksi.put("total", formatRupiah(Double.parseDouble(total)));
                                transaksi.put("statusPesanan", statusPesanan);
                                listTransaksi.add(transaksi);

                            }

                            adapter = new SimpleAdapter(getActivity(), listTransaksi,
                                    R.layout.list_transaksi_agen,
                                    new String[]{"idTransaksi", "namaSupplier", "namaProduk", "kuantitas", "total", "statusPesanan"}, new int[]{R.id.id_transaksi, R.id.nama_supplier, R.id.nama_produk, R.id.kuantitas, R.id.jumlah_total, R.id.btn_terima_pesanan});
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        System.out.println(error.getMessage());
                    }
                });
    }

    public void pesananDiterima(String idTransaksi) {
        AndroidNetworking.initialize(getActivity());
        AndroidNetworking.put(Apis.API_PESANAN_DITERIMA)
                .addBodyParameter("idTransaksi", idTransaksi)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("saldo berkurang", "onResponse: " + response);
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("log Error", "onError: " + error.getMessage());
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
