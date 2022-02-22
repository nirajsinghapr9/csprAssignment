package com.example.csparmaps.apiService;

import com.example.csparmaps.model.APiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface RetrofitService {

    @GET
    Observable<APiResponse> getProfile(@Url String url);

}
