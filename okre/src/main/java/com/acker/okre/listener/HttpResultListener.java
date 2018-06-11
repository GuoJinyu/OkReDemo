package com.acker.okre.listener;

/**
 * author: guojy
 * created on: 2018/6/11 15:06
 * description:
 */
public interface HttpResultListener {
    void onResult(Object object);

    void onFailure();
}
