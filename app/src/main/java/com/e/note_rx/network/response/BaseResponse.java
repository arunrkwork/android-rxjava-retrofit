package com.e.note_rx.network.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("error")
    boolean error;

    @SerializedName("message")
    String message;

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
