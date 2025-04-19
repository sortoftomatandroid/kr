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
import com.example.gnom.User;
import com.example.gnom.UserDao;

public class RegFragment extends Fragment implements View.OnClickListener {

    final private UserDao userDao;

    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    public RegFragment(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reg, container, false);

        nameEditText = view.findViewById(R.id.name);
        surnameEditText = view.findViewById(R.id.surname);
        emailEditText = view.findViewById(R.id.email);
        passwordEditText = view.findViewById(R.id.password);

        view.findViewById(R.id.auth).setOnClickListener(this);
        view.findViewById(R.id.register).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.auth) {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AuthFragment(userDao))
                    .commit();
        } else if (id == R.id.register) {
            String email = emailEditText.getText().toString();

            if (userDao.getByEmail(email) != null) {
                Toast.makeText(requireContext(), "Пользователь с такой почтой уже существует", Toast.LENGTH_SHORT).show();
            } else {
                userDao.insert(new User(
                        nameEditText.getText().toString(),
                        surnameEditText.getText().toString(),
                        email,
                        passwordEditText.getText().toString()
                ));
                Toast.makeText(requireContext(), "Вы успешно зарегистрированы", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
