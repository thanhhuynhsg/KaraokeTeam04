package com.example.team04_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.team04_final_project.Fragment.GPSFragment;
import com.example.team04_final_project.Fragment.KaraokeFragment;
import com.example.team04_final_project.Fragment.SearchFragment;
import com.example.team04_final_project.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    GPSFragment gpsFragment;
    KaraokeFragment karaokeFragment;
    SearchFragment searchFragment;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_timquanhday:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.navigation_karaoke:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.navigation_search:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        gpsFragment = new GPSFragment();
        karaokeFragment = new KaraokeFragment();
        searchFragment = new SearchFragment();
        adapter.addFragment(gpsFragment);
        adapter.addFragment(karaokeFragment);
        adapter.addFragment(searchFragment);
        viewPager.setAdapter(adapter);
    }
}
