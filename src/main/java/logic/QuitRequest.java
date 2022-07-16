package logic;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDate;

public class QuitRequest extends Request {
    static Logger logger = LogManager.getLogger(QuitRequest.class.getName());
    public QuitRequest(Student from, Teacher to, LocalDate date) {
        super(from, to, date);
    }

    public void setStatus(ReqStatus status) {
        this.status = status;
        logger.info("Teacher " + to.id + " answered to quit request of " + from.studentNumber);
        if (status == ReqStatus.ACCEPTED) {
            User.allUsers.remove(from);
            Student.allStudents.remove(from);
            logger.info("Student " + from.studentNumber + " removed");
        }
    }
}
