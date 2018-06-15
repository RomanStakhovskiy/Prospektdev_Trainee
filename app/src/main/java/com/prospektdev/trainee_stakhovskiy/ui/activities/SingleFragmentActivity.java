package com.prospektdev.trainee_stakhovskiy.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.prospektdev.trainee_stakhovskiy.R;
import com.prospektdev.trainee_stakhovskiy.db.AppDatabase;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected Fragment fragment;

    protected abstract Fragment createFragment(Bundle bundle);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        AppDatabase.init(getApplicationContext());

        FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment(getIntent().getExtras());
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }
}
