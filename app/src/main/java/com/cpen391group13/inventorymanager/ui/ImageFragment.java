package com.cpen391group13.inventorymanager.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.helpers.PreferencesHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageFragment extends Fragment {
    @BindView(R.id.item_image) ImageView imageView;
    @BindView(R.id.image_label) TextView labelText;
    @BindView(R.id.image_description) TextView descriptionText;
    @BindView(R.id.close_img_btn) ImageButton closeImageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
//        itemId = bundle.getInt("item_id");
//        timestamp = bundle.getInt("datetime");
        int itemId = 1;
        String timestamp = "2018-03-20";

        Picasso.get().load(R.drawable.).into(imageView);

        String url = String.format("%s/items/%d/image",
                PreferencesHelper.getServerPath(this.getContext()),
                itemId);

        Picasso.get()
                .load("https://i.imgur.com/HaNx24m.png")
//                .load(url)
                .into(imageView);

        labelText.setText("Item " + itemId);
        descriptionText.setText("Time Taken: " + timestamp);

        closeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
