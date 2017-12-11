package com.example.team04_final_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.team04_final_project.R;
import com.example.team04_final_project.SeeDetails;
import com.example.team04_final_project.data.Karaoke;

import java.util.List;

/**
 * Created by ToanLam on 12/9/2017.
 */

public class SeeDetailsInforAdapter  extends BaseAdapter{
    private Context context;
    private List<Karaoke> karaokeList;
    public  SeeDetailsInforAdapter (Context context, List<Karaoke> karaokeList){
        this.context = context;
        this.karaokeList = karaokeList;
    }

    @Override
    public int getCount() {
        return karaokeList.size();
    }

    @Override
    public Object getItem(int i) {
        return karaokeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_see_details,parent,false);
        }

        Karaoke currentKaraoke = (Karaoke)getItem(position);

        TextView  name = (TextView)convertView.findViewById(R.id.txt_name);
        TextView  address = (TextView)convertView.findViewById(R.id.txt_address);
        TextView  price = (TextView)convertView.findViewById(R.id.txt_price);
        TextView  phone = (TextView)convertView.findViewById(R.id.txt_phone);
        TextView  description = (TextView)convertView.findViewById(R.id.txt_description);

        name.setText(currentKaraoke.getmName());
        address.setText(currentKaraoke.getmAddress());
        price.setText(currentKaraoke.getmPrice());
        phone.setText(currentKaraoke.getmPhone());
        description.setText(currentKaraoke.getmDescription());

        return  convertView;
    }
}
