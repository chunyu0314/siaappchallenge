package com.rep5.sialah.common.models.watson;

import com.rep5.sialah.common.models.Context;

/**
 * Created by low on 3/10/16 8:59 AM.
 */
public class WatsonPacket {
    private WatsonInput input;
    private WatsonIntent[] intents;
    private WatsonEntity[] entities;
    private boolean alternate_intents;
    private Context context;

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

    public boolean isAlternate_intents() {
        return alternate_intents;
    }

    public void setAlternate_intents(boolean alternate_intents) {
        this.alternate_intents = alternate_intents;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
