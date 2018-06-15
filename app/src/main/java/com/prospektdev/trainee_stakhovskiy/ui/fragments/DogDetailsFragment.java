package com.prospektdev.trainee_stakhovskiy.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.prospektdev.trainee_stakhovskiy.R;
import com.prospektdev.trainee_stakhovskiy.db.entities.LDog;
import com.prospektdev.trainee_stakhovskiy.utils.GlideApp;
import com.prospektdev.trainee_stakhovskiy.utils.ShareUtils;

import java.io.Serializable;
import java.util.Random;

public class DogDetailsFragment extends Fragment {

    public static final String EXTRA_DOG = "com.prospektdev.trainee_stakhovskiy.ui.fragments.DogDetailsFragment";

    private static final int MAX_RANDOM_PRICE_NUMB = 5000;
    private static final int MIN_RANDOM_PRICE_NUMB = 200;
    private static final int MAX_RANDOM_PHONE_NUMB = 990000000;
    private static final int MIN_RANDOM_PHONE_NUMB = 111111111;

    private ImageButton btnShare;
    private LinearLayout llCall;
    private TextView tvDescription, tvDogTitle, tvPrice, tvPhoneNumber;
    private ImageView ivDogImage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);

        btnShare = view.findViewById(R.id.ib_share_details);
        tvDescription = view.findViewById(R.id.tv_text_details);
        tvDogTitle = view.findViewById(R.id.tv_dog_title_details);
        ivDogImage = view.findViewById(R.id.iv_dog_image_details);
        tvPrice = view.findViewById(R.id.tv_price);
        tvPhoneNumber = view.findViewById(R.id.tv_phone_number);
        llCall = view.findViewById(R.id.ll_call);

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
                final LDog dog = (LDog) object;

                GlideApp.with(getActivity())
                        .load(dog.getUrl())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.color.colorBlue)
                        .into(ivDogImage);

                tvDogTitle.setText(dog.getTitle());

                int price = rounding(randomNumber(MAX_RANDOM_PRICE_NUMB, MIN_RANDOM_PRICE_NUMB));
                tvPrice.setText(String.valueOf(getString(R.string.price) + price));

                final int phoneNumb = randomNumber(MAX_RANDOM_PHONE_NUMB, MIN_RANDOM_PHONE_NUMB);
                tvPhoneNumber.setText(String.valueOf(getString(R.string.phone_number) + phoneNumb));

                btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String url = dog.getUrl();
                        Intent sharingIntent = ShareUtils.createSharingIntent(url);
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    }
                });
                llCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",
                                tvPhoneNumber.getText().toString(), null));
                        startActivity(intent);
                    }
                });

            }
        }
    }

    public static int randomNumber(int max, int min) {
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    public static int rounding(int n) {
        return Math.round((n + 99) / 100) * 100;
    }

}
