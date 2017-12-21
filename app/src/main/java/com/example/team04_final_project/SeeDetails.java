package com.example.team04_final_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team04_final_project.Fragment.GPSFragment;
import com.example.team04_final_project.adapter.ReviewAndCommentAdapter;
import com.example.team04_final_project.adapter.SeeDetailsVPAdapter;
import com.example.team04_final_project.data.Karaoke;
import com.example.team04_final_project.data.Rating_Comment;
import com.example.team04_final_project.data.ReviewAndComment;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


public class SeeDetails extends AppCompatActivity {
    ImageView ivBackSeeDetails;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private Button btnRatingBar, btnChiDuong;
    private RatingBar ratingBar;
    private GoogleSignInClient mGoogleSignInClient;
    public String mID,mName,mAddress,mPrice,mPhone,mDesc,mLogo;
    Float mLat,mLon;
    private List<Karaoke> karaokeList;
    private RecyclerView rcReviewAndComment;
    private List<Rating_Comment> reviewAndCommentList;
    private ReviewAndCommentAdapter reviewAndCommentAdapter;
    //Thông tin của người đăng nhập
    private FirebaseAuth mAuth;
    private LoginActivity loginActivity;
    private String iddn,namedn,avatadn;
    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_see_details);

        final TextView name = (TextView)findViewById(R.id.txt_name);
        final TextView address = (TextView)findViewById(R.id.txt_address);
        TextView price = (TextView)findViewById(R.id.txt_price);
        TextView phone = (TextView)findViewById(R.id.txt_phone);
        TextView description = (TextView)findViewById(R.id.txt_description);

        mData = FirebaseDatabase.getInstance().getReference();
        //Lấy thông tin người đăng nhập
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
             iddn = user.getUid();
             namedn = user.getDisplayName();
             avatadn = user.getPhotoUrl().toString();

        }

        //List Comment
        rcReviewAndComment = (RecyclerView) findViewById(R.id.rcv_ListComment);
        ///List Comment
        reviewAndCommentList = new ArrayList<Rating_Comment>();

        rcReviewAndComment.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcReviewAndComment.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        rcReviewAndComment.addItemDecoration(dividerItemDecoration);
        reviewAndCommentAdapter = new ReviewAndCommentAdapter(reviewAndCommentList, this);
        rcReviewAndComment.setAdapter(reviewAndCommentAdapter);

//        Rating_Comment raco = dataSnapshot.getValue(Rating_Comment.class);
//        reviewAndCommentList.add(new Rating_Comment(raco.getIdKaraoke(),raco.getIdUsser(),raco.getuRating(),raco.getuComment(),raco.getuName(),raco.getuAvata()));
//        reviewAndCommentAdapter.notifyDataSetChanged();
        mData = FirebaseDatabase.getInstance().getReference();
       // final Query query = mData.child("Rating_Comment").orderByChild("idKaraoke").equalTo(mID);
        mData.child("Rating_Comment").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Rating_Comment raco = dataSnapshot.getValue(Rating_Comment.class);
                reviewAndCommentList.add(new Rating_Comment(raco.getIdKaraoke(),raco.getIdUsser(),raco.getuRating(),raco.getuComment(),raco.getuName(),raco.getuAvata()));
                reviewAndCommentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Show data
        karaokeList = new ArrayList<Karaoke>();

        //get dữ liêu từ KaraokeFragment
        Intent intent = getIntent();
        mID = intent.getStringExtra("ID");
        mName= intent.getStringExtra("NAME");
        mAddress= intent.getStringExtra("ADDRESS");
        mPrice= intent.getStringExtra("PRICE");
        mPhone= intent.getStringExtra("PHONE");
        mDesc= intent.getStringExtra("DESC");
        mLat = intent.getFloatExtra("LAT",0);
        mLon = intent.getFloatExtra("LON",0);
        mLogo = intent.getStringExtra("LOGO");

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
        SeeDetailsVPAdapter seeDetailsVPAdapter = new SeeDetailsVPAdapter(this,mLogo);
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
                Intent intent = new Intent(SeeDetails.this, DirectionActivity.class);
                String dAddress = address.getText().toString();
                intent.putExtra("ADDRESS",dAddress);
                startActivity(intent);
            }
        });
        btnRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mAuth.getCurrentUser() != null){
                    SeeDetails.this.onClick();
                }
                else {
                    Toast.makeText(SeeDetails.this, "Vui lòng đăng nhập!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SeeDetails.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        name.setText(mName);
        address.setText(mAddress);
        price.setText(mPrice);
        phone.setText(mPhone);
        description.setText(mDesc);
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
                String id = UUID.randomUUID().toString();
                String rating = String.valueOf(ratingBar.getRating());
                Rating_Comment raco = new Rating_Comment(mID,iddn,rating,etCommentRB.getText().toString(),namedn,avatadn);
                mData.child("Rating_Comment").child(id).setValue(raco, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null){
                            Toast.makeText(SeeDetails.this,"Gửi thành công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SeeDetails.this,"Gửi thất bại, Vui lòng thử lại!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
