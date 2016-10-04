package com.rep5.sialah.webendpoint;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by low on 17/7/16 6:04 PM.
 */
public class ContextInit implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        //TODO uncomment if kafka is needed
        //MessageFactory.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg1) {
        //MessageSort.close();
    }

}
