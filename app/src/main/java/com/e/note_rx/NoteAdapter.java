package com.e.note_rx;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.note_rx.model.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private static final String TAG = "NoteAdapter";
    Context context;
    List<Note> list;

    public NoteAdapter(Context context, List<Note> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.txtTitle.setText(list.get(position).getTitle());
        holder.txtDescription.setText(list.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle, txtDescription;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
        }
    }
}
