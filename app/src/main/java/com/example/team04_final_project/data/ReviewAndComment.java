package com.example.team04_final_project.data;

/**
 * Created by thand on 13/12/2017.
 */

public class ReviewAndComment {
    private String mId;
    private int mAvatar;
    private String mName;
    private Float mRateing;
    private String mContent;

    public ReviewAndComment(String mId, int mAvatar, String mName, Float mRateing, String mContent) {
        this.mId = mId;
        this.mAvatar = mAvatar;
        this.mName = mName;
        this.mRateing = mRateing;
        this.mContent = mContent;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public int getmAvatar() {
        return mAvatar;
    }

    public void setmAvatar(int mAvatar) {
        this.mAvatar = mAvatar;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Float getmRateing() {
        return mRateing;
    }

    public void setmRateing(Float mRateing) {
        this.mRateing = mRateing;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }
}
