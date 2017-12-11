package com.example.edogawa.filmku.main;


import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.edogawa.filmku.R;
import com.example.edogawa.filmku.data.ApiClient;
import com.example.edogawa.filmku.data.dao.MovieResponseDao;
import com.example.edogawa.filmku.data.offline.MovieContract;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView mRecyclerView;
    private List<MainDao> mData = new ArrayList<>();
    private MainAdapter adapter;
    private String TAG = this.getClass().getSimpleName();
    private SwipeRefreshLayout mRefreshLayout;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(0,null,this);



        adapter = new MainAdapter(mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeMain);
        mToolbar = (Toolbar) findViewById(R.id.toolbarMain);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(gridLayoutManager);


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
//        Toast.makeText(this, "Tunggu data 5 detik.....", Toast.LENGTH_SHORT).show();

        getDataMovie();
    }

    private void getDataMovie(){
        mRefreshLayout.setRefreshing(true);
        ApiClient.service().getMovieList("555ec9d1d2930832b20cb3820ad14c4d")
                .enqueue(new Callback<MovieResponseDao>() {
                    @Override
                    public void onResponse(Call<MovieResponseDao> call, Response<MovieResponseDao> response) {
                        if (response.isSuccessful()) {

                            mRefreshLayout.setRefreshing(false);

                            Uri deleteUri = MovieContract.MovieEntry.CONTENT_URI;
                            getContentResolver().delete(deleteUri, null,null);

                            for (MovieResponseDao.MovieData data : response.body().getResults()) {
//                                mData.add(new MainDao(data.getTitle(),"http://image.tmdb.org/t/p/w185"+data.getPoster_path()));
                                ContentValues contentValues = new ContentValues();

                                contentValues.put(MovieContract.MovieEntry.COLUMN_FAVORITE_IDS,data.getId());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_ADULT,data.isAdult() ? 1 : 0 );
                                contentValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH,data.getBackdrop_path());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_GENRE,"");
                                contentValues.put(MovieContract.MovieEntry.COLUMN_ORI_TITLE,data.getOriginal_title());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANG,data.getOriginal_language());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,data.getOverview());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY,data.getPopularity());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH,data.getPoster_path());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE,data.getRelease_date());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE,data.getTitle());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_VIDEO,data.isVideo() ? 1 : 0);
                                contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVG,data.getVote_average());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT,data.getVote_count());

                                Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,contentValues);

                                if (uri != null){
                                    Log.d("onResponse", "Insert data success ");

                                }

                            }
                            adapter.notifyDataSetChanged();
                        }else{
                            mRefreshLayout.setRefreshing(false);
                            Toast.makeText(MainActivity.this, "Internal Server Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponseDao> call, Throwable t) {
                        mRefreshLayout.setRefreshing(false);
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onRefresh() {
        getDataMovie();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this){
            Cursor mMovieData = null;

            @Override
            public void onStartLoading(){
                if (mMovieData != null){
                    deliverResult(mMovieData);
                }else{
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return this.getContext().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            MovieContract.MovieEntry._ID);
                }catch (Exception e){
                    Log.e(TAG, "FAILED to asynchronously load data.\n" + e.getMessage());
                    e.printStackTrace();
                    return null;
                }
            }


            @Override
            public void deliverResult(Cursor data) {
                mMovieData = data;
                super.deliverResult(data);
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("onLoadFinished", String.valueOf(data.getCount()));
        mData.clear();


        for (int i = 0; i < data.getCount() ; i++) {
            data.moveToPosition(i);
            mData.add(new MainDao(
//                    Memasukan Title
                    data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)),
                    data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)),
//                    Memasukan image url
                    "https://image.tmdb.org/t/p/w185/" + data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH)),
                    "https://image.tmdb.org/t/p/w185/" + data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH)),
                    data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE))

            ));

        }

        adapter.notifyDataSetChanged();


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
