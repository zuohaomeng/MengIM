package com.meng.mengim.common.bean;

/**
 * @Author ZuoHao
 * @Date 2020/11/12
 * @Description 上线消息
 */
public class LoginMessage {
    /**
     * 用户id
     */
    private long memberId;
    /**
     * 平台ID
     */
    private int platform = 1;
    /**
     * 版本
     */
    private String appVersion = "1.0.0";


    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "memberId=" + memberId +
                ", platform=" + platform +
                ", appVersion='" + appVersion + '\'' +
                '}';
    }
}
