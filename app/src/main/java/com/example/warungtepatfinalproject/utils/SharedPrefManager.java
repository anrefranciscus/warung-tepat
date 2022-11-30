package com.example.warungtepatfinalproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor spEditor;

    public static final String SHARED_PREF_NAME = "spAgenWarungTepat";
    public static final String SP_ID_PRODUK = "idProduk";
    public static final String SP_ID_SUPPLIER = "spID";
    public static final String SP_STATUS_LOGIN = "spIsAlreadyLogin";
    public static final String SP_ID_AGEN = "id";
    public static final String SP_NAMA_AGEN = "nama";
    public static final String SP_NIK_KTP_AGEN = "nikKTP";
    public static final String SP_EMAIL_AGEN = "email";
    public static final String SP_PASSWORD = "password";
    public static final String SP_NO_HP_AGEN = "noHP";
    public static final String SP_PEKERJAAN_AGEN = "pekerjaan";
    public static final String SP_ALAMAT_AGEN = "alamat";
    public static final String SALDO_AGEN = "saldo";
    public static final String NOMOR_REKENING_AGEN = "nomorRekening";
    public static final String SP_NAMA_PRODUK = "namaProduk";
    public static final String SP_HARGA_PRODUK = "hargaProduk";
    public static final String SP_DESKRIPSI_PRODUK = "deskripsiProduk";
    public static final String SP_GAMBAR_PRODUK = "gambar";
    public static final String SP_STOK_PRODUK = "stokProduk";
    public static final String SP_QUANTITAS = "kuantitasProduk";
    public static final String SP_ID_TRANSAKSI = "idTransaksi";
    public static final String SP_HARGA_TOTAL = "hargaTotal";
    public static final String SP_TOTAL = "total";

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        spEditor = sharedPreferences.edit();
    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public void saveInt(String keySP, int value) {
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public String getSpIdSupplier() {
        return sharedPreferences.getString(SP_ID_SUPPLIER, "");
    }
//
//    public String getIdProduk() {
//        return sharedPreferences.getString(SP_ID_PRODUK, "");
//    }

    public String getSPNamaAgen() {
        return sharedPreferences.getString(SP_NAMA_AGEN, "");
    }

    public String getSpNikKtpAgen() {
        return sharedPreferences.getString(SP_NIK_KTP_AGEN, "");
    }

    public String getSpEmailAgen() {
        return sharedPreferences.getString(SP_EMAIL_AGEN, "");
    }

    public String getSpNoHpAgen() {
        return sharedPreferences.getString(SP_NO_HP_AGEN, "");
    }

    public String getSpAlamatAgen() {
        return sharedPreferences.getString(SP_ALAMAT_AGEN, "");
    }

    public String getSaldoAgen() {
        return sharedPreferences.getString(SALDO_AGEN, "");
    }

    public String getNomorRekeningAgen() {
        return sharedPreferences.getString(NOMOR_REKENING_AGEN, "");
    }

    public String getSpPekerjaanAgen() {
        return sharedPreferences.getString(SP_PEKERJAAN_AGEN, "");
    }

//    public String getSpNamaProduk() {
//        return sharedPreferences.getString(SP_NAMA_PRODUK, "");
//    }
//
//    public String getSpHargaProduk() {
//        return sharedPreferences.getString(SP_HARGA_PRODUK, "");
//    }
//
//    public String getSpDeskripsiProduk() {
//        return sharedPreferences.getString(SP_DESKRIPSI_PRODUK, "");
//    }
//
//    public String getSpGambarProduk() {
//        return sharedPreferences.getString(SP_GAMBAR_PRODUK, "");
//    }
//
//    public String getSpStokProduk() {
//        return sharedPreferences.getString(SP_STOK_PRODUK, "");
//    }


    public String getSpIdTransaksi() {
        return sharedPreferences.getString(SP_ID_TRANSAKSI, "");
    }
//
//    public String getSpQuantitas() {
//        return sharedPreferences.getString(SP_QUANTITAS, "");
//    }
//
//    public String getHargaTotal() {
//        return sharedPreferences.getString(SP_HARGA_TOTAL, "");
//    }
//
//    public String getSpTotal() {
//        return sharedPreferences.getString(SP_TOTAL, "");
//    }

    public Boolean getSpStatusLogin() {
        return sharedPreferences.getBoolean(SP_STATUS_LOGIN, false);
    }

    public void removeData() {
        spEditor.clear();
        spEditor.commit();
    }
}
