package com.e.note_rx.network.response;

import com.e.note_rx.model.Note;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoteResponse extends BaseResponse {


    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {

        @SerializedName("notes")
        List<Note> list;

        public List<Note> getList() {
            return list;
        }
    }

}
