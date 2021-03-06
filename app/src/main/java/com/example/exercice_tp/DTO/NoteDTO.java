package com.example.exercice_tp.DTO;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "notes")
public class NoteDTO {

    @PrimaryKey(autoGenerate = true)
    public long noteId = 0;

    
    @SerializedName("libelle")
    public String libelle;

    // Constructeur public vide (obligatoire si autre constructeur existant) :
    public NoteDTO() {}

    // Autre constructeur :
    public NoteDTO(String libelle)
    {
        this.libelle = libelle;
    }


}
