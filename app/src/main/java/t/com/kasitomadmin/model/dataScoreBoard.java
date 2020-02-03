package t.com.kasitomadmin.model;

import java.util.Date;

public class dataScoreBoard {
    String nama, photoURI, correct, nilai, size, key;
    long scoreUserTime;

    public dataScoreBoard(){

    }

    public dataScoreBoard(String nama, String photoURI, String correct, String nilai, String size) {
        this.nama = nama;
        this.photoURI = photoURI;
        this.correct = correct;
        this.nilai = nilai;
        this.size = size;
        scoreUserTime = new Date().getTime();
    }

    public String getNama() {
        return nama;
    }

    public String getPhotoURI() {
        return photoURI;
    }

    public String getCorrect() {
        return correct;
    }

    public String getNilai() {
        return nilai;
    }

    public String getSize() {
        return size;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public long getScoreUserTime() {
        return scoreUserTime;
    }
}
