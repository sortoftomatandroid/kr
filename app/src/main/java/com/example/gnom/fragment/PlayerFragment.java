package com.example.gnom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gnom.R;

public class PlayerFragment extends Fragment {

    private final int id;

    public PlayerFragment(int id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        Spinner seriesSpinner = view.findViewById(R.id.series_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"1", "2", "3", "4", "5"});
        seriesSpinner.setAdapter(adapter);

        // Кнопка навигации
        Button navBtn = view.findViewById(R.id.nav_button);
        navBtn.setOnClickListener(v -> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new NavigationFragment(id))
                .commit());

        // Кнопка выбора роли
        Button roleBtn = view.findViewById(R.id.role_button);
        roleBtn.setOnClickListener(v -> requireActivity().finish());

        return view;
    }
}
