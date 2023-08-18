package com.example.cryptchat;

public class Images {
    String img;
    String sender_id;
    String received_img;
    long Timestamp;

    public Images() {
    }

    public Images(String img, String sender_id, String received_img, long timestamp) {
        this.img = img;
        this.sender_id = sender_id;
        this.received_img=received_img;
        Timestamp = timestamp;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }
    public void setReceived_img(String received_img){ this.received_img=received_img; }
    public String getReceived_img(){return received_img;}
    public long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(long timestamp) {
        Timestamp = timestamp;
    }
}


