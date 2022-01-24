package com.example.chatline.Models;







public class imagesModel {
    private String userid;
    private String username;
    private String ename;
    private String caption;
    private String sendImages;

    public imagesModel() {

    }

    public imagesModel(String username, String userid, String ename, String caption, String sendImages
    ) {
        this.userid = userid;
        this.username = username;
        this.ename = ename;
        this.caption = caption;
        this.sendImages = sendImages;

    }

    public String getSendImages() {
        return sendImages;
    }

    public void setSendImages(String sendImages) {
        this.sendImages = sendImages;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }


}

