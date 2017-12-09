package com.example.team04_final_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CreateKaraoke extends AppCompatActivity {
    Button chonhinh, luu, huy;
    EditText ten,diachi, sdt, gia , mota;
    ImageView hinhdaidien;
    DatabaseReference mData;
    String Karaoke_id;
    float lat,lon;

  //  final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_karaoke);
        // Ánh xạ
        chonhinh = (Button)findViewById(R.id.btn_selectimage);
        luu = (Button)findViewById(R.id.btn_save);
        huy = (Button)findViewById(R.id.btn_cancel);
        ten = (EditText)findViewById(R.id.edit_name);
        diachi = (EditText)findViewById(R.id.edit_address);
        sdt = (EditText)findViewById(R.id.edit_phone);
        gia = (EditText)findViewById(R.id.edit_price);
        mota = (EditText)findViewById(R.id.edit_description);
        hinhdaidien = (ImageView)findViewById(R.id.img_avatar);
        mData = FirebaseDatabase.getInstance().getReference();
        // chọn hình
        chonhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });

        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new GetCoordinates().execute(diachi.getText().toString().replace(" ","+"));
            }

        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private  class  GetCoordinates extends AsyncTask<String,Void,String> {
        ProgressDialog dialog = new ProgressDialog(CreateKaraoke.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Vui lòng chờ trong giây lát ...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String response;
            try{
                String address = strings[0];
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s",address);
                response = http.getHTTPData(url);
                return  response;
            }catch (Exception e){
            }
                return  null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);
                String lat_ = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString();
                String lng_ = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString();
                // Ép kiểu thành FLoat
               lat = Float.parseFloat(lat_);
               lon = Float.parseFloat(lng_);
          //   Toast.makeText(CreateKaraoke.this,lat+"/"+lon,Toast.LENGTH_SHORT).show();

                // Chuyển ảnh từ byte array thành chuỗi String
                byte[] anh = getByteArrayFromImageView(hinhdaidien);
                String Chuoihinh = Base64.encodeToString(anh,Base64.DEFAULT);
                // Tạo key id
                Karaoke_id = mData.push().getKey();

                // Chép data vào mảng
                Karaoke karaoke = new Karaoke(Karaoke_id,lat, lon,ten.getText().toString(),
                        diachi.getText().toString(),sdt.getText().toString(),gia.getText().toString(),
                        Chuoihinh,mota.getText().toString());

                // Đưa lên Database
                mData.child("Karaoke").child(Karaoke_id).setValue(karaoke, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                        if(databaseError == null){
                            Toast.makeText(CreateKaraoke.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(CreateKaraoke.this,"Thêm thất bại, Vui lòng thử lại!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                // Kết thúc và thoát màn hình
                finish();
            }catch (JSONException e){
                e.printStackTrace();
            }
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
