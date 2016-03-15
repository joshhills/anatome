package io.wellbeings.anatome;

/**
 * Created by Calum on 29/11/2015.
 */
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import io.wellbeings.anatome.Note;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.Toast;

public class BrainWidget extends Fragment implements Widget {
    // Store view object for UI manipulation.
    private View v;
    //declare variables for the graphical parts of the widget
    Button saveButton;
    Button testButton;
    ImageButton deleteButton;
    EditText noteInput;
    //list storing all the happynotes saved to file
    List<Note> noteList;

    //required empty constructor
    public BrainWidget() { }
    //method for creating a new instance of this class
    public static BrainWidget newInstance() {
        BrainWidget fragment = new BrainWidget();
        Bundle args = new Bundle();
        return fragment;
    }
    //onCreate method
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    //onCreateView method (when this fragment is navigated to)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment, storing view.
        v = inflater.inflate(R.layout.fragment_brain_widget, container, false);

        //get buttons --couldn't rename the id without an exception
        saveButton = (Button) v.findViewById(R.id.addButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("hello", "OI OI");
                saveList(v);
            }
        });

        return v;
    }

    //method for loading the notes into the fragment
    public List<Note> getList() {
        //try{
            Log.d("GetList","getList called");
            //return loadArrayList(getActivity().getApplicationContext(), FILE_NAME);
        //}
       //catch (IOException e){
            //e.printStackTrace();
            return new ArrayList<Note>();
        //}
    }

    //saveList method used in an onClickHandler in BrainWidget
    //as of yet there is no logical explanation for why Android insists on forcing me to do this
    //no other solutions work and 8 hours is too long to spend on a button
    //fuck you Android
    public void saveList(View v) {
        try {
        Log.d("savelist", "SaveList was called");
        saveArrayList(getActivity().getApplicationContext(), "brain", noteList);
        Log.d("SaveList", "finished doing the save");
        } catch (IOException e) {
         e.printStackTrace();
        }
    }

    public void saveArrayList(Context context, String filename, List<Note> data) throws IOException {
        FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(data);
        objectOutputStream.flush();
        fileOutputStream.close();
        Toast.makeText(context, "The contents are saved in the file.", Toast.LENGTH_LONG).show();
    }

    //method for deleting a note
    private void delete() {
        //remove note from list

        //save the updated list

        //play delete animation
    }

}