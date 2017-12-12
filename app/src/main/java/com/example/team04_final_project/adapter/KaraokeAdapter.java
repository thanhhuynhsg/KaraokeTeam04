package com.example.team04_final_project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.team04_final_project.IShowDetail;
import com.example.team04_final_project.R;
import com.example.team04_final_project.data.Karaoke;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.List;

/**
 * Created by thand on 22/11/2017.
 */

public class KaraokeAdapter extends RecyclerView.Adapter<KaraokeAdapter.ViewHolder>{
    private List<Karaoke> karaokeList;
    private IShowDetail _action;

    public List<Karaoke> getKaraokeList() {
        return karaokeList;
    }

    public void setKaraokeList(List<Karaoke> karaokeList) {
        this.karaokeList = karaokeList;
    }

    public KaraokeAdapter(List<Karaoke> karaokeList, IShowDetail _action) {
        this.karaokeList = karaokeList;
        this._action = _action;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_karaoke, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Karaoke karaoke = karaokeList.get(position);
        byte[] img = Base64.decode(karaoke.getmLogo().toString(),Base64.DEFAULT);
        Bitmap bmLogo = BitmapFactory.decodeByteArray(img,0,img.length);
        holder.ivLogo.setImageBitmap(bmLogo);
        holder.tvName.setText(karaoke.getmName());
        holder.tvAddress.setText(karaoke.getmAddress());
        holder.tvPrice.setText(karaoke.getmPrice());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(onItem_Click);
    }
    private  View.OnClickListener onItem_Click = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int position = Integer.valueOf(view.getTag().toString()).intValue();
            _action.onItemClick(position);
        }
    };
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
        }
    }
}
