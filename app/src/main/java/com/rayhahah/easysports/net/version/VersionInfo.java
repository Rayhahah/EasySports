package com.rayhahah.easysports.net.version;

/**
 * Created by Rayhahah on 2017/4/11.
 */

public class VersionInfo {
    /**
     * status : 0
     * msg : 获取版本信息成功
     * data : {"id":1,"versionName":"1.0.0","versionCode":"1","description":"测试第一版本","url":"ftp://88.128.18.163:21/raymall/version/easysportv1.0.0.apk","createTime":1506062976000,"updateTime":1506062982000}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * versionName : 1.0.0
         * versionCode : 1
         * description : 测试第一版本
         * url : ftp://88.128.18.163:21/raymall/version/easysportv1.0.0.apk
         * createTime : 1506062976000
         * updateTime : 1506062982000
         */

        private int id;
        private String versionName;
        private String versionCode;
        private String description;
        private String url;
        private long createTime;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
