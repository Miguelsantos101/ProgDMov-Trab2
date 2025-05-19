package com.example.contactdoctorapp.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.contactdoctorapp.R;
import com.example.contactdoctorapp.data.ContactDoctorDatabase;
import com.example.contactdoctorapp.data.entity.Doctor;
import com.example.contactdoctorapp.data.entity.Specialty;
import com.example.contactdoctorapp.databinding.FragmentDoctorBinding;
import com.example.contactdoctorapp.ui.specialty.SpecialtyFragment;

import java.util.ArrayList;
import java.util.List;

public class DoctorFragment extends Fragment {
    private FragmentDoctorBinding binding;
    private ContactDoctorDatabase db;
    private List<Doctor> doctors;
    private Doctor selectedDoctor;

    private List<Specialty> specialties;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDoctorBinding.inflate(inflater, container, false);
        db = ContactDoctorDatabase.getInstance(requireContext());

        binding.btnToSpecialty.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new SpecialtyFragment())
                        .commit()
        );

        binding.btnSave.setOnClickListener(v -> saveDoctor());
        binding.btnUpdate.setOnClickListener(v -> updateDoctor());
        binding.btnDelete.setOnClickListener(v -> deleteDoctor());

        binding.listViewDoctors.setOnItemClickListener((parent, view, position, id) -> {
            selectedDoctor = doctors.get(position);
            binding.editName.setText(selectedDoctor.name);
            binding.editPhone.setText(selectedDoctor.phone);
            binding.editAddress.setText(selectedDoctor.address);
            for (int i = 0; i < specialties.size(); i++) {
                if (specialties.get(i).id == selectedDoctor.specialtyId) {
                    binding.spinnerSpecialty.setSelection(i);
                    break;
                }
            }
        });

        binding.spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    loadDoctors();
                } else {
                    int specId = specialties.get(position - 1).id;
                    loadDoctors(db.doctorDao().getDoctorsBySpecialty(specId));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSpecialties();
        setupFilterSpinner();
        loadDoctors();
    }

    private void loadSpecialties() {
        specialties = db.specialtyDao().getAll();
        List<String> description = new ArrayList<>();
        for (Specialty specialty : specialties) description.add(specialty.description);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, description);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSpecialty.setAdapter(spinnerAdapter);
    }

    private void setupFilterSpinner() {
        List<Specialty> filterList = new ArrayList<>();
        filterList.add(new Specialty() {{ id = -1; description = "All Specialties"; }});
        filterList.addAll(specialties);

        ArrayAdapter<Specialty> filterAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                filterList
        );
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerFilter.setAdapter(filterAdapter);
    }

    private void loadDoctors(List<Doctor> list) {
        doctors = list;
        ArrayAdapter<Doctor> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                doctors
        );
        binding.listViewDoctors.setAdapter(adapter);
    }

    private void loadDoctors() {
        loadDoctors(db.doctorDao().getAll());
    }


    private void saveDoctor() {
        String name = binding.editName.getText().toString().trim();
        String phone = binding.editPhone.getText().toString().trim();
        String address = binding.editAddress.getText().toString().trim();
        int index = binding.spinnerSpecialty.getSelectedItemPosition();
        if (!name.isEmpty() && index >= 0) {
            Doctor d = new Doctor();
            d.name = name;
            d.phone = phone;
            d.address = address;
            d.specialtyId = specialties.get(index).id;
            db.doctorDao().insert(d);
            Toast.makeText(getContext(), "Doctor saved", Toast.LENGTH_SHORT).show();
            clearFields();
            loadDoctors();
        }
    }

    private void updateDoctor() {
        if (selectedDoctor != null) {
            selectedDoctor.name = binding.editName.getText().toString().trim();
            selectedDoctor.phone = binding.editPhone.getText().toString().trim();
            selectedDoctor.address = binding.editAddress.getText().toString().trim();
            int index = binding.spinnerSpecialty.getSelectedItemPosition();
            selectedDoctor.specialtyId = specialties.get(index).id;
            db.doctorDao().update(selectedDoctor);
            Toast.makeText(getContext(), "Doctor updated", Toast.LENGTH_SHORT).show();
            clearFields();
            selectedDoctor = null;
            loadDoctors();
        }
    }

    private void deleteDoctor() {
        if (selectedDoctor != null) {
            db.doctorDao().delete(selectedDoctor);
            Toast.makeText(getContext(), "Doctor deleted", Toast.LENGTH_SHORT).show();
            clearFields();
            selectedDoctor = null;
            loadDoctors();
        }
    }

    private void clearFields() {
        binding.editName.setText("");
        binding.editPhone.setText("");
        binding.editAddress.setText("");
    }
}
