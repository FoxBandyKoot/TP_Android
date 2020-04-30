package com.example.exercice_tp.memo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exercice_tp.DAO.AppDatabaseHelper;
import com.example.exercice_tp.DTO.NoteDTO;
import com.example.exercice_tp.DetailActivity;
import com.example.exercice_tp.DetailFragment;
import com.example.exercice_tp.R;
/*
import com.example.exercice_tp.modeles.Note;
*/

import java.util.Collections;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder>  {

    private static final String TAG = NotesAdapter.class.getName();
    private final AppCompatActivity activity;
    private List<NoteDTO> listNotesDTO;

    /* private Activity (AppCompatActivity) activity ; */

    // Constructeur :
    public NotesAdapter(List<NoteDTO> listNotesDTO, AppCompatActivity activity)
    {
        this.activity = activity; // FOR LANDSCAPE
        this.listNotesDTO = listNotesDTO;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_items_liste, parent, false);

        NoteDTO noteDTO = listNotesDTO.get(0);
        int orientation = view.getResources().getConfiguration().orientation;
        int screenSize = view.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        if (orientation == Configuration.ORIENTATION_PORTRAIT && (screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL || screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL)) {
        } else {
                    DetailFragment fragment = new DetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("libelle", noteDTO.libelle);
                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerFragmentDetail, fragment, "detailFragment");
                    fragmentTransaction.commit();
        }
        return new NoteViewHolder(view, listNotesDTO, activity);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position)
    {
        holder.textViewLibelleNote.setText(listNotesDTO.get(position).libelle);
    }

    @Override
    public int getItemCount()
    {
        return listNotesDTO.size();
    }

    // Appelé à chaque changement de position, pendant un déplacement.
    public boolean onItemMove(int positionDebut, int positionFin)
    {
        Collections.swap(listNotesDTO, positionDebut, positionFin);
        notifyItemMoved(positionDebut, positionFin);
        return true;
    }

    // Appelé une fois à la suppression.
    public void
    onItemDismiss(int position)
    {
        NoteDTO noteDTO = listNotesDTO.get(position);
        AppDatabaseHelper.getDatabase(activity).noteDAO().delete(noteDTO);

        if (position > -1)
        {
            listNotesDTO.remove(position);
            notifyItemRemoved(position);
        }
    }

}
