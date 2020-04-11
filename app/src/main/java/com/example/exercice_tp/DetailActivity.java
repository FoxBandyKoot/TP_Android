package com.example.exercice_tp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // fragment :
        DetailFragment fragment = new DetailFragment();
        // fragment manager :
        FragmentManager fragmentManager = getSupportFragmentManager();
        // transaction :
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerFragmentDetail, fragment, "detailFragment");
        fragmentTransaction.commit();

    }
}
