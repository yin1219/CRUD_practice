package com.example.user.crud;

import android.support.annotation.NonNull;

public class NoteId {
    public String notrId;
    public <T extends NoteId>T withId(@NonNull final  String id){
        this.notrId =id;
        return (T) this;
    }
}
