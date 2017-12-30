package com.example.team04_final_project;

import android.content.Context;
import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team04_final_project.data.Karaoke;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.team04_final_project.data.reservation;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.PatternSyntaxException;

/**
 * Created by ThanhHuynh on 12/21/2017.
 */


public class ReservationActivity extends AppCompatActivity {

    GridView gv;
    Button btnxn;
    TextView chon;
    Context context;
    String dc;
    ArrayList <String> list = new ArrayList<String>();
    ArrayList <String> list_item = new ArrayList<String>();
    ArrayList <String> getlist = new ArrayList<String>();

    int count=0;

    private DatabaseReference mDatabase;


    //Thông tin của người đăng nhập
    private FirebaseAuth mAuth;
    private String namedn, emaildn;
    String aaa="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        gv=(GridView)findViewById(R.id.gridView);
        btnxn=(Button)findViewById(R.id.btnxacnhan);
        chon=(TextView)findViewById(R.id.txtchon);

        //Lấy thông tin người đăng nhập
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            namedn = user.getDisplayName();
            emaildn = user.getEmail();
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mFirebaseDatabaseReference.child("DatCho").child("datcho").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dc = (String) dataSnapshot.getValue();
                getlist = new ArrayList<String>(Arrays.asList(dc.split(" ")));

                for(int i=1; i<=24; i++){
                    list.add(""+i);
                }
                xulychuoi(list, getlist);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReservationActivity.this, android.R.layout.simple_list_item_1, list);

        gv.setAdapter(adapter);
        gv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        gv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                count = count+1;
                actionMode.setTitle(count + " da chon");

                list_item.add(list.get(i));
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.my_context_menu,  menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.selectid:
                        for(String msg : list_item){
                            adapter.remove(msg);
                        }

                        for(int i=0; i < list_item.size(); i++){
                            aaa += list_item.get(i)+" ";
                        }

                        chon.setText(aaa);
                        count=0;
                        actionMode.finish();
                        return true;
                    //break;

                    default:
                        return false;
                }

                // return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });

        btnxn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                String datchoId = mDatabase.push().getKey();

                reservation datcho = new reservation(namedn, emaildn, aaa);

                mDatabase.child("DatCho").setValue(datcho, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null){
                            Toast.makeText(ReservationActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ReservationActivity.this,"Thêm thất bại, Vui lòng thử lại!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void xulychuoi(ArrayList list1, ArrayList list2) {

        for(int i = 0; i < list1.size(); i++){
            for(int j =0; j<list2.size(); j++){
                if(list1.get(i).equals(list2.get(j))){
                    list1.remove(i);
                }
            }
        }

    }

}