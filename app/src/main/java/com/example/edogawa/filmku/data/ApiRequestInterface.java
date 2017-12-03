package com.example.edogawa.filmku.data;

import com.example.edogawa.filmku.data.dao.MovieResponseDao;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Edogawa on 12/1/2017.
 */

public interface ApiRequestInterface {
    @GET("movie/popular")
    Call<MovieResponseDao> getMovieList(@Query("api_key") String api_key);
}
