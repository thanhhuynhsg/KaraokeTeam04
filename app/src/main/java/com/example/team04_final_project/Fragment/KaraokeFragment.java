package com.example.team04_final_project.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.team04_final_project.CreateKaraoke;
import com.example.team04_final_project.R;
import com.example.team04_final_project.SeeDetails;
import com.example.team04_final_project.adapter.KaraokeAdapter;
import com.example.team04_final_project.data.Karaoke;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class KaraokeFragment extends Fragment implements KaraokeAdapter.OnCallBack{
    private RecyclerView rcvListKaraoke;
    private KaraokeAdapter karaokeAdapter;
    private List<Karaoke> karaokeList;
    public KaraokeFragment() {
        // Required empty public constructor
    }
    private FloatingActionButton fabCreateKaraoke;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_karaoke, container, false);


        karaokeList = new ArrayList<Karaoke>();
        for(int i = 1; i <= 20; i++) {
            karaokeList.add(new Karaoke(R.drawable.mic, "Karaoke 01", "Quan 2, Ho Chi Minh", "100.000đ"));
            karaokeList.add(new Karaoke(R.drawable.mic, "Karaoke 02", "Quan 5, Ho Chi Minh", "200.000đ"));
            karaokeList.add(new Karaoke(R.drawable.mic, "Karaoke 03", "Quan 10, Ho Chi Minh", "300.000đ"));
            karaokeList.add(new Karaoke(R.drawable.mic, "Karaoke 04", "Quan 3, Ho Chi Minh", "400.000đ"));
            karaokeList.add(new Karaoke(R.drawable.mic, "Karaoke 05", "Quan 1, Ho Chi Minh", "500.000đ"));
        }


        rcvListKaraoke = (RecyclerView) view.findViewById(R.id.rcv_ListKaraoke);
        rcvListKaraoke.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvListKaraoke.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        rcvListKaraoke.addItemDecoration(dividerItemDecoration);
        karaokeAdapter = new KaraokeAdapter(karaokeList, getActivity(), this);
        rcvListKaraoke.setAdapter(karaokeAdapter);


        fabCreateKaraoke =(FloatingActionButton) view.findViewById(R.id.fab_CreateKaraoke);
        fabCreateKaraoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(login == true){
                    final AlertDialog.Builder mbuilder = new AlertDialog.Builder(getActivity());
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
                            Toast.makeText(getActivity(), "Login Facebook!", Toast.LENGTH_LONG).show();
                            openCearteKaraoke();
                            dialog.cancel();
                        }
                    });
                        /*btnGmail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(SeeDetails.this, "Login Gmail!", Toast.LENGTH_LONG).show();
                                openCearteKaraoke();
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
                //else {
                //    openCearteKaraoke();
                //}
            }
        });
        return view;
    }

    public void openCearteKaraoke() {
        Intent intent = new Intent(getActivity(),CreateKaraoke.class);
        startActivity(intent);
    }
    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getActivity(),SeeDetails.class);
        startActivity(intent);
        Toast.makeText(getActivity(),"Position is " + position, Toast.LENGTH_LONG).show();
    }
}
