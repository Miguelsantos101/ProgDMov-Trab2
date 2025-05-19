package com.example.contactdoctorapp.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contactdoctorapp.data.entity.Specialty;

import java.util.List;

@Dao
public interface SpecialtyDao {
    @Insert
    void insert(Specialty specialty);

    @Update
    void update(Specialty specialty);

    @Delete
    void delete(Specialty specialty);

    @Query("SELECT * FROM Specialty")
    List<Specialty> getAll();

    @Query("SELECT COUNT(*) FROM Doctor WHERE specialtyId = :id")
    int countDoctorsBySpecialty(int id);
}
