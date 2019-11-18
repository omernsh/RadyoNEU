package com.dh.neuradyo;

/**
 * Created by alper on 16/07/2017.
 */

public class Message_3 {

    String usermessage;
    String useremail;
    String usermessagetime;
    String username;

    public Message_3() {
    }

    public Message_3(String usermessage, String useremail, String usermessagetime, String username) {
        this.usermessage = usermessage;
        this.useremail = useremail;
        this.usermessagetime = usermessagetime;
        this.username = username;

    }

    public String getUsermessage() {
        return usermessage;
    }

    public void setUsermessage(String usermessage) {
        this.usermessage = usermessage;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsermessagetime() {
        return usermessagetime;
    }

    public void setUsermessagetime(String usermessagetime) {
        this.usermessagetime = usermessagetime;
    }

}