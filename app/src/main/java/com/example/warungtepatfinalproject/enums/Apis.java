package com.example.warungtepatfinalproject.enums;

public class Apis {
    public static final String BASE_URL_WARUNG_TEPAT = "http://192.168.83.108:8080";
    public static final String API_LOGIN = BASE_URL_WARUNG_TEPAT + "/login";
    public static final String API_DETAIL_AGEN = BASE_URL_WARUNG_TEPAT + "/nasabah/{nikKtp}";
    public static final String API_ALL_PRODUCT = BASE_URL_WARUNG_TEPAT + "/produk";
    public static final String API_GET_DETAIL_PRODUK = BASE_URL_WARUNG_TEPAT + "/produk/{idProduk}";
    public static final String API_PESAN_PRODUK = BASE_URL_WARUNG_TEPAT + "/transaksi";
    public static final String API_BAYAR_PRODUK = BASE_URL_WARUNG_TEPAT + "/transaksi/bayar";
    public static final String API_TRANSAKSI_AGEN = BASE_URL_WARUNG_TEPAT + "/transaksi/{nikKtp}";
    public static final String API_PESANAN_DITERIMA = BASE_URL_WARUNG_TEPAT + "/transaksi/diterima";

}
