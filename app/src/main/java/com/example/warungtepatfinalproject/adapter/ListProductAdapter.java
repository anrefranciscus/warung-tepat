package com.example.warungtepatfinalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.warungtepatfinalproject.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListProductAdapter extends SimpleAdapter {

    private Context mContext;
    private LayoutInflater inflater = null;


    public ListProductAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        View view = convertView;

        if (convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rekomendasi_produk, parent, false);
        }
        TextView idProduk = view.findViewById(R.id.idProduk);
        TextView namaProduk = view.findViewById(R.id.textNamaProduk);
        TextView hargaProduk = view.findViewById(R.id.textHarga);
        ImageView gambarProduk = view.findViewById(R.id.gambar_produk);

        HashMap<String, ?> data = (HashMap<String, ?>) getItem(position);
        if (data != null){
            idProduk.setText((String) data.get("idProduk"));
            namaProduk.setText((String) data.get("namaProduk"));
            hargaProduk.setText((String) data.get("harga"));
            Picasso.get().load((String) data.get("gambar")).into(gambarProduk);
        }

        return view;
    }
}
