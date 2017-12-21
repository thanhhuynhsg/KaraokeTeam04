package com.example.team04_final_project.data;

/**
 * Created by thand on 22/11/2017.
 */

public class Rating_Comment {

    private String idKaraoke;
    private String idUsser;
    private String uRating;
    private String uComment;
    private String uName;
    private String uAvata;
    public  Rating_Comment(){

    }

    public Rating_Comment(String idKaraoke, String idUsser, String uRating, String uComment, String uName,String uAvata) {
        this.idKaraoke = idKaraoke;
        this.idUsser = idUsser;
        this.uRating = uRating;
        this.uComment = uComment;
        this.uName = uName;
        this.uAvata = uAvata;
    }

    public String getIdKaraoke() {return idKaraoke;}

    public void setIdKaraoke(String idKaraoke) {this.idKaraoke = idKaraoke;}

    public String getIdUsser() {return idUsser;}

    public void setIdUsser(String idUsser) {this.idUsser = idUsser;}

    public String getuRating() {return uRating;}

    public void setuRating(String uRating) {this.uRating = uRating;}

    public String getuComment() {return uComment;}

    public void setuComment(String uComment) {this.uComment = uComment;}

    public String getuName() {return uName;}

    public void setuName(String uName) {this.uName = uName;}

    public String getuAvata() {return uAvata;}

    public void setuAvata(String uAvata) {this.uAvata = uAvata;}
}
