package com.example.team04_final_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.team04_final_project.Fragment.KaraokeFragment;
import com.example.team04_final_project.data.Karaoke;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateKaraoke extends AppCompatActivity {
    Button chuphinh, chonhinh, luu, huy;
    EditText ten,diachi, sdt, gia , mota;
    ImageView hinhdaidien;
    DatabaseReference mData;
    String Karaoke_id;
    ProgressBar pg;
    double lat,lon;

    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_karaoke);
        chuphinh = (Button)findViewById(R.id.btn_photograph);
        chonhinh = (Button)findViewById(R.id.btn_selectimage);
        luu = (Button)findViewById(R.id.btn_save);
        huy = (Button)findViewById(R.id.btn_cancel);
        ten = (EditText)findViewById(R.id.edit_name);
        diachi = (EditText)findViewById(R.id.edit_address);
        sdt = (EditText)findViewById(R.id.edit_phone);
        gia = (EditText)findViewById(R.id.edit_price);
        mota = (EditText)findViewById(R.id.edit_description);
        hinhdaidien = (ImageView)findViewById(R.id.img_avatar);
        pg = (ProgressBar)findViewById(R.id.progressBar);
        mData = FirebaseDatabase.getInstance().getReference();
        chonhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });
        chuphinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  takePicture();
            }
        });
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //đổi dữ liệu từ byte array sang string
                byte[] anh = getByteArrayFromImageView(hinhdaidien);
                String Chuoihinh = Base64.encodeToString(anh,Base64.DEFAULT);

                Karaoke_id = mData.push().getKey();
                Karaoke karaoke = new Karaoke(10.8398677, 106.5998327,ten.getText().toString(),diachi.getText().toString(),sdt.getText().toString(),gia.getText().toString(),Chuoihinh,mota.getText().toString());

                //Đổi dữ liệu sang dạng Json và đẩy lên Firebase bằng hàm Push
                mData.child("Karaoke").child(Karaoke_id).setValue(karaoke, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        pg.setVisibility(View.VISIBLE);
                        if(databaseError == null){
                            Toast.makeText(CreateKaraoke.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(CreateKaraoke.this,"Thêm thất bại, Vui lòng thử lại!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                finish();
//                Intent intent = new Intent(CreateKaraoke.this, KaraokeFragment.class);
//                Bundle bun = new Bundle();
//                bun.putString("key",Karaoke_id);
//                intent.putExtras(bun);
//                startActivity(intent);
            }
        });

    }

    private  void takePicture(){
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
        }
        catch (Exception e){
            Toast.makeText(this,"Vui lòng thử lại sau!", Toast.LENGTH_LONG).show();
        }
    }
    private  void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CHOOSE_PHOTO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CHOOSE_PHOTO){

                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    hinhdaidien.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else if (requestCode == REQUEST_TAKE_PHOTO){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                hinhdaidien.setImageBitmap(bitmap);
            }
        }
    }
    private  byte[] getByteArrayFromImageView(ImageView img){
        BitmapDrawable drawable = (BitmapDrawable)img.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}
