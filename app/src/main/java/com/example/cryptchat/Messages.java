package com.example.cryptchat;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class Messages {
    String msg;
    String sender_id;
    long Timestamp;
    encrypt_message encrypt = new encrypt_message();

    public Messages() {
    }

    public Messages(String msg, String sender_id, long timestamp) {
        this.msg = msg;
        this.sender_id = sender_id;
        Timestamp = timestamp;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getMsg() {
//        if (encrypt.decrypt(msg) == null){
//            return msg;
//        }else{
//            msg = encrypt.decrypt(msg);
//            System.out.println("***********************decrypt********************");
//            System.out.println(msg);
//            System.out.println("*******************************************");
//            return msg;
//        }
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(long timestamp) {
        Timestamp = timestamp;
    }
}
