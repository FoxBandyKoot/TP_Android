package com.example.exercice_tp.memo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exercice_tp.DAO.AppDatabaseHelper;
import com.example.exercice_tp.DTO.NoteDTO;
import com.example.exercice_tp.R;
/*
import com.example.exercice_tp.modeles.Note;
*/

import java.util.Collections;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {


    private static final String TAG = NotesAdapter.class.getName();
    private final AppCompatActivity activity;

    // Liste d'objets métier :
    private List<NoteDTO> listeNotes;

    /* private Activity (AppCompatActivity) activity ; */

    // Constructeur :
    public NotesAdapter(List<NoteDTO> listeCourses, AppCompatActivity activity)
    {
        this.activity = activity; // POUR LE LANDSCAPE
        this.listeNotes = listeCourses;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View viewCourse = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_items_liste, parent, false);

/*
        Switch switch

        // fragment :
/*      DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);

        // fragment manager :
        FragmentManager fragmentManager = activity.getSupportFragmentManager(); // TODO -> IL FAUT ABSOLUMENT CETTE FONCTION ICI
        // transaction :
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerFragmentDetail, fragment, "detailFragment");
        fragmentTransaction.commit();
*/

        return new NoteViewHolder(viewCourse);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position)
    {
        holder.textViewLibelleNote.setText(listeNotes.get(position).libelle);
    }

    @Override
    public int getItemCount()
    {
        return listeNotes.size();
    }

    // Appelé à chaque changement de position, pendant un déplacement.
    public boolean onItemMove(int positionDebut, int positionFin)
    {
        Collections.swap(listeNotes, positionDebut, positionFin);
        notifyItemMoved(positionDebut, positionFin);
        return true;
    }

    // Appelé une fois à la suppression.
    public void
    onItemDismiss(int position)
    {
        NoteDTO noteDTO = listeNotes.get(position);
        AppDatabaseHelper.getDatabase(activity).noteDAO().delete(noteDTO);

        if (position > -1)
        {
            listeNotes.remove(position);
            notifyItemRemoved(position);
        }
    }

}
