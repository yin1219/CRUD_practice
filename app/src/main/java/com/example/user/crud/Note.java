package com.example.user.crud;

import java.util.Date;

public class Note extends NoteId{
    String Artocle;
    String Title;
    Date Time;
    public Note(){

    }

    public Note(Date time) {
        Time = time;
    }

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }


    public Note(String title, String artocle) {
        Title = title;
        Artocle = artocle;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getArtocle() {
        return Artocle;
    }

    public void setArtocle(String artocle) {
        Artocle = artocle;
    }


}
