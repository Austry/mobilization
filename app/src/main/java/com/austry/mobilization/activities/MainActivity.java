package com.austry.mobilization.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.austry.mobilization.R;
import com.austry.mobilization.fragments.AllArtistsFragment;

public class MainActivity extends AppCompatActivity {

    public static final String MAIN_FRAGMENT_NAME = "main_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.flFragmentContainer,new AllArtistsFragment(), MAIN_FRAGMENT_NAME);
            ft.commit();
        }

    }
}
