package com.example.gnom.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gnom.R;

public class NavigationFragment extends Fragment implements View.OnClickListener {

    private final int id;

    public NavigationFragment(int id) {
        this.id = id;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        // Подчеркнутые тексты
        view.<TextView>findViewById(R.id.navigation).setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        view.<TextView>findViewById(R.id.role).setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        // Назначаем обработчики для всех кнопок
        view.findViewById(R.id.profile).setOnClickListener(this);
        view.findViewById(R.id.favorites).setOnClickListener(this);
        view.findViewById(R.id.player).setOnClickListener(this);
        view.findViewById(R.id.anime_list).setOnClickListener(this);
        view.findViewById(R.id.reviews).setOnClickListener(this);
        view.findViewById(R.id.history).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        Log.d("NavigationFragment", "onClick triggered, view ID: " + v.getId());

        // Используем if-else для обработки кликов
        if (v.getId() == R.id.profile) {
            fragment = new ProfileFragment(id);
        } else if (v.getId() == R.id.favorites) {
            fragment = new FavoritesFragment(id);
        } else if (v.getId() == R.id.player) {
            Log.d("NavigationFragment", "Player button clicked");
            fragment = new PlayerFragment(id);  // Проверим, создает ли фрагмент плеера
        } else if (v.getId() == R.id.anime_list) {
            fragment = new AnimeListFragment(id);
        } else if (v.getId() == R.id.reviews) {
            fragment = new ReviewsFragment(id);
        } else if (v.getId() == R.id.history) {
            fragment = new HistoryFragment(id);
        }

        // Проверяем, был ли создан фрагмент
        if (fragment != null) {
            Log.d("NavigationFragment", "Fragment created: " + fragment.getClass().getSimpleName());
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Log.e("NavigationFragment", "No fragment created");
        }
    }
}
