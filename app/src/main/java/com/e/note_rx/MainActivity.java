package com.e.note_rx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final int NOTE_REQUEST_CODE = 100;

    FloatingActionButton btnFabAdd;

    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    List<Note> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        noteAdapter = new NoteAdapter(this, list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(noteAdapter);

        btnFabAdd = findViewById(R.id.btnFabAdd);
        btnFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNoteActivity();
            }
        });

    }

    private void addNoteActivity() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        intent.putExtra("id", 0);
        intent.putExtra("title", "Arun");
        intent.putExtra("description", "My Description");
        startActivityForResult(intent, NOTE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            if(data != null) {

                int noteId = data.getExtras().getInt("noteId");
                String title = data.getExtras().getString("title");
                String description = data.getExtras().getString("description");

                Log.d(TAG, "onActivityResult: " + noteId + " -- " + title + " --- " + description);

                if(noteId == 0)
                    addNote(title, description);
                 else
                    updateNote(noteId, title, description);

            }
        }
    }

    private void updateNote(int noteId, String title, String description) {

    }

    private void addNote(String title, String description) {
        noteAdapter.addNote(new Note(title, description));
    }
}
