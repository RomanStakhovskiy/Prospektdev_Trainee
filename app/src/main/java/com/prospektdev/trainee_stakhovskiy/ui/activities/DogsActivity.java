package com.prospektdev.trainee_stakhovskiy.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.prospektdev.trainee_stakhovskiy.R;
import com.prospektdev.trainee_stakhovskiy.ui.fragments.DogsFragment;

public class DogsActivity extends SingleFragmentActivity implements SearchView.OnQueryTextListener {

    @Override
    protected Fragment createFragment(Bundle bundle) {
        return new DogsFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.sv_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (fragment != null)
            ((DogsFragment) fragment).getDogsAdapter().getFilter().filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
