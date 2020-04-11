package com.example.exercice_tp.memo;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.exercice_tp.R;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    // TextView intitulé note :
    public TextView textViewLibelleNote;

    // Constructeur :
    public NoteViewHolder(View itemView)
    {
        super(itemView);
        textViewLibelleNote = itemView.findViewById(R.id.libelle_notes);
    }

}
