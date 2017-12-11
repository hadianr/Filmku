package com.example.edogawa.filmku.detail;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edogawa.filmku.R;
import com.example.edogawa.filmku.main.MainDao;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private MainDao mData;
    private TextView tvTitle,tvDesc,tvRelease;
    private ImageView imgPoster,imgBackground;
    private Toolbar mToolbar;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mData = getIntent().getParcelableExtra("dataMovie");

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDesc = (TextView) findViewById(R.id.tvDesc);
        tvRelease = (TextView) findViewById(R.id.tvRelease);
        imgPoster = (ImageView) findViewById(R.id.imgPoster);
        imgBackground = (ImageView) findViewById(R.id.imageToolbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbarDetail);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mData.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        tvTitle.setText(mData.getTitle());
        tvDesc.setText(mData.getDescription());
        tvRelease.setText(mData.getReleaseDate());

        Picasso.with(this)
                .load(mData.getImageUrl())
                .into(imgPoster);

        Picasso.with(this)
                .load(mData.getImageBackground())
                .into(imgBackground);
    }
}
