package com.prospektdev.trainee_stakhovskiy.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.prospektdev.trainee_stakhovskiy.db.entities.LDog;
import com.prospektdev.trainee_stakhovskiy.ui.adapters.DogsAdapter;
import com.prospektdev.trainee_stakhovskiy.ui.fragments.ItemDetailsFragment;

import java.io.Serializable;
import java.util.Objects;

import static com.prospektdev.trainee_stakhovskiy.ui.fragments.ItemDetailsFragment.EXTRA_DOG;

public class ItemDetailsActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeButtonEnabled(true);

            if (getIntent().hasExtra(EXTRA_DOG)) {
                LDog dog = (LDog) Objects.requireNonNull(getIntent().getExtras()).getSerializable(EXTRA_DOG);
                if (dog != null)
                    supportActionBar.setTitle(dog.getTitle());
            }
        }
    }

    @Override
    protected Fragment createFragment(Bundle bundle) {
        ItemDetailsFragment itemDetailsFragment = new ItemDetailsFragment();
        itemDetailsFragment.setArguments(bundle);
        return itemDetailsFragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
