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
import android.widget.Toast;

import com.prospektdev.trainee_stakhovskiy.api.Callback;
import com.prospektdev.trainee_stakhovskiy.api.NetworkModule;
import com.prospektdev.trainee_stakhovskiy.R;
import com.prospektdev.trainee_stakhovskiy.db.entities.LDog;
import com.prospektdev.trainee_stakhovskiy.ui.adapters.DogsAdapter;

import java.util.List;

public class ItemsFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_items, container, false);
        recyclerView = view.findViewById(R.id.item_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
                fillData(dogs);
            }

            @Override
            public void failure(Throwable error) {
                Toast.makeText(getActivity(), NetworkModule.get().parseError(error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadLocalData() {

    }

    private void fillData(List<LDog> dogs) {
        DogsAdapter adapter = new DogsAdapter(getActivity(), dogs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}
