package io.wellbeings.anatome;

/**
 * Created by Calum on 29/11/2015.
 */
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageButton;

public class BrainWidget extends Fragment implements Widget {

    // Store view object for UI manipulation.
    private View v;
    private static final String FILE_NAME = "my_file";
    //declare variables for the graphical parts of the widget
    Button saveButton;
    ImageButton deleteButton;
    EditText noteInput;
    //variable for the note created by user
    Note note;

    //list storing all the happynotes saved to file
    List<Note> noteList;

    public BrainWidget() {
        // Required empty public constructor
    }

    public static BrainWidget newInstance() {
        BrainWidget fragment = new BrainWidget();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("OnCreateView", "onCreateView called.");

        // Inflate the layout for this fragment, storing view.
        v = inflater.inflate(R.layout.fragment_brain_widget, container, false);

        //could initialise graphics here
        initGUI();

        return v;

    }

    private void initGUI() {
        Log.d("INITGUI: ", "initGUI called");
        getActivity().setContentView(R.layout.fragment_brain_widget);
        //Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //initialise graphical elements
        saveButton = (Button)v.findViewById(R.id.btnSave1);
        deleteButton = (ImageButton)v.findViewById(R.id.btnDlt1);
        noteInput = (EditText)v.findViewById(R.id.etNote1);
        //add listeners
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnClick Listener","Onclick called");
                //create a note object based on user input
                if(noteInput.getText().toString() != null) {
                    note.setContent(noteInput.getText().toString());
                }
                else note.setContent("");

                //add note to list
                noteList.add(note);

                //save list to file
                saveList(noteList);
            }
        });

        //load in the notes list data
        noteList = getList();
        Log.d("GetList","getList was got with " + noteList);
        if(!noteList.isEmpty()) {
            noteInput.setHint(noteList.get(0).getContent());
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_brain, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    public List<Note> getList() {
        try{
            Log.d("GetList","getList called");
            return FileOperationHelper.getInstance().loadArrayList(getActivity().getApplicationContext(), FILE_NAME);

        }
        catch (IOException e){
            e.printStackTrace();
            return new ArrayList<Note>();
        }
    }

    public void saveList(List<Note> list) {
        try {
            Log.d("SaveList","SaveList was called");
            FileOperationHelper.getInstance().saveArrayList(getActivity().getApplicationContext(), FILE_NAME, list);
            Log.d("SaveList","finished doing the save");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteNote() {
        //remove note from list

        //save the updated list

        //play delete animation
    }

}