package com.rep5.sialah.convo;

import com.alibaba.fastjson.JSON;
import com.rep5.sialah.common.ContextCache;
import com.rep5.sialah.common.RestClient;
import com.rep5.sialah.common.models.Context;
import com.rep5.sialah.common.models.SiaMessage;
import com.rep5.sialah.common.models.watson.WatsonInput;
import com.rep5.sialah.common.models.watson.WatsonPacket;
import com.rep5.sialah.common.models.watson.WatsonReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by low on 3/10/16 9:55 PM.
 */
public class ConvoImpl {

    private static final Logger logger = LoggerFactory.getLogger(ConvoImpl.class);

    public static SiaMessage useConvo(SiaMessage message) {

        logger.info("sending msg: " + JSON.toJSONString(message));

        WatsonInput input = new WatsonInput();
        input.setText(message.getMessage());

        WatsonPacket packet = new WatsonPacket();
        packet.setContext(message.getContext());
        packet.setInput(input);

        Response response = ConvoClient.getWatsonTarget().request()
                .post(Entity.entity(JSON.toJSONString(packet), MediaType.APPLICATION_JSON));
        WatsonReply reply = response.readEntity(WatsonReply.class);

        logger.info("raw watsonreply object: " + JSON.toJSONString(reply));
        SiaMessage msg = new SiaMessage();
        msg.setMessage(reply.getOutput().getText()[0]);
        msg.setContext(reply.getContext());
        ContextCache.cacheContext(reply.getContext());

        logger.info("received Watson Reply: " + JSON.toJSONString(msg));

        return msg;

    }
}
