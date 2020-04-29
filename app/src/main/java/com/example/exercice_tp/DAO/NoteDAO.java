package com.example.exercice_tp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.exercice_tp.DTO.NoteDTO;

import java.util.List;

@Dao
public abstract class NoteDAO {
    @Query("SELECT * FROM notes")
    public abstract List<NoteDTO> getListeNotes();
    @Query("SELECT COUNT(*) FROM notes WHERE libelle = :libelle")
    public abstract long countNotesBtLibelle(String libelle);
    @Insert
    public abstract void insert(NoteDTO... notes);
    @Update
    public abstract void update(NoteDTO... notes);
    @Delete
    public abstract void delete(NoteDTO... notes);
}