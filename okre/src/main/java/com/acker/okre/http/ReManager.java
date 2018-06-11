package com.acker.okre.http;

import com.acker.okre.service.GitHubService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: guojy
 * created on: 2018/6/8 18:19
 * description:
 */
public class ReManager {

    private static Retrofit retrofit;
    private static GitHubService mGitHubService;

    public static Retrofit getDefaultRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Url.GITHUB_BASE)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofit(String url) {
        return new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
