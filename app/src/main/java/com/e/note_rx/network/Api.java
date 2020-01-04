package com.e.note_rx.network;


import com.e.note_rx.network.response.CommonNoteResponse;
import com.e.note_rx.network.response.NoteResponse;
import com.e.note_rx.utils.Const;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

//    @FormUrlEncoded
//    @POST(Const.NOTE)
//    Call<ResponseBody> createNote(
//            @Field("title") String title,
//            @Field("desc") String desc
//    );

    @FormUrlEncoded
    @POST(Const.NOTE)
    Call<CommonNoteResponse> createNote(
            @Field("title") String title,
            @Field("desc") String desc
    );

    @FormUrlEncoded
    @PUT(Const.NOTE + "/{id}")
    Call<CommonNoteResponse> updateNote(
            @Path("id") int id,
            @Field("title") String title,
            @Field("desc") String desc
    );

    @DELETE(Const.NOTE + "/{id}")
    Call<CommonNoteResponse> deleteNote(
            @Path("id") int id
    );

    @GET(Const.NOTE)
    Call<NoteResponse> getNotes();

//    @GET(Const.NOTE)
//    Call<ResponseBody> getNotes();

}
