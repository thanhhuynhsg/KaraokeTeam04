package com.example.team04_final_project.data;

/**
 * Created by ThanhHuynh on 12/21/2017.
 */

public class reservation {
    private String ten;
    private String email;
    private String datcho;

    public  reservation(){

    }

    public reservation(String ten, String email, String datcho) {
        this.ten = ten;
        this.email= email;
        this.datcho= datcho;

    }
    public String getTen(){return ten;}
    public  void setTen(String ten) {this.ten = ten;}

    public String getEmail(){return email;}
    public  void setEmail(String email) {this.email = email;}

    public String getDatcho(){return datcho;}
    public  void setDatcho(String datcho) {this.datcho = datcho;}

}