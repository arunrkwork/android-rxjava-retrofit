package com.e.note_rx.network;


import com.e.note_rx.utils.Const;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST(Const.CREATE_NOTE)
    Call<ResponseBody> createNote(
            @Field("title") String title,
            @Field("desc") String desc
    );
}
