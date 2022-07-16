package logic;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Department {
    static Logger logger = LogManager.getLogger(Department.class.getName());
    public static List<Department> allDepartments = new ArrayList<>();
    public String name;
    public transient Teacher admin, assistant;
    public List<Course> courseList = new ArrayList<>();
    public List<Student> studentList = new ArrayList<>();
    public List<Teacher> teacherList = new ArrayList<>();
    public transient List<MinorRequest> minorRequestList = new ArrayList<>();

    public String adminUsername;
    public String assistantUsername;

    public Department() {
        allDepartments.add(this);
    }

    public Department(String name) {
        this.name = name;
        allDepartments.add(this);
    }

    public void setAssistant(Teacher assistant) {
        this.assistant = assistant;
        this.assistantUsername = assistant.username;
        logger.info("Department " + name + " assistant changed to " + assistant.name);
    }

    public void setAdmin(Teacher admin) {
        this.admin = admin;
        this.adminUsername = admin.username;
    }

    @Override
    public String toString() {
        return name;
    }

    public void load() {
        this.admin = (Teacher) User.getByUsername(adminUsername);
        this.assistant = (Teacher) User.getByUsername(assistantUsername);

        for (Course course : courseList) {
            course.load();
            course.department = this;
        }

        for (Student student : studentList) {
            student.load();
            student.department = this;
        }

        for (Teacher teacher : teacherList) {
            teacher.department = this;
        }
    }
}
