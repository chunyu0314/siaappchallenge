package com.rep5.sialah.convo.tests;

import com.alibaba.fastjson.JSON;
import com.rep5.sialah.common.models.Context;
import com.rep5.sialah.common.models.SiaData;
import com.rep5.sialah.common.models.SiaMessage;
import com.rep5.sialah.convo.ConvoImpl;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by low on 4/10/16 9:27 AM.
 */
public class ConvoTest {

    @Ignore
    @Test
    public void test() {

        SiaData data = new SiaData();
        data.setFakeBooking(true);

        Context context = new Context();
        context.setSiaData(data);
        SiaMessage message = new SiaMessage();
        message.setMessage("Hello!");
        message.setContext(context);


        System.out.println(ConvoImpl.useConvo(message).getMessage());
    }
}
