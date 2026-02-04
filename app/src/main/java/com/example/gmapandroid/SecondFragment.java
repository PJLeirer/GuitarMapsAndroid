package com.example.gmapandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.util.Dictionary;
import java.util.Hashtable;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private FullGuitarMap guitarMap;

    Dictionary<String, Bitmap> images;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        images = new Hashtable<>();
        images.put("rootOff", BitmapFactory.decodeResource(getResources(), R.drawable.root_red_off));
        images.put("rootOn", BitmapFactory.decodeResource(getResources(), R.drawable.root_red_on));
        images.put("secondOff", BitmapFactory.decodeResource(getResources(), R.drawable.second_yellow_off));
        images.put("secondOn", BitmapFactory.decodeResource(getResources(), R.drawable.second_yellow_on));
        images.put("thirdOff", BitmapFactory.decodeResource(getResources(), R.drawable.third_orange_off));
        images.put("thirdOn", BitmapFactory.decodeResource(getResources(), R.drawable.third_orange_on));
        images.put("fourthOff", BitmapFactory.decodeResource(getResources(), R.drawable.fourth_purple_off));
        images.put("fourthOn", BitmapFactory.decodeResource(getResources(), R.drawable.fourth_purple_on));
        images.put("fifthOff", BitmapFactory.decodeResource(getResources(), R.drawable.fifth_green_off));
        images.put("fifthOn", BitmapFactory.decodeResource(getResources(), R.drawable.fifth_green_on));
        images.put("sixthOff", BitmapFactory.decodeResource(getResources(), R.drawable.sixth_pink_off));
        images.put("sixthOn", BitmapFactory.decodeResource(getResources(), R.drawable.sixth_pink_on));
        images.put("seventhOff", BitmapFactory.decodeResource(getResources(), R.drawable.seventh_teal_off));
        images.put("seventhOn", BitmapFactory.decodeResource(getResources(), R.drawable.seventh_teal_on));
        images.put("rosewood", BitmapFactory.decodeResource(getResources(), R.drawable.rosewood1));
        images.put("fret", BitmapFactory.decodeResource(getResources(), R.drawable.silver_fret));
        images.put("headstock", BitmapFactory.decodeResource(getResources(), R.drawable.headstock_lower));


        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Fretboard fretboard = new Fretboard(22, 6, 0, 0);
        guitarMap = new FullGuitarMap(getContext(), images, 22, 6, 0, 0);
        binding.mapContainer.addView(guitarMap);

        Spinner keySpinner = view.findViewById(R.id.key_spinner);
        keySpinner.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.key_spinner_items, android.R.layout.simple_spinner_item));

        Spinner scaleSpinner = view.findViewById(R.id.scale_spinner);
        scaleSpinner.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.scale_spinner_items, android.R.layout.simple_spinner_item));

        Button updateMapButton = view.findViewById(R.id.update_map_button);
        updateMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMap(keySpinner.getSelectedItemPosition(), scaleSpinner.getSelectedItemPosition());
            }
        });
    }

    private void updateMap(int key, int scale) {
        Log.d("Main Map", "Updating map with key " + key + " and scale " + scale);
        //Fretboard fretboard = new Fretboard(22, 6, key, scale);
        binding.mapContainer.removeView(guitarMap);
        guitarMap = new FullGuitarMap(getContext(), images, 22, 6, key, scale);
        binding.mapContainer.addView(guitarMap);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
