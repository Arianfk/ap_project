package logic;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    static Logger logger = LogManager.getLogger(User.class.getName());
    public static int IdCount = 1000000;
    public static List<Student> allStudents = new ArrayList<>();
    public boolean isRegistrationAllowed = true;
    public transient Teacher guide;
    public transient List<Course> courseList = new ArrayList<>();
    public LocalDateTime registerTime;
    public String registrationCertificate = "Allowed";
    public EduStatus eduStatus = EduStatus.educating;
    public List<RecomRequest> recomRequestList = new ArrayList<>();
    public List<MinorRequest> minorRequestList = new ArrayList<>();
    public QuitRequest quitRequest = null;
    public Degree degree;
    public List<DormRequest> dormRequestList = new ArrayList<>();
    public LocalDateTime thesisDate;
    public String studentNumber;

    public String guideUsername;

    public Student () {
        super();
        allStudents.add(this);
        registerTime = LocalDateTime.now();
        degree = Degree.BACHELOR;
        studentNumber = IdCount++ + "";
    }

    public Student(String username, String password) {
        super(username, password);
        allStudents.add(this);
        registerTime = LocalDateTime.now();
        degree = Degree.BACHELOR;
        studentNumber = IdCount++ + "";
        logger.info("New Student " + studentNumber + " created");
    }

    public void dormRequest() {
        DormRequest request = new DormRequest(LocalDate.now());
        dormRequestList.add(request);
        logger.info("Student " + studentNumber + " requested dorm and " + request.status.name());
    }

    public void setGuide(Teacher guide) {
        this.guide = guide;
        this.guideUsername = guide.username;
    }

    public double getAverage() {
        double res = 0;
        int unitSum = 0;
        for (Course course : courseList) {
            FinalMark mark = course.getFinalMark(this);
            if (mark != null && mark.isFinal) {
                unitSum += course.unitCount;
                res += mark.value * course.unitCount;
            }
        }
        return (unitSum > 0 ? res / unitSum : 0);
    }

    public int getUnitSum() {
        int unitSum = 0;
        for (Course course : courseList) {
            FinalMark mark = course.getFinalMark(this);
            if (mark != null && mark.isFinal) {
                unitSum += course.unitCount;
            }
        }
        return unitSum;
    }

    public void newRecommendationRequest(Teacher teacher) {
        RecomRequest recom = new RecomRequest(this, teacher, LocalDate.now());
        recomRequestList.add(recom);
        teacher.recomRequestList.add(recom);
        logger.info("Student " + studentNumber + " requested recommendation from " + teacher.name);
    }

    public void newMinorRequest(Department department) {
        MinorRequest request = new MinorRequest(this, null, LocalDate.now(), this.department, department);
        minorRequestList.add(request);
        this.department.minorRequestList.add(request);
        department.minorRequestList.add(request);
        logger.info("Student " + studentNumber + " requested minor to " + department.name);
    }

    public void newQuitRequest() {
        quitRequest = new QuitRequest(this, department.assistant, LocalDate.now());
        logger.info("Student " + studentNumber + " requested quit");
    }

    public void load() {
        guide = (Teacher) User.getByUsername(guideUsername);
    }

    @Override
    public void setDepartment(Department department) {
        if (this.department != null)
            this.department.studentList.remove(this);
        this.department = department;
        this.department.studentList.add(this);
    }
}
