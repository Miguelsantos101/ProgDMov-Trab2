package com.example.contactdoctorapp.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Specialty {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String description;

    @NonNull
    @Override
    public String toString() {
        return description;
    }

}
