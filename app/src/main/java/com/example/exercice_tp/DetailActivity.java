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

        DetailFragment fragment = new DetailFragment();

        String libelle = getIntent().getStringExtra("libelle");
/*      String details = getIntent().getStringExtra("details");*/

        // prepare fragment :
        Bundle bundle = new Bundle();
        bundle.putString("libelle", libelle);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        // transaction :
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerFragmentDetail, fragment, "detailFragment"); // TODO CHECK HERE IF DOES NOT WORK
        fragmentTransaction.commit();
    }
}