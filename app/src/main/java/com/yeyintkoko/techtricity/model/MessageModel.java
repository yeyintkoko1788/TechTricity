package com.yeyintkoko.techtricity.model;

public class MessageModel {
    private int ID;
    private String SenderName;
    private String SenderPhone;
    private String SenderEmail;
    private String Subject;
    private String BodyMessage;
    private boolean IsDeleted;
    private String Accesstime;
    private String ReplyDate;
    private int ReplyUserID;
    private String ReplyUserName;
    private boolean IsReplied;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getSenderPhone() {
        return SenderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        SenderPhone = senderPhone;
    }

    public String getSenderEmail() {
        return SenderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        SenderEmail = senderEmail;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getBodyMessage() {
        return BodyMessage;
    }

    public void setBodyMessage(String bodyMessage) {
        BodyMessage = bodyMessage;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public String getAccesstime() {
        return Accesstime;
    }

    public void setAccesstime(String accesstime) {
        Accesstime = accesstime;
    }

    public String getReplyDate() {
        return ReplyDate;
    }

    public void setReplyDate(String replyDate) {
        ReplyDate = replyDate;
    }

    public int getReplyUserID() {
        return ReplyUserID;
    }

    public void setReplyUserID(int replyUserID) {
        ReplyUserID = replyUserID;
    }

    public String getReplyUserName() {
        return ReplyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        ReplyUserName = replyUserName;
    }

    public boolean isReplied() {
        return IsReplied;
    }

    public void setReplied(boolean replied) {
        IsReplied = replied;
    }
}
