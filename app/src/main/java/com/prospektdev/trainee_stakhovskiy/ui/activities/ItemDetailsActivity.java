package com.prospektdev.trainee_stakhovskiy.ui.activities;

import android.support.v4.app.Fragment;

import com.prospektdev.trainee_stakhovskiy.ui.fragments.ItemDetailsFragment;

public class ItemDetailsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ItemDetailsFragment();
    }
}
