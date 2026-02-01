package com.example.gmapandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gmapandroid.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private FullGuitarMap guitarMap;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Fretboard fretboard = new Fretboard(22, 6, 0, 0);
        guitarMap = new FullGuitarMap(getContext(), fretboard);
        binding.mapContainer.addView(guitarMap);

        Spinner keySPinner = view.findViewById(R.id.key_spinner);
        keySPinner.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.key_spinner_items, android.R.layout.simple_spinner_item));

        Spinner scaleSpinner = view.findViewById(R.id.scale_spinner);
        scaleSpinner.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.scale_spinner_items, android.R.layout.simple_spinner_item));

        Button updateMapButton = view.findViewById(R.id.update_map_button);
        updateMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMap(scaleSpinner.getSelectedItemPosition(), keySPinner.getSelectedItemPosition());
            }
        });
    }

    private void updateMap(int key, int scale) {
        Log.d("Main Map", "Updating map with key " + key + " and scale " + scale);
        Fretboard fretboard = new Fretboard(22, 6, key, scale);
        guitarMap = new FullGuitarMap(getContext(), fretboard);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
