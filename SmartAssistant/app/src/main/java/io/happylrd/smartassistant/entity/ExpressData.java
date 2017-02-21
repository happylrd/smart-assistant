package io.happylrd.smartassistant.entity;

public class ExpressData {

    private String dateTime;
    private String remark;
    private String zone;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "ExpressData{" +
                "dateTime='" + dateTime + '\'' +
                ", remark='" + remark + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }
}
