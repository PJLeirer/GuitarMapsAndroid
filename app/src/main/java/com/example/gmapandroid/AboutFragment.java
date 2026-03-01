package com.example.gmapandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gmapandroid.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment {

    private FragmentAboutBinding binding;
    private Menu mainMenu;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainMenu = ((MainActivity) getActivity()).getMenu();
        if (mainMenu != null) {
            mainMenu.findItem(R.id.action_fretboard_menu).setVisible(false);
            mainMenu.findItem(R.id.action_show_instructions).setVisible(false);
            mainMenu.findItem(R.id.action_show_about).setVisible(false);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
