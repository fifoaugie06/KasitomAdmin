package t.com.kasitomadmin.model;

import java.io.Serializable;

public class dataKamus implements Serializable {
    private String judul;
    private String arti;
    private String key;
    private boolean expanded;

    public dataKamus(){

    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getJudul() {
        return judul;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }

    public String getArti() {
        return arti;
    }

    public dataKamus(String judul, String arti){
        this.judul = judul;
        this.arti = arti;
        this.expanded = false;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}

