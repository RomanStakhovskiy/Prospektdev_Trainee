package com.prospektdev.trainee_stakhovskiy.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.prospektdev.trainee_stakhovskiy.data.Item;
import com.prospektdev.trainee_stakhovskiy.R;

public class ItemDetailsFragment extends Fragment {

    private Item item;
    private Button shareBtn;
    private TextView itemText, tv;
    private ImageView iv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = new Item();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item, container, false);

        shareBtn = v.findViewById(R.id.share_btn);
        itemText = v.findViewById(R.id.item_text);
        tv = v.findViewById(R.id.item_tv);
        iv = v.findViewById(R.id.iv_item);

        return v;
    }
}
