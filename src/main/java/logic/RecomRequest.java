package logic;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecomRequest extends Request {
    static Logger logger = LogManager.getLogger(RecomRequest.class.getName());
    public List<Course> courseList = new ArrayList<>();
    public List<Course> TACourseList = new ArrayList<>();
    public String answer;

    public RecomRequest(Student from, Teacher to, LocalDate date) {
        super(from, to, date);
    }
    public void setStatus(ReqStatus status) {
        this.status = status;
        logger.info("Teacher " + to.id + " answered recommendation request of " + from.studentNumber);
    }
}
