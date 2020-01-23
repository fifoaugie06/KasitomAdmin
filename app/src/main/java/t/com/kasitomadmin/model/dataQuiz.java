package t.com.kasitomadmin.model;

public class dataQuiz {
    public String soal;
    public String optionA, optionB, optionC, optionD, jawaban;

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
}
