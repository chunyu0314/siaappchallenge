package com.rep5.sialah.common;

import com.rep5.sialah.common.models.ChatBotMessage;

/**
 * Created by low on 5/10/16 9:43 PM.
 */
public class ConvoStore {
    private static ChatBotMessage[] messages;

    public ConvoStore() {
    }

    public static ChatBotMessage[] getMessages() {
        return messages;
    }

    public static void setMessages(ChatBotMessage[] messages) {
        ConvoStore.messages = messages;
    }
}
