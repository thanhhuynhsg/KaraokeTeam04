package com.example.team04_final_project.data;

/**
 * Created by thand on 22/11/2017.
 */

public class Users {
    private String uId;
    private String uName;
    private String uEmail;
    private String uAvata;

    public Users() {

    }

    public Users(String uId, String uName, String uEmail, String uAvata) {
        this.uId = uId;
        this.uName = uName;
        this.uEmail = uEmail;
        this.uAvata = uAvata;
    }

    public String getuId() {return uId;}

    public void setuId(String uId) {this.uId = uId;}

    public String getuName() {return uName;}

    public void setuName(String uName) {this.uName = uName;}

    public String getuEmail() {return uEmail;}

    public void setuEmail(String uEmail) {this.uEmail = uEmail;}

    public String getuAvata() {return uAvata;}

    public void setuAvata(String mAvata) {this.uAvata = uAvata;}
}
