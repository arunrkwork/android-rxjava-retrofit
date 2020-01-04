package com.e.note_rx.network;


import com.e.note_rx.network.response.CommonNoteResponse;
import com.e.note_rx.network.response.NoteResponse;
import com.e.note_rx.utils.Const;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    // create note
    @FormUrlEncoded
    @POST(Const.NOTE)
    Single<CommonNoteResponse> createNote(
            @Field("title") String title,
            @Field("desc") String desc
    );

    // update note
    @FormUrlEncoded
    @PUT(Const.NOTE + "/{id}")
    Completable updateNote(
            @Path("id") int id,
            @Field("title") String title,
            @Field("desc") String desc
    );

    // delete note
    @DELETE(Const.NOTE + "/{id}")
    Completable deleteNote(
            @Path("id") int id
    );

    // get all note
    @GET(Const.NOTE)
    Single<NoteResponse> getNotes();

//    @GET(Const.NOTE)
//    Call<ResponseBody> getNotes();

}
