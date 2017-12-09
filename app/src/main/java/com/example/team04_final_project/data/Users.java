package com.example.team04_final_project.data;

/**
 * Created by thand on 22/11/2017.
 */

public class Users {
    private String uName;
    private String uEmail;
    private String mAvata;

    public Users() {

    }

    public Users(String uName, String uEmail, String mAvata) {
        this.uName = uName;
        this.uEmail = uEmail;
        this.mAvata = mAvata;
    }

    public String getuName() {return uName;}

    public void setuName(String uName) {this.uName = uName;}

    public String getuEmail() {return uEmail;}

    public void setuEmail(String uEmail) {this.uEmail = uEmail;}

    public String getmAvata() {return mAvata;}

    public void setmAvata(String mAvata) {this.mAvata = mAvata;}
}
