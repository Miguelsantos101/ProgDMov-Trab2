package com.example.contactdoctorapp.ui.specialty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.contactdoctorapp.R;
import com.example.contactdoctorapp.data.ContactDoctorDatabase;
import com.example.contactdoctorapp.data.entity.Specialty;
import com.example.contactdoctorapp.databinding.FragmentSpecialtyBinding;
import com.example.contactdoctorapp.ui.doctor.DoctorFragment;

import java.util.ArrayList;
import java.util.List;

public class SpecialtyFragment extends Fragment {
    private FragmentSpecialtyBinding binding;
    private ContactDoctorDatabase db;
    private List<Specialty> specialties = new ArrayList<>();
    private ArrayAdapter<Specialty> adapter;
    private Specialty selectedSpecialty;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSpecialtyBinding.inflate(inflater, container, false);
        db = ContactDoctorDatabase.getInstance(requireContext());

        // Delete database
        // requireContext().deleteDatabase("ContactDoctor");

        loadSpecialties();

        binding.btnToDoctor.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new DoctorFragment())
                        .commit()
        );

        binding.btnSave.setOnClickListener(v -> saveSpecialty());
        binding.btnUpdate.setOnClickListener(v -> updateSpecialty());
        binding.btnDelete.setOnClickListener(v -> deleteSpecialty());

        binding.listViewSpecialties.setOnItemClickListener((parent, view, position, id) -> {
            if (specialties != null && position >= 0 && position < specialties.size()) {
                selectedSpecialty = specialties.get(position);
                binding.editDescription.setText(selectedSpecialty.description);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSpecialties();
    }

    private void loadSpecialties() {
        specialties = db.specialtyDao().getAll();
        if (adapter == null) {
            adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, specialties);
            binding.listViewSpecialties.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(specialties);
            adapter.notifyDataSetChanged();
        }
    }

    private void saveSpecialty() {
        String desc = binding.editDescription.getText().toString().trim();
        if (!desc.isEmpty()) {
            Specialty s = new Specialty();
            s.description = desc;
            db.specialtyDao().insert(s);
            Toast.makeText(getContext(), "Specialty saved", Toast.LENGTH_SHORT).show();
            binding.editDescription.setText("");
            loadSpecialties();
        }
    }

    private void updateSpecialty() {
        if (selectedSpecialty != null) {
            selectedSpecialty.description = binding.editDescription.getText().toString().trim();
            db.specialtyDao().update(selectedSpecialty);
            Toast.makeText(getContext(), "Specialty updated", Toast.LENGTH_SHORT).show();
            binding.editDescription.setText("");
            selectedSpecialty = null;
            loadSpecialties();
        }
    }

    private void deleteSpecialty() {
        if (selectedSpecialty != null) {
            int count = db.specialtyDao().countDoctorsBySpecialty(selectedSpecialty.id);
            if (count == 0) {
                db.specialtyDao().delete(selectedSpecialty);
                Toast.makeText(getContext(), "Specialty deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Cannot delete specialty. Doctors are linked to it.", Toast.LENGTH_LONG).show();
            }
            binding.editDescription.setText("");
            selectedSpecialty = null;
            loadSpecialties();
        }
    }
}
