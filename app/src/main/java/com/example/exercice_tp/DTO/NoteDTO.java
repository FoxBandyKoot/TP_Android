package com.example.exercice_tp.DTO;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteDTO {

    @PrimaryKey(autoGenerate = true)
    public long noteId = 0;

    public String libelle;
    // Exemple d'attribut non pris en compte par Room :

    public boolean selectionne;
    
    // Constructeur public vide (obligatoire si autre constructeur existant) :
    public NoteDTO() {}

    // Autre constructeur :
    public NoteDTO(String libelle)
    {
        this.libelle = libelle;
    }


}
