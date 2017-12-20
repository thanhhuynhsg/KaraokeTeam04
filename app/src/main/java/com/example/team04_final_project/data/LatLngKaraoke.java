package com.example.team04_final_project.data;

/**
 * Created by ToanLam on 12/19/2017.
 */

public class LatLngKaraoke {
        private double lat;
        private double lng;
        private String title;
        private String snippet;
        public LatLngKaraoke(){

        }

        public LatLngKaraoke(double lat, double lng, String title, String snippet) {
            this.lat = lat;
            this.lng = lng;
            this.title = title;
            this.snippet = snippet;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lonng) {this.lng = lng;}

        public String getTitle() {return title;}

        public void setTitle(String title) {this.title = title;}

        public String getSnippet() {return snippet;}

        public void setSnippet(String snippet) {this.snippet = snippet;}
}

