package com.example.contactdoctorapp.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Specialty.class,
                parentColumns = "id",
                childColumns = "specialtyId",
                onDelete = ForeignKey.RESTRICT
        )
)
public class Doctor {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int specialtyId;

    public String name;

    public String phone;

    public String address;

    @NonNull
    @Override
    public String toString() {
        return "Name: " + name + "\nPhone number: " + phone + "\nAddress: " + address;
    }

}
