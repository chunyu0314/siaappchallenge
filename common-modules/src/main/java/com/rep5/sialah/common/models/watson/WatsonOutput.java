package com.rep5.sialah.common.models.watson;

/**
 * Created by low on 3/10/16 9:03 AM.
 */
public class WatsonOutput {
    private String text;
    private String[] nodes_visited;

    public WatsonOutput() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getNodes_visited() {
        return nodes_visited;
    }

    public void setNodes_visited(String[] nodes_visited) {
        this.nodes_visited = nodes_visited;
    }
}
