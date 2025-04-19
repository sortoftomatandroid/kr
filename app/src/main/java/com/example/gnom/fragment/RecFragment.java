    package com.example.gnom.fragment;

    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import androidx.annotation.NonNull;
    import androidx.fragment.app.Fragment;

    import com.example.gnom.R;
    import com.example.gnom.UserDao;

    public class RecFragment extends Fragment implements View.OnClickListener {

        final private UserDao userDao;

        public RecFragment(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_rec, container, false);
            view.findViewById(R.id.cancel).setOnClickListener(this);
            return view;
        }

        @Override
        public void onClick(View v) {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AuthFragment(userDao))
                    .commit();
        }
    }