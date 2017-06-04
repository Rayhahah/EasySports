package com.rayhahah.easysports.bean;

import cn.bmob.v3.BmobObject;


/**
 * 崩溃信息上传实体类
 */
public class CrashMessage extends BmobObject {

    private String date;
    private String versionName;
    private String versionCode;
    private String exceptionInfos;
    private String deviceInfos;
    private String systemInfoss;
    private String secureInfos;
    private String memoryInfos;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getExceptionInfos() {
        return exceptionInfos;
    }

    public void setExceptionInfos(String exceptionInfos) {
        this.exceptionInfos = exceptionInfos;
    }

    public String getDeviceInfos() {
        return deviceInfos;
    }

    public void setDeviceInfos(String deviceInfos) {
        this.deviceInfos = deviceInfos;
    }

    public String getSystemInfoss() {
        return systemInfoss;
    }

    public void setSystemInfoss(String systemInfoss) {
        this.systemInfoss = systemInfoss;
    }

    public String getSecureInfos() {
        return secureInfos;
    }

    public void setSecureInfos(String secureInfos) {
        this.secureInfos = secureInfos;
    }

    public String getMemoryInfos() {
        return memoryInfos;
    }

    public void setMemoryInfos(String memoryInfos) {
        this.memoryInfos = memoryInfos;
    }
}
