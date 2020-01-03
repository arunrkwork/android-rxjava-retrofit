package com.e.note_rx.network;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("error")
    boolean error;

    @SerializedName("message")
    String message;
}
