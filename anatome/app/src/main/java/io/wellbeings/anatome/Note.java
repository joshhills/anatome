package io.wellbeings.anatome;

import android.net.Uri;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Calum on 29/11/2015.
 * Purpose: To model the behaviour of note objects which a user's thoughts are recorded with
 */
public class Note implements Serializable {
    //Setting the SUID
    static final long serialVersionUID = 176903428466484737L;

    //string storing the note's content
    private String content;
    public String getContent(){
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    //String storing the note's creation date
    private String creationDate;
    public String getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /*private String imageContent;
    public String getImageContent() { return imageContent; }
    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }
    //method returns true if the content is an image
    public boolean hasImageContent() {
        if(imageContent == null) return true;
        else return false;
    }*/

    //constructor
    public Note(String creationDate, String content) {
        this.creationDate = creationDate;
        this.content = content;
    }

    @Override
    public String toString() {
        return this.getCreationDate() + this.getContent();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Note)) return false;
        if(o == this) return true;

        Note note = (Note)o;
        if(this.creationDate == note.getCreationDate()
                && this.content == note.getContent()) {
            return true;
        }
        return false;
    }
}