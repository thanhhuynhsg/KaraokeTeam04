package com.example.team04_final_project.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.team04_final_project.CreateKaraoke;
import com.example.team04_final_project.IShowDetail;
import com.example.team04_final_project.LoginActivity;
import com.example.team04_final_project.R;
import com.example.team04_final_project.SeeDetails;
import com.example.team04_final_project.adapter.KaraokeAdapter;
import com.example.team04_final_project.data.Karaoke;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class KaraokeFragment extends Fragment implements IShowDetail{
    private RecyclerView rcvListKaraoke;
    private KaraokeAdapter karaokeAdapter;
    private List<Karaoke> karaokeList;
    private DatabaseReference mData;
    public static String mID,mName,mAddress,mPrice,mPhone,mDescription,mLogo;
    public static Float mLat,mLon;
    private FirebaseAuth mAuth;
    private LoginActivity loginActivity;
    public KaraokeFragment() {
        // Required empty public constructor
    }
    private FloatingActionButton fabCreateKaraoke;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_karaoke, container, false);
        mAuth = FirebaseAuth.getInstance();

        karaokeList = new ArrayList<Karaoke>();
        rcvListKaraoke = (RecyclerView) view.findViewById(R.id.rcv_ListKaraoke);
        rcvListKaraoke.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvListKaraoke.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        rcvListKaraoke.addItemDecoration(dividerItemDecoration);

        karaokeAdapter = new KaraokeAdapter(karaokeList,this);
        rcvListKaraoke.setAdapter(karaokeAdapter);

        mData = FirebaseDatabase.getInstance().getReference();

        mData.child("Karaoke").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Karaoke kara = dataSnapshot.getValue(Karaoke.class);
                karaokeList.add(new Karaoke(kara.getmId(),kara.getmLat(),kara.getmLon(),kara.getmName(),kara.getmAddress(),kara.getmPhone(),kara.getmPrice(),kara.getmLogo(),kara.getmDescription()));
                karaokeAdapter.notifyDataSetChanged();
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

        fabCreateKaraoke =(FloatingActionButton) view.findViewById(R.id.fab_CreateKaraoke);
        fabCreateKaraoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (mAuth.getCurrentUser() != null) {
                        openCreateKaraoke();
                    }else {
                        Toast.makeText(getActivity(), "Vui lòng đăng nhập!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
            }
        });

        return view;
    }

    public void openCreateKaraoke() {
        Intent intent = new Intent(getActivity(),CreateKaraoke.class);
        startActivity(intent);
    }
    @Override
    public void onItemClick(int position) {
        mID = karaokeAdapter.getKaraokeList().get(position).getmId();
        mName = karaokeAdapter.getKaraokeList().get(position).getmName();
        mAddress = karaokeAdapter.getKaraokeList().get(position).getmAddress();
        mPrice = karaokeAdapter.getKaraokeList().get(position).getmPrice();
        mPhone = karaokeAdapter.getKaraokeList().get(position).getmPhone();
        mDescription = karaokeAdapter.getKaraokeList().get(position).getmDescription();
        mLat = karaokeAdapter.getKaraokeList().get(position).getmLat();
        mLon = karaokeAdapter.getKaraokeList().get(position).getmLon();
        mLogo =  karaokeAdapter.getKaraokeList().get(position).getmLogo();
        Intent intent = new Intent(getActivity().getBaseContext(),SeeDetails.class);
        intent.putExtra("ID",mID);
        intent.putExtra("NAME",mName);
        intent.putExtra("ADDRESS",mAddress);
        intent.putExtra("PRICE",mPrice);
        intent.putExtra("PHONE",mPhone);
        intent.putExtra("DESC",mDescription);
        intent.putExtra("LOGO",mLogo);
        intent.putExtra("LAT",mLat);
        intent.putExtra("LON",mLon);
        getActivity().startActivity(intent);

    }
}
