package com.example.gnom.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.gnom.AppDatabase;
import com.example.gnom.R;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RecoveryFragment extends Fragment implements View.OnClickListener, DialogInterface.OnClickListener {
    private int code;

    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText codeEditText;
    private EditText passwordEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recovery, container, false);

        phoneEditText = view.findViewById(R.id.phone);
        emailEditText = view.findViewById(R.id.email);

        view.findViewById(R.id.cancel).setOnClickListener(this);
        view.findViewById(R.id.next).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.cancel) {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AuthorizationFragment())
                    .commit();

        } else if (id == R.id.next) {
            String email = emailEditText.getText().toString();

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Невалидная почта", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.PHONE.matcher(phoneEditText.getText().toString()).matches()) {
                Toast.makeText(requireContext(), "Невалидный номер телефона", Toast.LENGTH_SHORT).show();
                return;
            }

            if (AppDatabase.getInstance(requireContext()).userDao().getByEmail(email) == null) {
                Toast.makeText(requireContext(), "Вы не зарегистрированы", Toast.LENGTH_SHORT).show();
                return;
            }

            final String SENDER = "gnompoltorashka@gmail.com";

            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER, "dqow ypoq udhl vdae");
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(SENDER));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject("Сброс пароля");
                code = new Random().nextInt(10000);
                message.setText(String.format("Код для восстановления пароля: %04d", code));
                Transport.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

            Context context = requireContext();
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            codeEditText = new EditText(context);
            codeEditText.setHint("Код");
            codeEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            layout.addView(codeEditText);
            passwordEditText = new EditText(context);
            passwordEditText.setHint("Новый пароль");
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            layout.addView(passwordEditText);

            new AlertDialog.Builder(context)
                    .setTitle("Сброс пароля")
                    .setMessage("На указанную почту отправлено письмо с кодом подтверждения")
                    .setPositiveButton(android.R.string.ok, this)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setView(layout)
                    .show();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (Integer.parseInt(codeEditText.getText().toString()) != code) {
            Toast.makeText(requireContext(), "Введен неправильный код", Toast.LENGTH_SHORT).show();
        } else {
            AppDatabase.getInstance(requireContext()).userDao().password(emailEditText.getText().toString(), passwordEditText.getText().toString());
            Toast.makeText(requireContext(), "Пароль успешно сброшен", Toast.LENGTH_SHORT).show();
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AuthorizationFragment())
                    .commit();
        }
    }

}