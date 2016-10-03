package com.rep5.sialah.common.models.watson;

/**
 * Created by low on 3/10/16 9:17 AM.
 */
public class WatsonIntent {
    private String intent;
    private double confidence;

    public WatsonIntent() {
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
