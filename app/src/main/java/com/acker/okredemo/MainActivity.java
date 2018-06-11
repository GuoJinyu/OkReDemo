package com.acker.okredemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.acker.okre.bean.Repo;
import com.acker.okre.listener.HttpListResultListener;
import com.acker.okre.model.GitHubModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tvResult = findViewById(R.id.tv_result);
        findViewById(R.id.btn_request_sync).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText(null);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final List<Repo> repos = GitHubModel.getRepoSync("GuoJinyu");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (repos != null && !repos.isEmpty()) {
                                    tvResult.setText(repos.size()+"");
                                } else {
                                    if (repos == null) {
                                        Toast.makeText(MainActivity.this, "" + repos,
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "" + repos.size(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }
                }).start();
            }
        });
        findViewById(R.id.btn_request_async).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText(null);
                GitHubModel.getRepoAsync("GuoJinyu", new HttpListResultListener() {
                    @Override
                    public void onResult(List<?> objects) {
                        if (objects != null) {
                            List<Repo> repos = (List<Repo>) objects;
                            if (!repos.isEmpty()) {
                                tvResult.setText(repos.size()+"");
                            } else {
                                Toast.makeText(MainActivity.this, "" + repos.size(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}
