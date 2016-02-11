package com.wellbeings.anatome;

/**
 * Created by Calum & Ahm on 29/11/2015.
 */

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.wellbeings.anatome.Note;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class BrainActivity extends AppCompatActivity {
    //declare variables for the graphical parts of the widget
    Button saveButton, deleteButton;
    EditText noteInput;
    //variable for the note created by user
    Note note;

    //list storing all the happynotes saved to file
    List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //initialise graphical elements
        saveButton = (Button)findViewById(R.id.saveButton);
        deleteButton = (Button)findViewById(R.id.deleteButton);
        noteInput = (EditText)findViewById(R.id.noteInput);
        //add listeners
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a note object based on user input
                note.setContent(noteInput.getText().toString());

                //add note to list
                noteList.add(note);

                //save list to file
                saveList(noteList);
            }
        });

        //load in the notes list data
        getList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_brain, menu);
        return true;
    }

    @Override
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
    }

    public void getList() {
        //find out if a list has been saved to phone
        String fileURL = "/appname/data.xml";
        String file = android.os.Environment.getExternalStorageDirectory().getPath() + fileURL;
        File f = new File(file);
        //if one has been found, load it
        if(f.exists()) {
            return;
        } else {
            //otherwise create a new list
            noteList = new ArrayList<Note>();
            saveList(noteList);
        }
    }

    public void saveList(List<Note> list) {
        //create a new fileOutput stream
        /*NOTE: selected private because it may contain sensitive infomation
            that shouldn't be used in other apps*/
        try{
            ObjectOutputStream out;
            File outFile = new File(Environment.getExternalStorageDirectory(), "brain.txt");
            out = new ObjectOutputStream(new FileOutputStream(outFile));
            out.writeObject(noteList);
            out.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
