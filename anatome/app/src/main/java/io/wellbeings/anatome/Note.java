package io.wellbeings.anatome;

import java.io.Serializable;

/**
 * To model the behaviour of note objects
 * which a user's thoughts are recorded with,
 * provide integration with visual display.
 *
 * @author Team WellBeings - Calum
 */
public class Note implements Serializable {
    //Setting the SUID
    static final long serialVersionUID = 176903428466484737L;
    //string storing the note's content
    private String content;
    //String storing the directory of any selected image content
    private String imageDirectory;
    //String storing audio  directory
    private String audioDirectory;
    //String storing the note's creation date
    private String creationDate;

    //get content
    public String getContent(){
        return content;
    }

    //set content
    public void setContent(String content) {
        this.content = content;
    }

    //get creation date
    public String getCreationDate() {
        return creationDate;
    }

    //set creation date
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }


    //get image directory
    public String getImageDirectory() {
        //protect against nullPointers
        if(hasImageContent() == false){
            return "";
        }
        return imageDirectory;
    }

    //set image content
    public void setImageContent(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }

    //method returns true if the content is an image
    public boolean hasImageContent() {
        if(imageDirectory == null) return false;
        else return true;
    }

    //check audio content
    public boolean hasAudioContent(){
        if(audioDirectory == null) return false;
        else return true;
    }

    //get audio directory
    public String getAudioDirectory() {
        //protect against nullPointers
        if(hasAudioContent() == false){
            return "";
        }
        return audioDirectory;
    }

    //set audio directory
    public void setAudioContent(String audioDirectory) {
        this.audioDirectory = audioDirectory;
    }

    //constructor
    public Note(String creationDate, String content) {
        this.creationDate = creationDate;
        this.content = content;
    }

    //toString
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