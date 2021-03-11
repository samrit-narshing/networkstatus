/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.internal;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Set;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author Samrit
 */
@WebListener()
public class ServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("SimC-Logger - Context Initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Deregistering Driver To resolve possible memoryloss issue 
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver d = null;
        while (drivers.hasMoreElements()) {
            try {
                d = drivers.nextElement();
                DriverManager.deregisterDriver(d);
                System.out.println(String.format("Driver %s deregistered", d));
            } catch (SQLException ex) {
                System.out.println(String.format("Error deregistering driver %s", d));
            }
        }
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
        for (Thread t : threadArray) {
            if (t.getName().contains("Abandoned connection cleanup thread")) {
                synchronized (t) {
                    t.stop(); //don't complain, it works
                }
            }
        }
        System.out.println("SimC-Logger - Context Destroyed");
    }
}
