package logic;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User {
    static Logger logger = LogManager.getLogger(Teacher.class.getName());
    public static List<Teacher> allTeachers = new ArrayList<>();
    public static int IdCount = 1000000;
    public transient List<RecomRequest> recomRequestList = new ArrayList<>();
    public TeacherDegree degree;
    public String roomId, id;

    public Teacher() {
        super();
        allTeachers.add(this);
    }

    public Teacher(String username, String password) {
        super(username, password);
        allTeachers.add(this);
        this.id = IdCount++ + "";
        logger.info("New teacher with id: " + this.id + " added");
    }

    public void setDegree(TeacherDegree degree) {
        this.degree = degree;
        logger.info("Teacher " + this.id + " degree has been changed");
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
        logger.info("Teacher " + this.id + " room id has been changed");
    }

    public boolean isAssistant() {
        return this.department.assistant == this;
    }

    public boolean isAdmin() {
        return this.department.admin == this;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void setDepartment(Department department) {
        super.setDepartment(department);
    }

    @Override
    public void delete() {
        super.delete();
        allTeachers.remove(this);
        logger.info("Teacher " + id + " has been removed");
    }
}
