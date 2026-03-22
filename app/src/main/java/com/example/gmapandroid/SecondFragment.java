package com.example.gmapandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gmapandroid.databinding.FragmentSecondBinding;

import java.util.Dictionary;
import java.util.Hashtable;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private FullGuitarMap guitarMap;
    private Menu mainMenu;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        mainMenu = ((MainActivity)getActivity()).getMenu();
        if(mainMenu != null) {
            mainMenu.findItem(R.id.action_fretboard_menu).setVisible(true);
            mainMenu.findItem(R.id.action_show_reference_card).setVisible(true);
            mainMenu.findItem(R.id.action_show_instructions).setVisible(false);
            mainMenu.findItem(R.id.action_show_about).setVisible(false);
        }
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        guitarMap = view.findViewById(R.id.guitar_map);

        Dictionary<String, Bitmap> images = new Hashtable<>();
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
        images.put("inlayDot", BitmapFactory.decodeResource(getResources(), R.drawable.mother_of_pearl_inlay_dot));
        images.put("dot_1", BitmapFactory.decodeResource(getResources(), R.drawable.dot_1));
        images.put("dot_2", BitmapFactory.decodeResource(getResources(), R.drawable.dot_2));
        images.put("dot_3", BitmapFactory.decodeResource(getResources(), R.drawable.dot_3));
        images.put("dot_5", BitmapFactory.decodeResource(getResources(), R.drawable.dot_5));
        images.put("dot_7", BitmapFactory.decodeResource(getResources(), R.drawable.dot_7));
        images.put("dot_9", BitmapFactory.decodeResource(getResources(), R.drawable.dot_9));
        images.put("dot_15", BitmapFactory.decodeResource(getResources(), R.drawable.dot_15));
        images.put("dot_17", BitmapFactory.decodeResource(getResources(), R.drawable.dot_17));
        images.put("dot_19", BitmapFactory.decodeResource(getResources(), R.drawable.dot_19));


        guitarMap.setImages(images);

        int[] settings = SettingsData.GetFretbordSettingsData();
        updateMap(settings[0], settings[1], settings[2], settings[3]);
    }

    public void updateMap(int key, int scale, int mode, int position) {
        Log.d("Main Map", "Updating map with key " + key + " and scale " + scale + " and mode " + mode + " and position " + position);
        guitarMap.updateSettings(key, scale, mode, position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
