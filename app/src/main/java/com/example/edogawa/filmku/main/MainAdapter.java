package com.example.edogawa.filmku.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edogawa.filmku.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Edogawa on 12/1/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

    private List<MainDao> mData;

    public MainAdapter(List<MainDao> mData) {
        this.mData = mData;
    }


    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main,parent, false);
        MainHolder holder = new MainHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(MainAdapter.MainHolder holder, final int position) {
        holder.tvTitleRow.setText(mData.get(position).getTitle());
        Picasso.with(holder.imgRow.getContext())
                .load(mData.get(position).getImageUrl())
                .into(holder.imgRow);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),mData.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MainHolder extends RecyclerView.ViewHolder {

        private ImageView imgRow;
        private TextView tvTitleRow;

        public MainHolder(View itemView) {
            super(itemView);

            imgRow = (ImageView) itemView.findViewById(R.id.imgRow);
            tvTitleRow = (TextView) itemView.findViewById(R.id.titleRow);

        }
    }
}
