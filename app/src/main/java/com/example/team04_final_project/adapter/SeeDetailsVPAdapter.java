package com.example.team04_final_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.team04_final_project.R;

/**
 * Created by thand on 24/11/2017.
 */

public class SeeDetailsVPAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
   // private Integer [] images = {R.drawable.firework,R.drawable.firework,R.drawable.firework,R.drawable.firework,R.drawable.firework};
    String avata;

    public SeeDetailsVPAdapter(Context context,String avata) {
        this.context = context;
        this.avata = avata;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_viewpager_seedetails, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_ViewPagerSD);
        byte[] img = Base64.decode(avata,Base64.DEFAULT);
        Bitmap bmLogo = BitmapFactory.decodeByteArray(img,0,img.length);
        imageView.setImageBitmap(bmLogo);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }

}
