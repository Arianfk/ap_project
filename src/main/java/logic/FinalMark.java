package logic;

public class FinalMark {
    transient public Student student;
    transient public Course course;
    public double value;
    public boolean isFinal = false;
    public Objection objection = null;

    public FinalMark(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public double getRoundedValue(double value) {
        return Math.ceil(value / 0.25) * 0.25;
    }

    public void setObjection(Objection objection) {
        this.objection = objection;
        Student.logger.info("Student " + student.studentNumber + " objected to " + course.id + " mark");
    }

    public void setValue(double value) {
        this.value = getRoundedValue(value);
    }

    public boolean isPassed(){
        return value >= 10;
    }
}
