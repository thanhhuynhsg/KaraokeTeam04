package com.example.team04_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.team04_final_project.adapter.SeeDetailsVPAdapter;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.util.Timer;
import java.util.TimerTask;

public class SeeDetails extends AppCompatActivity {
    ImageView ivBackSeeDetails;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private Button btnRatingBar, btnChiDuong;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_see_details);

        ivBackSeeDetails = (ImageView) findViewById(R.id.iv_BackSeeDetails);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
            ivBackSeeDetails.setVisibility(View.VISIBLE);
        }
        ivBackSeeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.vp_SeeDetails);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        SeeDetailsVPAdapter seeDetailsVPAdapter = new SeeDetailsVPAdapter(this);
        viewPager.setAdapter(seeDetailsVPAdapter);
        dotscount = seeDetailsVPAdapter.getCount();
        dots = new ImageView[dotscount];
        for(int i = 0; i < dotscount; i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 5000, 5000);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        /////////////////////////////////////////////////////////////
        btnRatingBar = (Button) findViewById(R.id.btn_RatinngBar);
        btnChiDuong = (Button) findViewById(R.id.btn_ChiDuong);
        btnChiDuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeDetails.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(login == true){
                    final AlertDialog.Builder mbuilder = new AlertDialog.Builder(SeeDetails.this);
                    View mView = getLayoutInflater().inflate(R.layout.login_layout, null);
                    LoginButton btnLogin_Face = (LoginButton) mView.findViewById(R.id.login_Facebook);
                    //LoginButton btnGmail = (LoginButton) mView.findViewById(R.id.loginGmail);
                    Button btnCancelADSD= (Button) mView.findViewById(R.id.btn_CancelADSD);
                    mbuilder.setView(mView);
                    final AlertDialog dialog = mbuilder.create();
                    dialog.show();
                    btnLogin_Face.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(SeeDetails.this, "Login Facebook!", Toast.LENGTH_LONG).show();
                            SeeDetails.this.onClick();
                            dialog.cancel();
                        }
                    });
                    /*btnGmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(SeeDetails.this, "Login Gmail!", Toast.LENGTH_LONG).show();
                            SeeDetails.this.onClick();
                            dialog.cancel();
                        }
                    });*/
                    btnCancelADSD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                //}
                //else{
                //    SeeDetails.this.onClick();
                //}
            }
        });
    }

    public void onClick(){
        final AlertDialog.Builder builder_rating = new AlertDialog.Builder(SeeDetails.this);
        View mView_rating = getLayoutInflater().inflate(R.layout.rating_bar, null);
        final RatingBar ratingBar = (RatingBar) mView_rating.findViewById(R.id.rb_SeeDetails);
        final EditText etCommentRB = (EditText) mView_rating.findViewById(R.id.et_CommentSD);
        final Button btnCancelRB = (Button) mView_rating.findViewById(R.id.btn_CancelRB);
        final Button btnSubmitRB = (Button) mView_rating.findViewById(R.id.btn_SubmitRB);
        builder_rating.setView(mView_rating);
        final AlertDialog dialog_rating = builder_rating.create();
        dialog_rating.show();
        btnCancelRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_rating.cancel();
            }
        });
        btnSubmitRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating = String.valueOf(ratingBar.getRating());
                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
                dialog_rating.cancel();
            }
        });
    }
    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {

            SeeDetails.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    } else if(viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    }else if(viewPager.getCurrentItem() == 2){
                        viewPager.setCurrentItem(3);
                    }else if(viewPager.getCurrentItem() == 3){
                        viewPager.setCurrentItem(4);
                    }else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
