package com.example.gnom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gnom.R;
import com.example.gnom.UserDao;

public class AuthFragment extends Fragment implements View.OnClickListener {

    final private UserDao userDao;

    private EditText emailEditText;
    private EditText passwordEditText;

    public AuthFragment(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);

        emailEditText = view.findViewById(R.id.email);
        passwordEditText = view.findViewById(R.id.password);

        view.findViewById(R.id.auth).setOnClickListener(this);
        view.findViewById(R.id.register).setOnClickListener(this);
        view.findViewById(R.id.recover).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.auth) {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (userDao.login(email, password) == null) {
                Toast.makeText(requireContext(), "Неверные данные", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Добро пожаловать", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.register) {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new RegFragment(userDao))
                    .commit();
        } else if (id == R.id.recover) {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new RecFragment(userDao))
                    .commit();
        }
    }
}
