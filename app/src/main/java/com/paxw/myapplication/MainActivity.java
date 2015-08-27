package com.paxw.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.paxw.myapplication.view.ToggleButton;

public class MainActivity extends Activity implements ToggleButton.OnToggleButtonStateChangedListener {
    private ToggleButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (ToggleButton) findViewById(R.id.view);
        findViewById(R.id.tobanner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this,BannerActivity.class));
            }
        });
    }

    @Override
    public void onToggleButtonStateChanged() {
        if (button!=null) Log.e("customView",button.getCurrentState()+"");
    }
}
