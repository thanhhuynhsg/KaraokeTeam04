package com.example.team04_final_project.data;

/**
 * Created by thand on 22/11/2017.
 */

public class Rating_Comment {

    private String idKaraoke;
    private String idUsser;
    private int uRating;
    private String uComment;

    public  Rating_Comment(){

    }

    public Rating_Comment(String idKaraoke, String idUsser, int uRating, String uComment) {
        this.idKaraoke = idKaraoke;
        this.idUsser = idUsser;
        this.uRating = uRating;
        this.uComment = uComment;
    }

    public String getIdKaraoke() {return idKaraoke;}

    public void setIdKaraoke(String idKaraoke) {this.idKaraoke = idKaraoke;}

    public String getIdUsser() {return idUsser;}

    public void setIdUsser(String idUsser) {this.idUsser = idUsser;}

    public int getuRating() {return uRating;}

    public void setuRating(int uRating) {this.uRating = uRating;}

    public String getuComment() {return uComment;}

    public void setuComment(String uComment) {this.uComment = uComment;}
}
