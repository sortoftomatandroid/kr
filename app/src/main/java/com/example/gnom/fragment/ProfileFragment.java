package com.example.gnom.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.gnom.AppDatabase;
import com.example.gnom.R;
import com.example.gnom.User;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private final int id;
    private ImageView avatarImageView;

    public ProfileFragment(int id) {
        this.id = id;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        avatarImageView = view.findViewById(R.id.avatar);
        avatarImageView.setOnClickListener(this);
        User user = AppDatabase.getInstance(requireContext()).userDao().get(id);
        Bitmap avatar = user.getAvatar();
        if (avatar != null) {
            avatarImageView.setImageBitmap(avatar);
        }

        view.<TextView>findViewById(R.id.name).setText(user.getName() + ' ' + user.getSurname());
        view.<TextView>findViewById(R.id.email).setText(user.getEmail());
        view.<TextView>findViewById(R.id.phone).setText(user.getPhone());
        view.<TextView>findViewById(R.id.date).setText(user.getCreated().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));

        view.findViewById(R.id.navigation).setOnClickListener(this);
        view.findViewById(R.id.login).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        FragmentActivity activity = requireActivity();

        if (id == R.id.avatar) {
            startActivityForResult(new Intent()
                    .setType("image/*")
                    .setAction(Intent.ACTION_GET_CONTENT), 0);
        } else if (id == R.id.navigation) {
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new NavigationFragment(id))
                    .commit();
        } else if (id == R.id.login) {
            activity.finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            try {
                User user = AppDatabase.getInstance(requireContext()).userDao().get(id);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), data.getData());
                user.setAvatar(bitmap);
                AppDatabase.getInstance(requireContext()).userDao().update(user);
                avatarImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}