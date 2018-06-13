package com.prospektdev.trainee_stakhovskiy.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.prospektdev.trainee_stakhovskiy.ui.fragments.ItemDetailsFragment;

public class ItemDetailsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(Bundle bundle) {
        ItemDetailsFragment itemDetailsFragment = new ItemDetailsFragment();
        itemDetailsFragment.setArguments(bundle);
        return itemDetailsFragment;
    }
}
