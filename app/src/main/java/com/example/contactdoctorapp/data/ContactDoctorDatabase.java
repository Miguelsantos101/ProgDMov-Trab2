package com.example.contactdoctorapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.contactdoctorapp.data.dao.DoctorDao;
import com.example.contactdoctorapp.data.dao.SpecialtyDao;
import com.example.contactdoctorapp.data.entity.Doctor;
import com.example.contactdoctorapp.data.entity.Specialty;

@Database(entities = {Doctor.class, Specialty.class}, version = 1)
public abstract class ContactDoctorDatabase extends RoomDatabase {
    private static volatile ContactDoctorDatabase INSTANCE;

    public abstract DoctorDao doctorDao();
    public abstract SpecialtyDao specialtyDao();

    public static ContactDoctorDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ContactDoctorDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ContactDoctorDatabase.class,
                            "ContactDoctor"
                    ).allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
