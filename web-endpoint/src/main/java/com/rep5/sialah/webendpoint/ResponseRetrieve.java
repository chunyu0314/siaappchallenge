package com.rep5.sialah.webendpoint;

import com.clef.infra.commons.models.ResultResponse;
import com.clef.infra.commons.services.ResponseStore;
import com.clef.infra.elasticsearch.modules.BasicModules;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by low on 27/7/16 3:51 PM.
 */
public class ResponseRetrieve {
    private static ResponseStore store;

    public static void init() {
        Injector injector = Guice.createInjector(new BasicModules());
        store = injector.getInstance(ResponseStore.class);
    }

    public static ResultResponse retrieve(String transactionId) {
        return store.getResultResponse(transactionId);
    }

    public static void acknowledge(String transactionId) {
        store.deleteIndex(transactionId);
    }
}
