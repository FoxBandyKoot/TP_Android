package com.example.exercice_tp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class DetailFragment extends Fragment {

    public DetailFragment() {
        // Required empty public constructor
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_detail, container, false);

        String libelle = getArguments().getString("libelle", "COUCOU");
        EditText libelleEditText = root.findViewById(R.id.libelle_fragment);
        libelleEditText.setText(libelle);

        /* DESCRIPTION
        TextView description = root.findViewById(R.id.description);
        description.setText(memoDescription);
        */
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }
}
