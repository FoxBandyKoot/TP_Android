package com.example.exercice_tp.memo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exercice_tp.DetailFragment;
import com.example.exercice_tp.R;
import com.example.exercice_tp.modeles.Note;

import java.util.Collections;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    // Liste d'objets métier :
    private List<Note> listeCourses;
    private Activity (AppCompatActivity) activity ;

    // Constructeur :
    public NotesAdapter(List<Note> listeCourses, AppCompatActivity activity)
    {
        this.activity = activity; // POUR LE LANDSCAPE
        this.listeCourses = listeCourses;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View viewCourse = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_item_liste, parent, false);

/*
        Switch switch
*/

        // fragment :
        DetailFragment fragment = new DetailFragment();

        /*Bundle bundle = new Bundle();
        bundle.putInt(DetailFragment.EXTRA_PARAMETER, 1234);
        fragment.setArguments(bundle);
*/
        // fragment manager :
        FragmentManager fragmentManager = activity.getSupportFragmentManager(); // IL FAUT ABSOLUMENT CETTE FONCTION ICI
        // transaction :
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerFragmentDetail, fragment, "detailFragment");
        fragmentTransaction.commit();

        return new NoteViewHolder(viewCourse);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position)
    {
        holder.textViewLibelleNote.setText(listeCourses.get(position).libelle);
    }

    @Override
    public int getItemCount()
    {
        return listeCourses.size();
    }

    // Appelé à chaque changement de position, pendant un déplacement.
    public boolean onItemMove(int positionDebut, int positionFin)
    {
        Collections.swap(listeCourses, positionDebut, positionFin);
        notifyItemMoved(positionDebut, positionFin);
        return true;
    }
    // Appelé une fois à la suppression.
    public void onItemDismiss(int position)
    {
        if (position > -1)
        {
            listeCourses.remove(position);
            notifyItemRemoved(position);
        }
    }

}
