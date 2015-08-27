package com.paxw.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.paxw.myapplication.view.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton button = (ToggleButton) findViewById(R.id.view);
        button.setToggleButtonBackground(R.drawable.switch_background);
        button.setSlideButtonBackgroundResource(R.drawable.slide_button_background);
        button.setToggleState(true);
    }

}
