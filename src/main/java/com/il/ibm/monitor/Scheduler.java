package com.il.ibm.monitor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by cadano on 1/15/17.
 */
public class Scheduler {

    final static Logger logger = LogManager.getLogger(Scheduler.class);

    Monitor monitor;

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    //every 10 minute
    @Scheduled(fixedRate=600000)
    public void scan(){
        logger.info("--------------------------------------------------");
        logger.info("Start new scanning interval");
        logger.info("--------------------------------------------------");
        monitor.monitor();
    }

}
