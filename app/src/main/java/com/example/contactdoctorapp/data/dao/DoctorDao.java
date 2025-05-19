package com.example.contactdoctorapp.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contactdoctorapp.data.entity.Doctor;

import java.util.List;

@Dao
public interface DoctorDao {
    @Insert
    void insert(Doctor doctor);

    @Update
    void update(Doctor doctor);

    @Delete
    void delete(Doctor doctor);

    @Query("SELECT * FROM Doctor")
    List<Doctor> getAll();

    @Query("SELECT * FROM Doctor WHERE specialtyId = :specialtyId")
    List<Doctor> getDoctorsBySpecialty(int specialtyId);
}

