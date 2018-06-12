package com.prospektdev.trainee_stakhovskiy.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.prospektdev.trainee_stakhovskiy.data.Item;
import com.prospektdev.trainee_stakhovskiy.data.ItemLab;
import com.prospektdev.trainee_stakhovskiy.R;

import java.util.List;

public class ItemsFragment extends Fragment {

    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_rv_list, container, false);
        recyclerView = view.findViewById(R.id.item_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private void updateUI() {
        ItemLab itemLab = ItemLab.get(getActivity());
        List<Item> items = itemLab.getItems();
        adapter = new Adapter(items);
        recyclerView.setAdapter(adapter);
    }

    private class Holder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private Item item;
        private Button button;

        public Holder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));
            titleTextView = itemView.findViewById(R.id.rv_item_tv);
            button = itemView.findViewById(R.id.share_rv_imagebtn);
        }

        public void bind(Item mItem) {
            item = mItem;
            titleTextView.setText(mItem.getTitle());
        }
    }

    private class Adapter extends RecyclerView.Adapter<Holder> {
        private List<Item> items;
        public Adapter(List<Item> mItems) {
            items = mItems;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new Holder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            Item item = items.get(position);
            holder.bind(item);

        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}
