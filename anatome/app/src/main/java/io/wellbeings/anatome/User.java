package io.wellbeings.anatome;

import android.app.Activity;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Model a user based on our applications
 * persistent needs.
 *
 * @author Team WellBeings - Josh
 * @version 1.0
 */
public class User extends Activity {

    /*
     * The single instance of the User stores
     * their preferences and settings and enforces
     * concurrency.
     */
    private static User instance;

    // Constant file reference.
    final String USER_FILE = "UserProfile";

    /* Store user settings. */

    // Default language to English.
    private String lang = "en";

    /**
     * Method models the Singleton design pattern, which serves to
     * provide over-arching management of all user-settings.
     *
     * @return	The only available instance of the 'User' class.
     */
    public static User getInstance() {

        // If one does not exist...
        if(instance == null) {
            // Create one.
            instance = new User();
        }
        // Return instance of 'User'.
        return instance;

    }

    /**
     * Constructor is hidden; this prevents any component
     * from creating another instance.
     */
    protected User() {
        loadUserProfile();
    }

    /**
     * Save the user profile to the device storage
     * for persistent settings.
     *
     * @return Status indicating success of operation.
     */
    public STATUS saveUserProfile() {

        /* Attempt to save the object to memory.
        try {
            FileOutputStream fos = this.openFileOutput(USER_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(instance);
            // Close streams.
            fos.close();
            os.close();
        } catch(IOException e) {
            e.printStackTrace();
            return STATUS.FAIL;
        }*/

        return STATUS.SUCCESS;

    }

    public STATUS loadUserProfile() {

        /* Attempt to load the object from memory.
        try {
            FileInputStream fis = this.openFileInput(USER_FILE);
            ObjectInputStream is = new ObjectInputStream(fis);
            instance = (User) is.readObject();
        } catch(NullPointerException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return STATUS.FAIL;
        }*/

        return STATUS.SUCCESS;

    }

    /* Mutator methods. */

    public String getLang() {
        return lang;
    }
    public void setLang(String lang) {
        this.lang = lang;
        saveUserProfile();
    }

}
