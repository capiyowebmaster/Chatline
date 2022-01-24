package com.example.chatline.Models;







public class posts {
    private  String userid;
    private String  username;
    private String ename;
    private String imageUrl;
    private  String picMessage;
    private  String counter;




    public posts(){

    }

    public posts(String username,String counter, String userid, String ename
                 , String imageUrl ,String picMessage) {
        this.userid=userid;
        this.username = username;
        this.ename = ename;
        this.imageUrl=imageUrl;
        this.picMessage=picMessage;
        this.counter=counter;

    }

    public String getPicMessage() {
        return picMessage;
    }

    public void setPicMessage(String picMessage) {
        this.picMessage = picMessage;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
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



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

