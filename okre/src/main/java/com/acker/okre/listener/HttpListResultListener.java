package com.acker.okre.listener;

import java.util.List;

/**
 * author: guojy
 * created on: 2018/6/11 15:06
 * description:
 */
public interface HttpListResultListener {
    void onResult(List<?> objects);

    void onFailure();
}
