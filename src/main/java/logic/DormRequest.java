package logic;

import java.time.LocalDate;

public class DormRequest {
    public LocalDate date;
    public ReqStatus status;

    public DormRequest(LocalDate date) {
        this.date = date;
        if (Math.random() < 0.5)
            status = ReqStatus.ACCEPTED;
        else
            status = ReqStatus.DECLINED;
    }
}
