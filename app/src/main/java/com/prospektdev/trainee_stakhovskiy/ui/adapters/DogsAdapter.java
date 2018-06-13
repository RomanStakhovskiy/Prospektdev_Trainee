package com.prospektdev.trainee_stakhovskiy.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.prospektdev.trainee_stakhovskiy.R;
import com.prospektdev.trainee_stakhovskiy.db.entities.LDog;
import com.prospektdev.trainee_stakhovskiy.ui.activities.ItemDetailsActivity;
import com.prospektdev.trainee_stakhovskiy.ui.fragments.ItemDetailsFragment;
import com.prospektdev.trainee_stakhovskiy.utils.GlideApp;
import com.prospektdev.trainee_stakhovskiy.utils.ShareUtils;

import java.util.List;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.Holder> {

    private Context context;
    private List<LDog> dogs;

    public DogsAdapter(Context context, List<LDog> dogs) {
        this.context = context;
        this.dogs = dogs;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dog_info, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        LDog dog = dogs.get(position);
        holder.bind(dog);
    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private ImageView ivDogImage;
        private TextView tvDogTitle;
        private ImageButton ibShare;

        Holder(View view) {
            super(view);

            ivDogImage = view.findViewById(R.id.iv_dog_image);
            tvDogTitle = view.findViewById(R.id.tv_dog_title_details);
            ibShare = view.findViewById(R.id.ib_share);
        }

        void bind(final LDog dog) {
            final String url = dog.getUrl();
            GlideApp.with(context)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .into(ivDogImage);

            ivDogImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetails(context, dog);
                }
            });

            if (dog.getTitle() == null)
                dog.setTitle(getTitle(url));

            tvDogTitle.setText(dog.getTitle());
            ibShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sharingIntent = ShareUtils.createSharingIntent(url);
                    context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });
        }

        private String getTitle(String url) {
            String title = "Empty Dog";
            try {
                String[] urlParts = url.split("/");
                title = urlParts[urlParts.length - 2];
            } catch (Exception e) {
                e.printStackTrace();
            }
            return title.substring(0, 1).toUpperCase() + title.substring(1);
        }

        private void showDetails(Context context, LDog dog) {
            Intent details = new Intent(context, ItemDetailsActivity.class);
            details.putExtra(ItemDetailsFragment.EXTRA_DOG, dog);
            context.startActivity(details);
        }
    }
}
