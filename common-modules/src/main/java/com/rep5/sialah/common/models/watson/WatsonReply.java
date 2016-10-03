package com.rep5.sialah.common.models.watson;

import com.rep5.sialah.common.models.Context;

/**
 * Created by low on 3/10/16 9:03 AM.
 */
public class WatsonReply {
    private WatsonInput input;
    private WatsonIntent[] intents;
    private WatsonEntity[] entities;
    private Context context;
    private WatsonOutput output;

    public WatsonInput getInput() {
        return input;
    }

    public void setInput(WatsonInput input) {
        this.input = input;
    }

    public WatsonIntent[] getIntents() {
        return intents;
    }

    public void setIntents(WatsonIntent[] intents) {
        this.intents = intents;
    }

    public WatsonEntity[] getEntities() {
        return entities;
    }

    public void setEntities(WatsonEntity[] entities) {
        this.entities = entities;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public WatsonOutput getOutput() {
        return output;
    }

    public void setOutput(WatsonOutput output) {
        this.output = output;
    }
}
