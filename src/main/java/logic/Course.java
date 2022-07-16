package logic;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Course {
    public static List<Course> allCourse = new ArrayList<>();
    public static int idCount = 100000;
    static Logger logger = LogManager.getLogger(Course.class.getName());
    public transient Department department;
    public transient Teacher teacher;
    public transient List<Student> studentList;
    public String name, id;
    public int unitCount;
    public List<Exam> examList = new ArrayList<>();
    public List<Session> sessionList = new ArrayList<>();
    public List<FinalMark> finalMarks = new ArrayList<>();
    public boolean finished = false;
    public Exam finalExam;
    public Degree degree;

    public String teacherUsername;

    public void addSession(Session session){
        sessionList.add(session);
        logger.info("Session " + session.day + " added to course " + this.id);
    }

    public void setFinalExamDate(LocalDateTime localDateTime) {
        this.finalExam.date = localDateTime;
        logger.info("Course " + id + " final exam date has been changed");
    }

    public void setName(String name) {
        this.name = name;
        logger.info("Course " + id + " name has been changed");
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
        logger.info("Course " + id + " unit count has been changed");
    }

    public List<String> studentUsernameList = new ArrayList<>();

    public Course() {
        allCourse.add(this);
        this.id = idCount++ + "";
        this.finalExam = new Exam();
        this.finalExam.course = this;
        this.studentList = new ArrayList<>();
    }

    public Course(String name, Teacher teacher) {
        allCourse.add(this);
        this.name = name;
        this.teacher = teacher;
        this.teacherUsername = teacher.username;
        this.id = idCount++ + "";
        this.studentList = new ArrayList<>();
        logger.info("New Course " + id + " created");
        degree = Degree.BACHELOR;
    }

    public void finish(){
        this.finished = true;
        for (FinalMark mark : finalMarks)
            mark.isFinal = true;

        logger.info("Course " + this.id + " finished");
    }

    public void addStudent(Student student) {
        this.studentList.add(student);
        studentUsernameList.add(student.username);
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        this.teacherUsername = teacher.username;
    }

    public void setFinalMarks(List<FinalMark> finalMarks) {
        this.finalMarks = finalMarks;
        logger.info("Course " + id + " marks changed");
    }

    public FinalMark getFinalMark(Student student) {
        for (FinalMark mark : finalMarks) {
            if (mark.student == student) {
                return mark;
            }
        }
        return null;
    }

    public void delete() {
        allCourse.remove(this);
        department.courseList.remove(this);
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isTempResultSet() {
        return finalMarks.size() == studentList.size();
    }

    public int getPassedStudentNumber() {
        int count = 0;
        for (FinalMark mark : finalMarks) {
            if (mark.value >= 10) count++;
        }
        return count;
    }

    public int getFailedStudentNumber() {
        int count = 0;
        for (FinalMark mark : finalMarks) {
            if (mark.value < 10) count++;
        }
        return count;
    }

    public double getAverage() {
        double sum = 0;
        for (FinalMark mark : finalMarks) {
            sum += mark.value;
        }

        return (finalMarks.size() == 0 ? -1 : sum / finalMarks.size());
    }

    public double getPassedAverage() {
        double sum = 0;
        int count = 0;
        for (FinalMark mark : finalMarks) {
            if (mark.value >= 10) {
                count++;
                sum += mark.value;
            }
        }

        return (count == 0 ? -1 : sum / count);
    }

    public void load() {
        teacher = (Teacher) User.getByUsername(teacherUsername);
        for (String username : studentUsernameList) {
            Student student = (Student) User.getByUsername(username);
            studentList.add(student);
            student.courseList.add(this);
        }
    }
}
