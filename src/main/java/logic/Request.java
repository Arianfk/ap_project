package logic;

import java.time.LocalDate;

public class Request {
    public Student from;
    public Teacher to;
    public ReqStatus status;
    public LocalDate date;
    public boolean isAnswered = false;

    public Request(Student from, Teacher to, LocalDate date) {
        this.from = from;
        this.to = to;
        this.status = ReqStatus.PROCESSING;
        this.date = date;
    }

    public void answer(){
        isAnswered = true;
    }
}
