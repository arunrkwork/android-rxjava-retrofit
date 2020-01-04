package com.e.note_rx.network.response;

import com.google.gson.annotations.SerializedName;

public class CommonNoteResponse extends BaseResponse {

    @SerializedName("data")
    private Data data;

    public class Data {

        @SerializedName("noteId")
        private int noteId;

        public int getNoteId() {
            return noteId;
        }
    }

    public Data getData() {
        return data;
    }
}
