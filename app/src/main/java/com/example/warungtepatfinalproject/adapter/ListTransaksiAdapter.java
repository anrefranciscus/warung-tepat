//package com.example.warungtepatfinalproject.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//
//import com.example.warungtepatfinalproject.R;
//import com.squareup.picasso.Picasso;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ListTransaksiAdapter extends SimpleAdapter {
//
//    private Context mContext;
//    private LayoutInflater inflater = null;
//
//
//    public ListTransaksiAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
//        super(context, data, resource, from, to);
//        mContext = context;
//        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
//        View view = convertView;
//
//        if (convertView == null) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksi_agen, parent, false);
//        }
//        TextView idTransaksi = view.findViewById(R.id.id_transaksi);
//        TextView id_supplier = view.findViewById(R.id.id_supplier);
//        TextView id_harga = view.findViewById(R.id.id_harga);
//        TextView id_kuantitas = view.findViewById(R.id.id_kuantitas);
//
//        HashMap<String, ?> data = (HashMap<String, ?>) getItem(position);
//        if (data != null) {
//            idTransaksi.setText((String) data.get("idProduk"));
//            id_supplier.setText((String) data.get("namaProduk"));
//            id_harga.setText((String) data.get("harga"));
//            id_kuantitas.setText((String) data.get("harga"));
////            Picasso.get().load((String) data.get("gambar")).into(gambarProduk);
//        }
//
//        return view;
//    }
//}
