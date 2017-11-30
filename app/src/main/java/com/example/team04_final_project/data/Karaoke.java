package com.example.team04_final_project.data;

/**
 * Created by thand on 22/11/2017.
 */

public class Karaoke {
    private int mLogo;
    private String mName;
    private String mAddress;
    private String mPrice;

    public Karaoke(int mLogo, String mName, String mAddress, String mPrice) {
        this.mLogo = mLogo;
        this.mName = mName;
        this.mAddress = mAddress;
        this.mPrice = mPrice;
    }

    public int getmLogo() {
        return mLogo;
    }

    public void setmLogo(int mLogo) {
        this.mLogo = mLogo;
    }

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
