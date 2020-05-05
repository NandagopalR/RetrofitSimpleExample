package com.workshop.retrofitexample.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.workshop.retrofitexample.R;
import com.workshop.retrofitexample.adapter.PhotoAdapter;
import com.workshop.retrofitexample.data.model.PhotoModel;
import com.workshop.retrofitexample.data.network.RetrofitInstance;
import com.workshop.retrofitexample.data.network.WorkshopApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PhotoAdapter.PhotoClickListener {

    private PhotoAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new PhotoAdapter(this);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);

        /*Create handle for the RetrofitInstance interface*/
        WorkshopApi workshopApi = RetrofitInstance.getRetrofitInstance().create(WorkshopApi.class);
        showLoading();
        workshopApi.getAllPhotos().enqueue(new Callback<List<PhotoModel>>() {
            @Override
            public void onResponse(Call<List<PhotoModel>> call, Response<List<PhotoModel>> response) {
                hideLoading();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<PhotoModel>> call, Throwable throwable) {
                hideLoading();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<PhotoModel> photoModelList) {
        adapter.setPhotoModelList(photoModelList);
    }

    private void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading....");
            progressDialog.show();
        } else {
            progressDialog.show();
        }
    }

    private void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onPhotoClicked(PhotoModel model) {
        startActivity(DetailActivity.getCallingIntent(this, model.getId(), model.getUrl(), model.getTitle()));
    }
}
