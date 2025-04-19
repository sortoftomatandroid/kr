package com.example.gnom.fragment;

import android.os.Bundle;
import android.util.Patterns;
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

public class RegistrationFragment extends Fragment implements View.OnClickListener {

    final private UserDao userDao;

    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    public RegistrationFragment(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

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
                    .replace(R.id.fragment_container, new AuthorizationFragment())
                    .commit();
        } else if (id == R.id.register) {

            String name = nameEditText.getText().toString();
            String surname = surnameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            String text;

            if (name.isBlank() || surname.isBlank() || email.isBlank() || password.isBlank()) {
                text = "Не все поля заполнены";
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                text = "Невалидный email";
            } else if (userDao.getByEmail(email) != null) {
                text = "Пользователь с такой почтой уже существует";
            } else {
                userDao.insert(new User(name, surname, email, password));
                text = "Вы успешно зарегистрированы";
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AuthorizationFragment())
                        .commit();
            }

            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show();
        }
    }
}
