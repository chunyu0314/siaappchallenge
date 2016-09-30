package com.clef.restapi;

import com.clef.infra.commons.services.ClefUUID;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.UUID;

/**
 * Created by low on 17/7/16 6:04 PM.
 */
public class ContextInit implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        UserFactory.init();
        ResponseRetrieve.init();
        //MessageSort.init(instanceId);
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg1) {
        //MessageSort.close();
    }

}
