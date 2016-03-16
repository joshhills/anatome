package io.wellbeings.anatome;

/**
 * Created by Calum on 29/11/2015.
 */
public class Note {
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

    //constructor
    public Note(String creationDate, String content) {
        this.creationDate = creationDate;
        this.content = content;
    }

    public String toString() {
        return this.getCreationDate() + this.getContent();
    }
}