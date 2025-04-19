package com.example.gnom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gnom.AppDatabase;
import com.example.gnom.R;
import com.example.gnom.User;
import com.example.gnom.UserDao;
import com.example.gnom.activity.MainActivity;

public class AuthorizationFragment extends Fragment implements View.OnClickListener {

    private EditText emailOrPhoneEditText;
    private EditText passwordEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authorize, container, false);

        emailOrPhoneEditText = view.findViewById(R.id.emailOrPhone);
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
            String emailOrPhone = emailOrPhoneEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            UserDao userDao = AppDatabase.getInstance(requireContext()).userDao();
            User user = userDao.login(emailOrPhone, password);

            if (user == null) {
                Toast.makeText(requireContext(), "Неверные данные", Toast.LENGTH_SHORT).show();
                return;
            }

            startActivity(new Intent(requireContext(), MainActivity.class).putExtra("id", user.getId()));
        } else if (id == R.id.register) {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new RegisterFragment())
                    .commit();
        } else if (id == R.id.recover) {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new RecoveryFragment())
                    .commit();
        }
    }
}