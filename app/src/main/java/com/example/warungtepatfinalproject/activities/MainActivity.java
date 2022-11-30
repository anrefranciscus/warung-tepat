package com.example.warungtepatfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.warungtepatfinalproject.R;
import com.example.warungtepatfinalproject.databinding.ActivityMainBinding;
import com.example.warungtepatfinalproject.utils.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        sharedPrefManager = new SharedPrefManager(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.beranda:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.akun:
                    replaceFragment(new AkunFragment());
                    break;
                case R.id.transaksi:
                    replaceFragment(new TransaksiFragment());
                    break;
            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}