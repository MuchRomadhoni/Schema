package com.example.myschedule;

public class FirebaseData {
    String mapel,semt,dosen;

    public FirebaseData(String mapel, String semt, String dosen) {
        this.mapel = mapel;
        this.semt = semt;
        this.dosen = dosen;
    }

    public FirebaseData() {

    }

    public String getMapel() {
        return mapel;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }

    public String getSemt() {
        return semt;
    }

    public void setSemt(String semt) {
        this.semt = semt;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }
}
