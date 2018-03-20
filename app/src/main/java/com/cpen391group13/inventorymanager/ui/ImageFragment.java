package com.cpen391group13.inventorymanager.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.Item;
import com.cpen391group13.inventorymanager.api.service.ItemClient;
import com.cpen391group13.inventorymanager.api.service.RetrofitClient;
import com.cpen391group13.inventorymanager.helpers.PreferencesHelper;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ImageFragment extends Fragment {
    @BindView(R.id.item_image) ImageView imageView;
    @BindView(R.id.image_label) TextView labelText;
    @BindView(R.id.image_description) TextView descriptionText;
    @BindView(R.id.close_img_btn) ImageButton closeImageButton;

    ItemClient itemClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.bind(this, view);

        // TODO: change this when we integrate with ItemView
        Bundle bundle = getArguments();
//      int itemId = bundle.getInt("item_id");
        int itemId = 1;

        // image stored on server under /items/{id}/image endpoint
        String url = String.format("%s/items/%d/image",
                PreferencesHelper.getServerPath(this.getContext()),
                itemId);

        // load image from server
        Picasso.get().load(url).into(imageView);

        Retrofit retrofit = RetrofitClient.getClient(this.getContext());
        itemClient = retrofit.create(ItemClient.class);

        Call<Item> call = itemClient.getItem(String.valueOf(itemId));
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful()) {
                    Item item = response.body();
                    labelText.setText("Item " + item.getId());
                    String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                            .format(item.getDatetime());
                    descriptionText.setText("Time Taken: " + time + " GMT");
                }
                else {
                    labelText.setText("");
                    descriptionText.setText("");
                }
            }
            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Toast.makeText(getActivity(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });

        closeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }
}