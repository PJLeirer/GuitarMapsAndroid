package com.example.gmapandroid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.gmapandroid.databinding.ActivityMainBinding;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private Menu mMenu;
    public Menu getMenu() {
        return mMenu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // set initial menu state
        menu.findItem(R.id.action_fretboard_menu).setVisible(false);
        menu.findItem(R.id.action_show_reference_card).setVisible(false);
        menu.findItem(R.id.action_show_instructions).setVisible(false);
        menu.findItem(R.id.action_show_about).setVisible(false);
        // set menu reference
        mMenu = menu;
        return true;
    }

    private void showReferenceCard() {
        // show anywhere, attach to any current fragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        Fragment currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
        View fragmentView = currentFragment.getView();

        //create popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.reference_card, null);

        LinearLayout cardLayout = popupView.findViewById(R.id.reference_card_layout);
        ReferenceCard refCard = new ReferenceCard(this);

        PopupWindow popReferenceCard = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        // add content to popup window
        /*
        View card = popupView.findViewById(R.id.reference_card_layout);
        TextView title = popupView.findViewById(R.id.reference_card_title);
        TextView info = popupView.findViewById(R.id.reference_card_info);
        title.setText("References");
        info.setText("updated info text");
        */
        // show popup window


        popReferenceCard.showAtLocation(fragmentView, Gravity.CENTER, 0, 0);
    }

    private void showMapOptionsMenu() {
        // make sure fretboard fragment is visible
        Fragment currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (!(currentFragment instanceof SecondFragment)) {
            return;
        }
        SecondFragment secondFragment = (SecondFragment) currentFragment;
        View fragmentView = secondFragment.getView();
        if (fragmentView == null) {
            return;
        }



        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fretboard_menu, null);

        PopupWindow popMenu = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        int[] settings = SettingsData.GetFretbordSettingsData();

        Spinner keySpinner = popupView.findViewById(R.id.pop_key_spinner);
        ArrayAdapter<CharSequence> keyAdapter = ArrayAdapter.createFromResource(popupView.getContext(), R.array.key_spinner_items, android.R.layout.simple_spinner_item);
        keyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        keySpinner.setAdapter(keyAdapter);
        keySpinner.setSelection(settings[0]);

        Spinner scaleSpinner = popupView.findViewById(R.id.pop_scale_spinner);
        ArrayAdapter<CharSequence> scaleAdapter = ArrayAdapter.createFromResource(popupView.getContext(), R.array.scale_spinner_items, android.R.layout.simple_spinner_item);
        scaleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scaleSpinner.setAdapter(scaleAdapter);
        scaleSpinner.setSelection(settings[1]);

        Spinner modeSpinner = popupView.findViewById(R.id.pop_mode_spinner);
        ArrayAdapter<CharSequence> modeAdapter = ArrayAdapter.createFromResource(popupView.getContext(), R.array.mode_spinner_items, android.R.layout.simple_spinner_item);
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(modeAdapter);
        modeSpinner.setSelection(settings[2]);

        Spinner positionSpinner = popupView.findViewById(R.id.pop_position_spinner);
        ArrayAdapter<CharSequence> positionAdapter = ArrayAdapter.createFromResource(popupView.getContext(), R.array.position_spinner_items, android.R.layout.simple_spinner_item);
        positionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        positionSpinner.setAdapter(positionAdapter);
        positionSpinner.setSelection(settings[3]);

        Button updateMapButton = popupView.findViewById(R.id.pop_go_button);
        updateMapButton.setOnClickListener(v -> {
            popMenu.dismiss();
            int[] selectedSettings = {
                    keySpinner.getSelectedItemPosition(),
                    scaleSpinner.getSelectedItemPosition(),
                    modeSpinner.getSelectedItemPosition(),
                    positionSpinner.getSelectedItemPosition()
            };
            SettingsData.SetFretboardSettings(selectedSettings[0], selectedSettings[1], selectedSettings[2], selectedSettings[3]);

            updateMap(selectedSettings[0], selectedSettings[1], selectedSettings[2], selectedSettings[3]);
        });

        View mapContainer = fragmentView.findViewById(R.id.guitar_map);
        popMenu.showAtLocation(mapContainer, Gravity.CENTER, 0, 0);
    }

    private void updateMap(int key, int scale, int mode, int pos) {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        Fragment currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (currentFragment instanceof SecondFragment) {
            ((SecondFragment) currentFragment).updateMap(key, scale, mode, pos);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_fretboard_menu) {
            showMapOptionsMenu();
            return true;
        } else if(id == R.id.action_show_reference_card) {
            showReferenceCard();
            return true;
        } else if (id == R.id.action_show_instructions) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_FirstFragment_to_InstructionsFragment);
            return true;
        } else if (id == R.id.action_show_about) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_FirstFragment_to_AboutFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
