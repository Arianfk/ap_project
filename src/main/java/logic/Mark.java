package logic;

public class Mark {
    public Student student;
    public double value;
    public Exam exam;

    public Mark(Student student, Exam exam) {
        this.student = student;
        this.exam = exam;
    }

    public double getRoundedValue(){
        return Math.ceil(value / 0.25) * 0.25;
    }
}
