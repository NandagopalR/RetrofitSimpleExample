package com.workshop.retrofitexample.utils;

import android.content.Context;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class UiUtils {

    public static Picasso.Builder getPicassoBuilder(Context context) {
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        return builder;
    }

}
