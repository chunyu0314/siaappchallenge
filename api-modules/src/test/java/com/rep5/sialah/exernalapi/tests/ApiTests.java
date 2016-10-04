package com.rep5.sialah.exernalapi.tests;

import com.rep5.sialah.externalapi.WaitTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Duration;

/**
 * Created by low on 3/10/16 3:36 PM.
 */
public class ApiTests {

    @Test
    @Ignore
    public void testWaitTime() {
        Duration time = WaitTime.getWaitTime();
        System.out.println(time.toMinutes());
    }
}
