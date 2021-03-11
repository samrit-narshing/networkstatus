/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core;


import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author Samrit
 */
public class SchedulerConfig {

    @Autowired
    private Logger logger;



//    @Scheduled(cron = "0 30 23 * * ?")
    @Scheduled(cron = "0 */10 * * * ?")
    public void grabageCollectorService() {
//        configService.callGarbageCollector();
    }

}
