package com.utapass.topwords.api;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("/api/terms/")
    Call<HashMap<String, ArrayList<String>>> doGetTopWordsList();
}
