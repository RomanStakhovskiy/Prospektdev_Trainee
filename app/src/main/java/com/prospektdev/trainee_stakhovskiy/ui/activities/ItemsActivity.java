package com.prospektdev.trainee_stakhovskiy.ui.activities;

import android.support.v4.app.Fragment;

import com.prospektdev.trainee_stakhovskiy.ui.fragments.ItemsFragment;

public class ItemsActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ItemsFragment();
    }
}
