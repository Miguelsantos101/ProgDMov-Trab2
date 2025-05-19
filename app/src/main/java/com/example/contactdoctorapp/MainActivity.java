package com.example.contactdoctorapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactdoctorapp.databinding.ActivityMainBinding;
import com.example.contactdoctorapp.ui.specialty.SpecialtyFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.contactdoctorapp.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SpecialtyFragment()).commit();
    }

}