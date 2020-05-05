package com.example.exercice_tp.memo;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Ce fichier gère les intéractions du tactile
 */
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private NotesAdapter adapter;
    // Constructeur.
    public ItemTouchHelperCallback(NotesAdapter adapter)
    {
        this.adapter = adapter;
    }
    @Override
    public boolean isLongPressDragEnabled() { return true; }
    @Override
    public boolean isItemViewSwipeEnabled() { return true; }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
    {
        int dragFlagsUpDown = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int dragFlagsLeftRight = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlagsUpDown, dragFlagsLeftRight);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target)
    {
        adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
        adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }


}
