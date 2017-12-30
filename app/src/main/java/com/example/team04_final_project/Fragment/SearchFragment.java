package com.example.team04_final_project.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team04_final_project.IShowDetail;
import com.example.team04_final_project.LoginActivity;
import com.example.team04_final_project.R;
import com.example.team04_final_project.ReservationActivity;
import com.example.team04_final_project.SeeDetails;
import com.example.team04_final_project.adapter.KaraokeAdapter;
import com.example.team04_final_project.data.Karaoke;
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
public class SearchFragment extends Fragment implements IShowDetail {
    private DatabaseReference mData;
    private RecyclerView rcvListKaraoke;
    private KaraokeAdapter karaokeAdapter;
    private List<Karaoke> karaokeList;
    public static String mID,mName,mAddress,mPrice,mPhone,mDescription,mLogo;
    public static Float mLat,mLon;
    EditText name,address,price1,price2;
    public SearchFragment() {
        // Required empty public constructor
    }

    Button btnSearch, btnLogout, btndp;
    LoginActivity loginActivity;
    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mAuth = FirebaseAuth.getInstance();
        name = (EditText)view.findViewById(R.id.editText2);
        address = (EditText)view.findViewById(R.id.editText5);
        price1 = (EditText)view.findViewById(R.id.editText3);
        price2 = (EditText)view.findViewById(R.id.editText4);
        karaokeList = new ArrayList<Karaoke>();
        rcvListKaraoke = (RecyclerView) view.findViewById(R.id.rcv_ListKaraoke);
        //rcvListKaraoke.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvListKaraoke.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        rcvListKaraoke.addItemDecoration(dividerItemDecoration);

        karaokeAdapter = new KaraokeAdapter(karaokeList,this);

        rcvListKaraoke.setAdapter(karaokeAdapter);
        mAuth = FirebaseAuth.getInstance();

        btnSearch = (Button) view.findViewById(R.id.btn_Search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                karaokeList.clear();
                mData = FirebaseDatabase.getInstance().getReference();
                mData.child("Karaoke").orderByChild("mName").startAt(name.getText().toString())
                        .addChildEventListener(new ChildEventListener() {
                //mData.child("Karaoke").orderByChild("mPrice").startAt(149).addChildEventListener(new ChildEventListener() {
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
                    Toast.makeText(getActivity(), "Loi!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btndp = (Button) view.findViewById(R.id.btndp);
        btndp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReservationActivity.class);
                startActivity(intent);
            }
        });

        btnLogout = (Button)view.findViewById(R.id.btn_logout);
        if (mAuth.getCurrentUser() == null) {
            btnLogout.setVisibility(View.INVISIBLE);
        }
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
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
