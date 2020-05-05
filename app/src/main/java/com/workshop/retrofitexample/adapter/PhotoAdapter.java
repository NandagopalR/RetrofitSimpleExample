package com.workshop.retrofitexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.workshop.retrofitexample.R;
import com.workshop.retrofitexample.data.model.PhotoModel;
import com.workshop.retrofitexample.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private List<PhotoModel> photoModelList;
    private Context context;
    private PhotoClickListener clickListener;

    public PhotoAdapter(PhotoClickListener clickListener) {
        this.clickListener = clickListener;
        photoModelList = new ArrayList<>();
    }

    public void setPhotoModelList(List<PhotoModel> itemList) {
        if (itemList == null) {
            return;
        }
        photoModelList.clear();
        photoModelList.addAll(itemList);
        notifyDataSetChanged();
    }

    public interface PhotoClickListener {
        void onPhotoClicked(PhotoModel model);
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        PhotoModel model = photoModelList.get(position);
        holder.bindDataToViews(model);
    }

    @Override
    public int getItemCount() {
        return photoModelList.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtTitle;
        private ImageView coverImage;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.title);
            coverImage = itemView.findViewById(R.id.coverImage);
            itemView.setOnClickListener(this);
        }

        public void bindDataToViews(PhotoModel model) {
            txtTitle.setText(model.getTitle());
            Picasso.Builder builder = UiUtils.getPicassoBuilder(context);
            builder.build().load(model.getThumbnailUrl())
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(coverImage);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position < 0) {
                return;
            }
            PhotoModel model = photoModelList.get(position);
            clickListener.onPhotoClicked(model);
        }
    }

}
