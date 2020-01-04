package com.e.note_rx.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.note_rx.R;

public class AddNoteActivity extends AppCompatActivity {

    EditText edTitle, edDescription;
    Button btnAdd;
    int noteId = 0, index = 0;
    String title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            index = bundle.getInt("index");
            noteId = bundle.getInt("id");
            title = bundle.getString("title");
            description = bundle.getString("description");
        }

        edTitle = findViewById(R.id.edTitle);
        edDescription = findViewById(R.id.edDescription);
        btnAdd = findViewById(R.id.btnAdd);

        if (noteId != 0 ) {
            edTitle.setText(title);
            edDescription.setText(description);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });

    }

    private void addNote() {
        title = edTitle.getText().toString();
        description = edDescription.getText().toString();

        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please add note", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("index", index);
        intent.putExtra("id", noteId);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        setResult(RESULT_OK, intent);
        finish();
    }
}
