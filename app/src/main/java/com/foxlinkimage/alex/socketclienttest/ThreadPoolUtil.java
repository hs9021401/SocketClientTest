package com.foxlinkimage.alex.socketclienttest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Alex on 2015/8/13.
 */
public class ThreadPoolUtil {
    private static ExecutorService instance = null;

    private ThreadPoolUtil() {
    }

    public static ExecutorService getInstance() {
        if (instance == null)
            instance = Executors.newCachedThreadPool();
        return instance;
    }
}
