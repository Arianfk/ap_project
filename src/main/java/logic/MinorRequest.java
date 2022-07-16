package logic;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDate;

public class MinorRequest extends Request {
    static Logger logger = LogManager.getLogger(MinorRequest.class.getName());
    transient public Department home, des;
    public ReqStatus homeStatus, desStatus;

    public MinorRequest(Student from, Teacher to, LocalDate date, Department home, Department des) {
        super(from, to, date);
        this.home = home;
        this.des = des;
        this.homeStatus = ReqStatus.PROCESSING;
        this.desStatus = ReqStatus.PROCESSING;
    }

    public void setHomeStatus(ReqStatus homeStatus) {
        this.homeStatus = homeStatus;
        updateStatus();
        logger.info("Home Department " + home.name + " answered to minor request of " + from.studentNumber);
    }

    public void setDesStatus(ReqStatus desStatus) {
        this.desStatus = desStatus;
        updateStatus();
        logger.info("Des Department " + des.name + " answered to minor request of " + from.studentNumber);
    }

    private void updateStatus(){
        if (homeStatus == ReqStatus.ACCEPTED && desStatus == ReqStatus.ACCEPTED)
            this.status = ReqStatus.ACCEPTED;

        if (homeStatus == ReqStatus.DECLINED || desStatus == ReqStatus.DECLINED)
            this.status = ReqStatus.DECLINED;
    }

    public ReqStatus getHomeStatus() {
        return homeStatus;
    }

    public ReqStatus getDesStatus() {
        return desStatus;
    }
}
