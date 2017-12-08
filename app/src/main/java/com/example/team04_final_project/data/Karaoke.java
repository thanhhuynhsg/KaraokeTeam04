package com.example.team04_final_project.data;

/**
 * Created by thand on 22/11/2017.
 */

public class Karaoke {
    private float mLat;
    private float mLon;
    private String mName;
    private String mAddress;
    private String mPhone;
    private String mPrice;
    private String mLogo;
    private String mDescription;
    public Karaoke(){

    }
    public Karaoke(float mLat, float mLon, String mName, String mAddress, String mPhone, String mPrice, String mLogo, String mDescription) {
        this.mLat = mLat;
        this.mLon = mLon;
        this.mName = mName;
        this.mAddress = mAddress;
        this.mPhone = mPhone;
        this.mPrice = mPrice;
        this.mLogo = mLogo;
        this.mDescription = mDescription;
    }

    public float getmLat() {return mLat;}

    public void setmLat(float mLat) {this.mLat = mLat;}

    public float getmLon() {return mLon;}

    public void setmLon(float mLon) {this.mLon = mLon;}

    public String getmPhone() {return mPhone;}

    public void setmPhone(String mPhone) {this.mPhone = mPhone;}

    public String getmDescription() {return mDescription;}

    public void setmDescription(String mDescription) {this.mDescription = mDescription;}

    public String getmLogo() {return mLogo;}

    public void setmLogo(String mLogo) {this.mLogo = mLogo;}

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }
}
