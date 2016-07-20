package com.app.fastindex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.fastindex.library.FastIndexView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FastIndexView indexView = (FastIndexView) findViewById(R.id.fast_index);
    }
}
