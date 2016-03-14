package io.wellbeings.anatome;

/**
 * Created by Calum on 29/11/2015.
 */
public class Note {
    //string storing the note's content
    private String content;
    public String getContent(){
        return new String(content);
    }
    public void setContent(String content) {
        this.content = content;
    }

    //String storing the note's creation date
    private String creationDate;
    public String getCreationDate(){
        return new String(creationDate);
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}