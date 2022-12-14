package com.example.myapplication.Model;

public class HoaDonHenGio extends Hoadon{
    private String userId;
    private String timeStart;
    private String timeEnd;
    private boolean isSuccess = false;
    private String Gameid;

    public HoaDonHenGio() {
    }

    public HoaDonHenGio(float cost, String userId, String timeStart, String timeEnd,String gameid) {
        super(cost);
        this.userId = userId;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.Gameid = gameid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getGameid() {
        return Gameid;
    }

    public void setGameid(String gameid) {
        Gameid = gameid;
    }

    @Override
    public String toString() {
        return "HoaDonHenGio{" +
                "cost=" + cost +
                "userId='" + userId + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", isSuccess=" + isSuccess +
                ", Gameid='" + Gameid + '\'' +
                '}';
    }
}
