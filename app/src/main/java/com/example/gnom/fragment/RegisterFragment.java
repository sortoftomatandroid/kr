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

import com.example.gnom.AppDatabase;
import com.example.gnom.R;
import com.example.gnom.User;
import com.example.gnom.UserDao;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText emailOrPhoneEditText;
    private EditText passwordEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        view.findViewById(R.id.register).setOnClickListener(this);

        nameEditText = view.findViewById(R.id.name);
        surnameEditText = view.findViewById(R.id.surname);
        emailOrPhoneEditText = view.findViewById(R.id.emailOrPhone);
        passwordEditText = view.findViewById(R.id.password);

        return view;
    }

    @Override
    public void onClick(View v) {
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String emailOrPhone = emailOrPhoneEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        UserDao userDao = AppDatabase.getInstance(requireContext()).userDao();

        if (name.isBlank() || surname.isBlank() || emailOrPhone.isBlank() || password.isBlank()) {
            Toast.makeText(requireContext(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Patterns.EMAIL_ADDRESS.matcher(emailOrPhone).matches()) {
            String email = emailOrPhone;
            if (userDao.getByEmail(email) == null) {
                userDao.insert(new User(name, surname, email, password));
                Toast.makeText(requireContext(), "Вы успешно зарегистрированы", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AuthorizationFragment())
                        .commit();
            } else {
                Toast.makeText(requireContext(), "Пользователь с такой почтой уже существует", Toast.LENGTH_SHORT).show();
            }
        } else if (Patterns.PHONE.matcher(emailOrPhone).matches()) {
            String phone = emailOrPhone;
            if (userDao.getByPhone(phone) == null) {
                User user = new User(name, surname, null, password);
                user.setPhone(phone);
                userDao.insert(user);
                Toast.makeText(requireContext(), "Вы успешно зарегистрированы", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AuthorizationFragment())
                        .commit();
            } else {
                Toast.makeText(requireContext(), "Пользователь с таким телефоном уже существует", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "Введите корректный email или телефон", Toast.LENGTH_SHORT).show();
        }
    }
}