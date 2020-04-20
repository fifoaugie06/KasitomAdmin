package t.com.kasitomadmin.model;

import java.io.Serializable;

public class dataQuiz implements Serializable {
    private String soal;
    private String optionA, optionB, optionC, optionD, jawaban;
    private String key;

    public dataQuiz(){

    }

    public dataQuiz(String soal, String optionA, String optionB, String optionC, String optionD, String jawaban) {
        this.soal = soal;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.jawaban = jawaban;
    }

    public String getSoal() {
        return soal;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setSoal(String soal) {
        this.soal = soal;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }
}
