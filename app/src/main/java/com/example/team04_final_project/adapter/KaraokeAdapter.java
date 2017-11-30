package com.example.team04_final_project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.team04_final_project.R;
import com.example.team04_final_project.data.Karaoke;

import java.util.List;

/**
 * Created by thand on 22/11/2017.
 */

public class KaraokeAdapter extends RecyclerView.Adapter<KaraokeAdapter.ViewHolder>{
    private List<Karaoke> karaokeList;
    private Context context;
    private OnCallBack mListener;
    public KaraokeAdapter(List<Karaoke> karaokeList, Context context, OnCallBack mListener) {
        this.karaokeList = karaokeList;
        this.context = context;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_karaoke, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Karaoke karaoke = karaokeList.get(position);
        holder.ivLogo.setImageResource(karaoke.getmLogo());
        holder.tvName.setText(karaoke.getmName());
        holder.tvAddress.setText(karaoke.getmAddress());
        holder.tvPrice.setText(karaoke.getmPrice());
    }

    @Override
    public int getItemCount() {
        return karaokeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLogo;
        TextView tvName, tvAddress, tvPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            ivLogo = (ImageView) itemView.findViewById(R.id.iv_Logo);
            tvName = (TextView) itemView.findViewById(R.id.tv_Name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_Address);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_Price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(getPosition());
                }
            });
        }
    }
    public interface OnCallBack{
        void onItemClicked(int position);
    }
}
