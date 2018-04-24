package com.jianjunhuang.bluemountain.model.bean;

import java.io.Serializable;
import java.util.Date;

public class Community {

    private String communityId;
    private String title;
    private String content;
    private Date date;
    private int agreeNum;
    private int disagreeNum;
    private int isAgree;

    public Community() {
        super();
    }

    public Community(String communityId, String title, String content, Date date, int agreeNum, int disagreeNum, int isAgree) {
        this.communityId = communityId;
        this.title = title;
        this.content = content;
        this.date = date;
        this.agreeNum = agreeNum;
        this.disagreeNum = disagreeNum;
        this.isAgree = isAgree;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAgreeNum() {
        return agreeNum;
    }

    public void setAgreeNum(int agreeNum) {
        this.agreeNum = agreeNum;
    }

    public int getDisagreeNum() {
        return disagreeNum;
    }

    public void setDisagreeNum(int disagreeNum) {
        this.disagreeNum = disagreeNum;
    }

    public int getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(int isAgree) {
        this.isAgree = isAgree;
    }

    @Override
    public String toString() {
        return "\n\nid = " + communityId +
                "\ntitle = " + title +
                "\ncontent = " + content +
                "\ndate = " + date +
                "\ndisposition = " + isAgree;
    }
}
