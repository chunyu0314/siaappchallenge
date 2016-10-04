package com.rep5.sialah.common.models.watson;

/**
 * Created by low on 3/10/16 9:03 AM.
 */
public class WatsonOutput {
    private String[] text;
    private String[] nodes_visited;
    private Object[] log_messages;

    public WatsonOutput() {
    }

    public Object[] getLog_messages() {
        return log_messages;
    }

    public void setLog_messages(Object[] log_messages) {
        this.log_messages = log_messages;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }

    public String[] getNodes_visited() {
        return nodes_visited;
    }

    public void setNodes_visited(String[] nodes_visited) {
        this.nodes_visited = nodes_visited;
    }
}
