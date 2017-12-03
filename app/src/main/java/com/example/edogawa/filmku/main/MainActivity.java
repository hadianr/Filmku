package com.example.edogawa.filmku.main;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.edogawa.filmku.R;
import com.example.edogawa.filmku.data.ApiClient;
import com.example.edogawa.filmku.data.dao.MovieResponseDao;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private List<MainDao> mData = new ArrayList<>();
    private MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adapter = new MainAdapter(mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        ApiClient.service().getMovieList("555ec9d1d2930832b20cb3820ad14c4d")
                .enqueue(new Callback<MovieResponseDao>() {
                    @Override
                    public void onResponse(Call<MovieResponseDao> call, Response<MovieResponseDao> response) {
                        if (response.isSuccessful()) {
                            for (MovieResponseDao.MovieData data : response.body().getResults()) {
                                mData.add(new MainDao(data.getTitle(),"http://image.tmdb.org/t/p/w185"+data.getPoster_path()));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponseDao> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                mData.add(new MainDao("satu","https://akimg0.ask.fm/assets2/026/226/760/960/normal/profilepicture.jpg"));
//                mData.add(new MainDao("dua","http://78.media.tumblr.com/a00b41d9dfc6d8c9c45ebd47ef6a38c9/tumblr_nnynxbTJ6L1uoxa1ao5_500.png"));
//                mData.add(new MainDao("tilu","http://78.media.tumblr.com/cc14fde656421e1e8575dd8837c9a230/tumblr_nnynxbTJ6L1uoxa1ao4_500.png"));
//                mData.add(new MainDao("opat","http://78.media.tumblr.com/35592a797fb59e6dded4f595598afda9/tumblr_nnynxbTJ6L1uoxa1ao3_500.png"));
//                mData.add(new MainDao("lima","http://78.media.tumblr.com/a2c7ee96fe4d092b2fc6be8606f54159/tumblr_nnynxbTJ6L1uoxa1ao2_500.png"));
//                mData.add(new MainDao("enam","https://akimg0.ask.fm/assets2/026/226/760/960/normal/profilepicture.jpg"));
//                mData.add(new MainDao("tujuh","http://78.media.tumblr.com/a00b41d9dfc6d8c9c45ebd47ef6a38c9/tumblr_nnynxbTJ6L1uoxa1ao5_500.png"));
//                mData.add(new MainDao("delapan","http://78.media.tumblr.com/cc14fde656421e1e8575dd8837c9a230/tumblr_nnynxbTJ6L1uoxa1ao4_500.png"));
//                mData.add(new MainDao("sembilan","http://78.media.tumblr.com/35592a797fb59e6dded4f595598afda9/tumblr_nnynxbTJ6L1uoxa1ao3_500.png"));
//                mData.add(new MainDao("sepuluh","http://78.media.tumblr.com/a2c7ee96fe4d092b2fc6be8606f54159/tumblr_nnynxbTJ6L1uoxa1ao2_500.png"));
//
//                adapter.notifyDataSetChanged();
//            }
//        },5000);
        Toast.makeText(this, "Tunggu data 5 detik.....", Toast.LENGTH_SHORT).show();


    }


}
