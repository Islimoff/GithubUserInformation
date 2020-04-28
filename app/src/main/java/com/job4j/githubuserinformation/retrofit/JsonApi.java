package com.job4j.githubuserinformation.retrofit;

import com.job4j.githubuserinformation.models.Repository;
import com.job4j.githubuserinformation.models.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonApi {

    @GET("users/{name}")
    Single<User> getUser(@Path("name") String name);

    @GET("/users/{name}/repos")
    Single<List<Repository>> getRepositories(@Path("name") String name);
}
