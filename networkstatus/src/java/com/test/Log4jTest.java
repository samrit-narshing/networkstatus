/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

/**
 *
 * @author SAM
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jTest
{

static final Logger logger = LogManager.getLogger(Logger.class.getName());

public static void main(String[] args)
{   
    logger.trace("trace");
    logger.debug("debug");
    logger.info("info");
    logger.warn("warn");
    logger.error("error");
    logger.fatal("fatal");
}

}
