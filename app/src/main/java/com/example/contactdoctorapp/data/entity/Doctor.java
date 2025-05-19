package com.example.contactdoctorapp.data.entity;

import android.content.Context;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.contactdoctorapp.R;

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

    @Ignore
    public String specialtyDescription;

    public String toLocalizedString(Context context) {
        return context.getString(R.string.label_name, name) + "\n"
                + context.getString(R.string.label_phone, phone) + "\n"
                + context.getString(R.string.label_address, address) + "\n"
        + context.getString(R.string.label_specialty, specialtyDescription != null ? specialtyDescription : "");
    }


}
