package logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Exam {
    public Course course;
    public String name;
    public LocalDateTime date;
    public boolean taken = false;
    public List<Mark> markList = new ArrayList<>();

    public Mark getStudentMark(Student student) {
        for (Mark mark : markList) {
            if (student == mark.student) return mark;
        }
        return null;
    }

    @Override
    public String toString() {
        return course.name + " - " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
