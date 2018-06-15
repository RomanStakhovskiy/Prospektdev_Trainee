package com.prospektdev.trainee_stakhovskiy.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.prospektdev.trainee_stakhovskiy.api.Callback;
import com.prospektdev.trainee_stakhovskiy.api.NetworkModule;
import com.prospektdev.trainee_stakhovskiy.R;
import com.prospektdev.trainee_stakhovskiy.db.AppDatabase;
import com.prospektdev.trainee_stakhovskiy.db.entities.LDog;
import com.prospektdev.trainee_stakhovskiy.ui.adapters.DogsAdapter;
import com.prospektdev.trainee_stakhovskiy.utils.ThreadUtils;

import java.util.List;

public class ItemsFragment extends Fragment {

    private RecyclerView recyclerView;
    private DogsAdapter adapter;
    private TextView tvEmpty;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_items, container, false);
        recyclerView = view.findViewById(R.id.rvItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvEmpty = view.findViewById(R.id.tv_empty);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (NetworkModule.get().isConnected(getActivity()))
            loadRemoteData();
        else
            loadLocalData();
    }

    private void loadRemoteData() {
        NetworkModule.v1().dogs().getRandomSet(new Callback<List<LDog>>() {
            @Override
            public void success(List<LDog> dogs) {
                saveData(dogs);
                fillData(dogs);
                updateVisibilityEmptyView();
            }

            @Override
            public void failure(Throwable error) {
                Toast.makeText(getActivity(), NetworkModule.get().parseError(error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveData(final List<LDog> dogs) {
        ThreadUtils.runInBackground(new Runnable() {
            @Override
            public void run() {
                LDog[] dogsArray = new LDog[dogs.size()];
                for (int i = 0; i < dogs.size(); i++) {
                    dogsArray[i] = dogs.get(i);
                }
                AppDatabase.instance().dogsDao().insert(dogsArray);
            }
        });
    }

    private void loadLocalData() {
        ThreadUtils.runInBackground(new Runnable() {
            @Override
            public void run() {
                List<LDog> dogs = AppDatabase.instance().dogsDao().get();
                fillData(dogs);
                updateVisibilityEmptyView();
            }
        });
    }

    private void fillData(List<LDog> dogs) {
        adapter = new DogsAdapter(getActivity(), dogs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    public DogsAdapter getDogsAdapter() {
        return adapter;
    }

    private void updateVisibilityEmptyView() {
        if (adapter != null) {
            if (adapter.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                showEmptyView();
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                hideEmptyView();
            }
        }
    }

    private void showEmptyView() {
        tvEmpty.setVisibility(View.VISIBLE);
    }

    private void hideEmptyView() {
        tvEmpty.setVisibility(View.GONE);
    }
}
