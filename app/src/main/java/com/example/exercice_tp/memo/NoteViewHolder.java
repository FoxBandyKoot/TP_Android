package com.example.exercice_tp.memo;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exercice_tp.DTO.NoteDTO;
import com.example.exercice_tp.DetailActivity;
import com.example.exercice_tp.R;

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

                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    intent.putExtra("libelle", noteDTO.libelle);
                    view.getContext().startActivity(intent);
            }
        });
    }

}
