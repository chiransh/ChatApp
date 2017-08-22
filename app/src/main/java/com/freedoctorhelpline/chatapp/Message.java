package com.freedoctorhelpline.chatapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 8/13/2017.
 */

public class Message {

    @SerializedName("username")
    String username;
    @SerializedName("message")
    String message;
    @SerializedName("timeStamp")
    long timeStamp;
    public Message(String username, String message,long timeStamp) {
        this.username = username;
        this.message = message;
        this.timeStamp=timeStamp;
    }

    public Message(String username, String message) {
        this.message=message;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
    public long getTimeStamp(){
        return timeStamp;
    }
}
