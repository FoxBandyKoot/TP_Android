package com.example.exercice_tp.DAO;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.exercice_tp.DTO.NoteDTO;

@Database(entities = {NoteDTO.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract NoteDAO noteDAO();

}