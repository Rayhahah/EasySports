package com.rayhahah.easysports.common;

import cn.bmob.v3.BmobObject;

/**
 * Created by a on 2017/5/16.
 */

public class AppCrashMessage extends BmobObject {

    public String message;

    public AppCrashMessage() {
        this.setTableName("crash");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
