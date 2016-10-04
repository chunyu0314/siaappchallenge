package com.rep5.sialah.common;

import com.rep5.sialah.common.models.Context;

/**
 * Created by low on 3/10/16 10:00 PM.
 */
public class ContextCache {
    private static Context context;

    public static Context getContext() {
        if (context == null) {
            init();
        }
        return context;
    }

    private static void init() {

    }

    public static void cacheContext(Context context) {
        ContextCache.context = context;
    }
}
