package com.e.note_rx.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.e.note_rx.ClickListener;
import com.e.note_rx.NoteAdapter;
import com.e.note_rx.R;
import com.e.note_rx.RecyclerTouchListener;
import com.e.note_rx.model.Note;
import com.e.note_rx.network.response.CommonNoteResponse;
import com.e.note_rx.network.response.NoteResponse;
import com.e.note_rx.network.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final int CREATE_NOTE_REQUEST_CODE = 100;
    public static final int UPDATE_NOTE_REQUEST_CODE = 101;

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


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                updateNoteActivity(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                deleteNoteDialog(position);
            }
        }));


        btnFabAdd = findViewById(R.id.btnFabAdd);
        btnFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNoteActivity();
            }
        });


        readNotes();

    }

    private void deleteNoteDialog(int position) {

        AlertDialog.Builder builder = createDialog("Action",
                "Do you want delete this note?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                deleteNote(position, list.get(position).getId());
                Toast.makeText(MainActivity.this, "position : " + position  + " list : " + list.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                dialogInterface.cancel();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                dialogInterface.cancel();
            }
        });


        AlertDialog dialog = builder.create();

        dialog.show();

    }

    private AlertDialog.Builder createDialog(String title, String message) {
        return new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void addNoteActivity() {
        startActivityForResult(
                new Intent(this, AddNoteActivity.class),
                CREATE_NOTE_REQUEST_CODE);
    }

    private void updateNoteActivity(int position) {
        Note note = list.get(position);
        Intent intent = new Intent(this, AddNoteActivity.class);
        intent.putExtra("index", position);
        intent.putExtra("id", note.getId());
        intent.putExtra("title", note.getTitle());
        intent.putExtra("description", note.getDescription());
        startActivityForResult(intent, UPDATE_NOTE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String title = data.getExtras().getString("title");
                String description = data.getExtras().getString("description");
                addNote(title, description);
            }
        } else if (requestCode == UPDATE_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                int index = data.getExtras().getInt("index");
                int id = data.getExtras().getInt("id");
                String title = data.getExtras().getString("title");
                String description = data.getExtras().getString("description");
                updateNote(index, id, title, description);
            }
        }
    }

    private void deleteNote(int index, int noteId) {
        Call<CommonNoteResponse> call = RetrofitClient.getInstance()
                .getApi()
                .deleteNote(noteId);

        call.enqueue(new Callback<CommonNoteResponse>() {
            @Override
            public void onResponse(Call<CommonNoteResponse> call, Response<CommonNoteResponse> response) {
                if (response.code() == 201) {
                    Log.d(TAG, "onResponse: " + response.body().getData().getNoteId());
                    removeNotePosition(index);
                } else {
                    // Log.d(TAG, "onResponse: " + response.body().getData().getNoteId());
                }
            }

            @Override
            public void onFailure(Call<CommonNoteResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void updateNote(int index, int noteId, String title, String description) {
        Call<CommonNoteResponse> call = RetrofitClient.getInstance()
                .getApi()
                .updateNote(noteId, title, description);

        call.enqueue(new Callback<CommonNoteResponse>() {
            @Override
            public void onResponse(Call<CommonNoteResponse> call, Response<CommonNoteResponse> response) {
                if (response.code() == 201) {
                    Log.d(TAG, "onResponse: " + response.body().getData().getNoteId());
                    Note note = new Note(noteId, title, description);

                    updateNotePosition(index, note);
                } else {

                }
            }

            @Override
            public void onFailure(Call<CommonNoteResponse> call, Throwable t) {

            }
        });
    }

    private void addNote(String title, String description) {

        Call<CommonNoteResponse> call = RetrofitClient.getInstance()
                .getApi()
                .createNote(title, description);

        call.enqueue(new Callback<CommonNoteResponse>() {
            @Override
            public void onResponse(Call<CommonNoteResponse> call, Response<CommonNoteResponse> response) {
                if (response.code() == 201) {
                    Log.d(TAG, "onResponse: " + response.body().getData().getNoteId());
                    int id = response.body().getData().getNoteId();
                    addNotePosition(new Note(id, title, description));
                } else {

                }
            }

            @Override
            public void onFailure(Call<CommonNoteResponse> call, Throwable t) {

            }
        });

//        Call<ResponseBody> call = RetrofitClient.getInstance()
//                .getApi()
//                .createNote(title, description);
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                if (response.code() == 201) {
//                    String res = null;
//                    try {
//                        res = response.body().string();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        JSONObject jsonObject = new JSONObject(res);
//                        boolean error = jsonObject.getBoolean("error");
//                        String message = jsonObject.getString("message");
//                        Log.d(TAG, "onResponse: " + jsonObject.getJSONObject("data"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + t.getMessage());
//            }
//        });
    }

    public void readNotes() {
        Call<NoteResponse> readCall = RetrofitClient.getInstance()
                .getApi()
                .getNotes();
        readCall.enqueue(new Callback<NoteResponse>() {
            @Override
            public void onResponse(Call<NoteResponse> call, Response<NoteResponse> response) {
                List<Note> list = response.body().getData().getList();
                addNotesPosition(list);
            }

            @Override
            public void onFailure(Call<NoteResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void addNotePosition(Note note) {
        this.list.add(0, note);
        Log.d(TAG, "addNotePosition: size " + this.list.size());
        noteAdapter.notifyItemInserted(0);
    }

    public void removeNotePosition(int index) {
        this.list.remove(index);
        noteAdapter.notifyItemRemoved(index);
    }

    public void updateNotePosition(int index, Note note) {
        this.list.set(index, note);
        noteAdapter.notifyItemChanged(index);
    }

    public void addNotesPosition(List<Note> list) {
        this.list.clear();
        this.list.addAll(list);
        noteAdapter.notifyDataSetChanged();
        Log.d(TAG, "addNotesPosition: size " + this.list.size());
    }
}
