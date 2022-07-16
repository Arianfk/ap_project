package logic;

public class Objection {
    public FinalMark finalMark;
    public String object, ans = "";
    public boolean isAnswered = false;

    public Objection(FinalMark finalMark, String object) {
        this.finalMark = finalMark;
        this.object = object;
    }

    public void answer(String ans) {
        this.ans = ans;
        isAnswered = true;
    }
}
