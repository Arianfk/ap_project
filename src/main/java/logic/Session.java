package logic;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Session {
    static Logger logger = LogManager.getLogger(Session.class.getName());
    public DayOfWeek day;
    public LocalTime start;
    public int lengthMinutes;
    public transient Course course;

    public Session(DayOfWeek day, LocalTime start, int lengthMinutes, Course course) {
        this.day = day;
        this.start = start;
        this.lengthMinutes = lengthMinutes;
        this.course = course;
    }

    @Override
    public String toString() {
        LocalTime end = start.plusMinutes(lengthMinutes);
        return start.format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + end.format(DateTimeFormatter.ofPattern("HH:mm"))
                + " " + course.name;
    }

    public void delete() {
        course.sessionList.remove(this);
        logger.info("Session " + day + " removed from course " + course.id);
    }
}
