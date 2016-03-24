package io.wellbeings.anatome;

/**
 * Created by Calum on 29/11/2015.
 */
import android.app.ActionBar;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
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

    //filename for persistent data
    private static final String FILE_NAME = "brain.txt";

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

        //initialise list of notes from file
        //noteList = getList();
        noteList = new ArrayList<Note>();
        noteList.add(new Note("1st January", "helloworld"));
        noteList.add(new Note("12th March", "CalumRUles"));

        //initialise the graphics for each note in the noteList
        for(int i = 0; i < noteList.size(); i++) {
            initNote(i);
        }

        //add another note for the latest one
        initNote(-1);

        return v;
    }

    //method for initialising notes
    //index is set to -1 for a note not part of the noteList
    private void initNote(int index) {
        //obtain the horizontal scroll view that stores the notes
        LinearLayout scroll = (LinearLayout)v.findViewById(R.id.noteScroll);

        //create a LinearLayout element
        LinearLayout ll = new LinearLayout(getContext());
        ll.setOrientation(LinearLayout.VERTICAL);

        //add the note's date textView
        TextView date = new TextView(getContext());
        if(index < 0) date.setText("1st December");
        else date.setText(noteList.get(index).getCreationDate());
        ll.addView(date);

        //add the note's text input
        noteInput = new EditText(getContext());
        noteInput.setLayoutParams(new ActionBar.LayoutParams(800, 800));
        if(index < 0) noteInput.setText("1st December");
        else noteInput.setText(noteList.get(index).getContent());
        ll.addView(noteInput);

        //add the save button
        saveButton = new Button(getContext());
        saveButton.setText("Save");
        saveButton.setLayoutParams(new ActionBar.LayoutParams(400,120));
        ll.addView(saveButton);

        //add the delete button
        deleteButton = new ImageButton(getContext());
        Drawable d = Drawable.createFromPath("@drawable/bin.png");
        deleteButton.setImageDrawable(d);
        ll.addView(deleteButton);

        //change background colour of the linearLayout to white
        ll.setBackgroundColor(Color.parseColor("#FFFFFF"));

        //add Layout to the scrollview
        scroll.addView(ll);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("hello", "OI OI");
                saveList(v);
                List<Note> testList = getList();
                Toast.makeText(getContext(), "Gotlist: " + testList.toString(), Toast.LENGTH_LONG).show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }

    //method for loading the notes into the fragment
    public List<Note> getList() {
        try{
            Log.d("GetList","getList called");
            return loadArrayList(getActivity().getApplicationContext(), FILE_NAME);
        }
       catch (IOException e){
            return new ArrayList<Note>();
        }
    }

    //saveList method used in an onClickHandler in BrainWidget
    //as of yet there is no logical explanation for why Android insists on forcing me to do this
    //no other solutions work and 8 hours is too long to spend on a button
    //fuck you Android
    public void saveList(View v) {
        try {
            Note note = new Note("Example", noteInput.getText().toString());
            noteList.add(note);
            Log.d("tostring", "Note: " +noteInput.getText().toString());
        saveArrayList(getActivity().getApplicationContext(), FILE_NAME, noteList);
        Log.d("SaveList", "finished doing the save");
        } catch (IOException e) {
         e.printStackTrace();
        }
    }

    private void saveArrayList(Context context, String filename, List<Note> data) throws IOException {
        FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(data);
        objectOutputStream.flush();
        fileOutputStream.close();
        Toast.makeText(context, "The contents are saved in the file" + getList(), Toast.LENGTH_LONG).show();
    }

    private List<Note> loadArrayList(Context context, String filename) throws IOException {
        ArrayList<Note> readBack = new ArrayList<Note>();

        try {
            FileInputStream fis = getActivity().openFileInput(filename);
            ObjectInputStream ois= new ObjectInputStream(fis);
            noteList = (ArrayList<Note>)ois.readObject();
            if(noteList==null){
                Log.e("null", "The noteList was read in as null.");
                noteList=new ArrayList<Note>();
            }
            ois.close();
            return noteList;
        }
        catch(ClassNotFoundException ex)
        {
            Log.e("loadlist","class not found fam",ex);
            return new ArrayList<Note>();
        }
        catch(IOException ex) {
            Log.e("loadlist", "io problem fam", ex);
            return new ArrayList<Note>();
        }
    }


    //method for deleting a note
    private void delete(int id) {
        //remove note from list
        //noteList.remove(id);
        //save the updated list
        saveList(v);
        //play delete animation
    }

}