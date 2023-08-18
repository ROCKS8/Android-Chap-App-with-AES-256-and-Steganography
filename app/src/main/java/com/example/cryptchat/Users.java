package com.example.cryptchat;


public class Users {

    String uid;
    String name;
    String number;
    String imageuri;
    String status;

    public Users(){

    }
    public Users(String uid, String name, String number) {
        this.uid = uid;
        this.name = name;
        this.number = number;
    }

//    public String getStatus() {
//        return status;
//    }

    public void setStatus(String status) {
        this.status = status;
    }

    //, String imageuri, String status


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

//    public String getImageuri() {
//        return imageuri;
//    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

}
