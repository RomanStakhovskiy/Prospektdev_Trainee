package com.prospektdev.trainee_stakhovskiy.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.prospektdev.trainee_stakhovskiy.R;
import com.prospektdev.trainee_stakhovskiy.db.entities.LDog;
import com.prospektdev.trainee_stakhovskiy.ui.activities.DogDetailsActivity;
import com.prospektdev.trainee_stakhovskiy.ui.fragments.DogDetailsFragment;
import com.prospektdev.trainee_stakhovskiy.utils.GlideApp;
import com.prospektdev.trainee_stakhovskiy.utils.ShareUtils;

import java.util.ArrayList;
import java.util.List;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.Holder> implements Filterable {

    private Context context;

    private List<LDog> dogs;
    private List<LDog> originalDogs;

    public DogsAdapter(Context context, List<LDog> dogs) {
        this.context = context;
        this.dogs = dogs;
        this.originalDogs = dogs;
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

    public boolean isEmpty() {
        return originalDogs.isEmpty();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dogs = (List<LDog>) results.values;
                DogsAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<LDog> filteredResults;
                if (constraint.length() == 0)
                    filteredResults = originalDogs;
                else
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    private List<LDog> getFilteredResults(String constraint) {
        List<LDog> results = new ArrayList<>();

        for (LDog dog : originalDogs) {
            String title = dog.getTitle();
            if (title.toLowerCase().contains(constraint.toLowerCase())) {
                results.add(dog);
            }
        }
        return results;
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
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.color.colorBlue)
                    .into(ivDogImage);

            ivDogImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetails(context, dog);
                }
            });

            tvDogTitle.setText(dog.getTitle());
            ibShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sharingIntent = ShareUtils.createSharingIntent(url);
                    context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });
        }

        private void showDetails(Context context, LDog dog) {
            Intent details = new Intent(context, DogDetailsActivity.class);
            details.putExtra(DogDetailsFragment.EXTRA_DOG, dog);
            context.startActivity(details);
        }
    }
}
