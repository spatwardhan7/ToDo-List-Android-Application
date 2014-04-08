package com.example.todo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class FinActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin);
        
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_fin, menu);
        return true;
    }
}
