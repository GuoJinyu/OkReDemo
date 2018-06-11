package com.acker.okre.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.acker.okre.bean.Repo;
import com.acker.okre.http.ReManager;
import com.acker.okre.http.Url;
import com.acker.okre.listener.HttpListResultListener;
import com.acker.okre.service.GitHubService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: guojy
 * created on: 2018/6/11 14:56
 * description:
 */
public class GitHubModel {

    private static final String TAG = GitHubModel.class.getSimpleName();

    private static GitHubService getGitHubService() {
        return InstanceHolder.mGitHubService;
    }

    private static class InstanceHolder {
        private static GitHubService mGitHubService = ReManager.getRetrofit(Url.GITHUB_BASE).create(GitHubService.class);
    }

    public static List<Repo> getRepoSync(String userName) {
        Call<List<Repo>> call = getGitHubService().listRepos(userName);
        List<Repo> result;
        try {
            Response<List<Repo>> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                Log.w(TAG, "*******************OkRe Log*********************");
                Log.w(TAG, response.code() + ":" + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getRepoAsync(String userName, final HttpListResultListener mResultListener) {
        Call<List<Repo>> call = getGitHubService().listRepos(userName);
        Callback<List<Repo>> callback = new Callback<List<Repo>>() {
            @Override
            public void onResponse(@NonNull Call<List<Repo>> call, @NonNull Response<List<Repo>> response) {
                if (response.isSuccessful()) {
                    mResultListener.onResult(response.body());
                } else {
                    Log.w(TAG, "*******************OkRe Log*********************");
                    Log.w(TAG, response.code() + ":" + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Repo>> call, @NonNull Throwable t) {
                Log.w(TAG, "*******************OkRe Log*********************");
                Log.w(TAG, t.getLocalizedMessage());
                mResultListener.onFailure();
            }
        };
        call.enqueue(callback);
    }
}
