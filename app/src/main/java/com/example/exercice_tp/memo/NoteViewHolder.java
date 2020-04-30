package com.example.exercice_tp.memo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exercice_tp.DTO.NoteDTO;
import com.example.exercice_tp.DetailActivity;
import com.example.exercice_tp.DetailFragment;
import com.example.exercice_tp.R;
import com.example.exercice_tp.modeles.Note;

import java.util.List;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewLibelleNote;

    public NoteViewHolder(View itemView, final List<NoteDTO> listNotesDTO, final AppCompatActivity activity)
    {
        super(itemView);
        textViewLibelleNote = itemView.findViewById(R.id.libelle_note);
        textViewLibelleNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                NoteDTO noteDTO = listNotesDTO.get(getAdapterPosition());
                int orientation = view.getResources().getConfiguration().orientation;
                int screenSize = view.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

                if (orientation == Configuration.ORIENTATION_PORTRAIT && (screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL || screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL)) {
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    intent.putExtra("libelle", noteDTO.libelle);
                    view.getContext().startActivity(intent);
                } else {
                    DetailFragment fragment = new DetailFragment();
                    Bundle bundle = new Bundle();
                    fragment.setArguments(bundle);
                    bundle.putString("libelle", noteDTO.libelle);

                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerFragmentDetail, fragment, "detailFragment");
                    fragmentTransaction.commit();
                }
            }
        });

    }

}
