package com.rayhahah.easysports.module.mine.bean;

import com.rayhahah.easysports.bean.BaseSection;
import com.rayhahah.easysports.app.C;

/**
 * Created by a on 2017/6/26.
 */

public class MineListBean extends BaseSection {

    public final static int TYPE_NULL = 0;
    public final static int TYPE_CHECKBOX = 1;
    public final static int TYPE_TEXTVIEW = 2;

    private int coverRes;
    private String coverPath;
    private String title;
    private int type;
    private int id;

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public int getCoverRes() {
        return coverRes;
    }

    public void setCoverRes(int coverRes) {
        this.coverRes = coverRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(@C.ACCOUNT int id) {
        this.id = id;
    }
}
