package com.rep5.sialah.ibm.kafka;

/**
 * Created by low on 24/5/16.
 * onReceipt() called when single data received
 */
public interface ConsumerListener {
     void onReceipt(final String result);
}
