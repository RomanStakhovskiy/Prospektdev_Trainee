package com.prospektdev.trainee_stakhovskiy.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.prospektdev.trainee_stakhovskiy.R;
import com.prospektdev.trainee_stakhovskiy.db.entities.LDog;
import com.prospektdev.trainee_stakhovskiy.utils.GlideApp;

import java.io.Serializable;
import java.util.Random;

public class ItemDetailsFragment extends Fragment {

    public static final String EXTRA_DOG = "com.prospektdev.trainee_stakhovskiy.ui.fragments.ItemDetailsFragment";

    private ImageButton btnShare;
    private TextView tvDescription, tvDogTitle;
    private ImageView ivDogImage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);

        btnShare = view.findViewById(R.id.ib_share_details);
        tvDescription = view.findViewById(R.id.tv_text_details);
        tvDogTitle = view.findViewById(R.id.tv_dog_title_details);
        ivDogImage = view.findViewById(R.id.iv_dog_image_details);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showDetails(getArguments());
    }

    private void showDetails(Bundle arguments) {
        if (arguments != null) {
            Serializable object = arguments.getSerializable(EXTRA_DOG);
            if (object != null && (object instanceof LDog)) {
                LDog dog = (LDog) object;

                GlideApp.with(getActivity())
                        .load(dog.getUrl())
                        .centerCrop()
                        .placeholder(R.drawable.progress_animation)
                        .into(ivDogImage);

                tvDogTitle.setText(dog.getTitle());
                tvDescription.setText(randomDetails());
            }
        }
    }


    public static String randomDetails() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(256);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
