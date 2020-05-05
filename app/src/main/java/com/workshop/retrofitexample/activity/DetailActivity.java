package com.workshop.retrofitexample.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.workshop.retrofitexample.R;
import com.workshop.retrofitexample.app.AppConstants;
import com.workshop.retrofitexample.utils.UiUtils;

public class DetailActivity extends AppCompatActivity {

    private TextView tvId;
    private TextView tvDescription;
    private ImageView imgPhoto;

    private int albumId;
    private String photoLargeUrl;
    private String description;

    public static Intent getCallingIntent(Context context, int albumId, String photoLargeUrl, String description) {
        return new Intent(context, DetailActivity.class)
                .putExtra(AppConstants.ALBUM_ID, albumId)
                .putExtra(AppConstants.ALBUM_PHOTO_URL, photoLargeUrl)
                .putExtra(AppConstants.ALBUM_DESCRIPTION, description);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getIntent() != null) {
            albumId = getIntent().getExtras().getInt(AppConstants.ALBUM_ID);
            photoLargeUrl = getIntent().getExtras().getString(AppConstants.ALBUM_PHOTO_URL);
            description = getIntent().getExtras().getString(AppConstants.ALBUM_DESCRIPTION);
        }

        tvId = findViewById(R.id.tv_id_value);
        tvDescription = findViewById(R.id.tv_description);
        imgPhoto = findViewById(R.id.img_photo);

        tvId.setText(String.valueOf(albumId));
        tvDescription.setText(description);
        Picasso.Builder builder = UiUtils.getPicassoBuilder(this);
        builder.build().load(photoLargeUrl)
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(imgPhoto);
    }
}
