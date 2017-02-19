package com.il.ibm.monitor;

import com.il.ibm.monitor.common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cadano on 1/15/17.
 *
 * Responsible for managing the monitor process.
 */
public class Monitor {

    final static Logger logger = LogManager.getLogger(Monitor.class);

    private ConfigReader configReader;
    private IDataCalculator dataCalculator;
    private IDataValidator dataValidator;
    private IMessageDeliver messageDeliver;
    private DateRetriever dataRetriever;

    public DateRetriever getDataRetriever() {
        return dataRetriever;
    }

    public void setDataRetriever(DateRetriever dataRetriever) {
        this.dataRetriever = dataRetriever;
    }

    public IDataCalculator getDataCalculator() {
        return dataCalculator;
    }

    public void setDataCalculator(IDataCalculator dataCalculator) {
        this.dataCalculator = dataCalculator;
    }

    public IDataValidator getDataValidator() {
        return dataValidator;
    }

    public void setDataValidator(IDataValidator dataValidator) {
        this.dataValidator = dataValidator;
    }

    public ConfigReader getConfigReader() {
        return configReader;
    }

    public void setConfigReader(ConfigReader configReader) {
        this.configReader = configReader;
    }

    public IMessageDeliver getMessageDeliver() {
        return messageDeliver;
    }

    public void setMessageDeliver(IMessageDeliver messageDeliver) {
        this.messageDeliver = messageDeliver;
    }

    public void monitor(){
        logger.info("Start monitoring session");
        HashMap<String, List<String>> allConfiguration = configReader.getConfigurations();
        allConfiguration.entrySet().forEach(kv->{
            try {
                monitorSingle(kv);
            }catch (Exception e){
                logger.error(e);
            }
        });
    }

    private void monitorSingle(Map.Entry<String,List<String>> single){

        String baseUrl = single.getKey();
        for (String ip:single.getValue()){
            logger.info(String.format("Start checking for %s-%s",baseUrl,ip));
            byte[] data = dataRetriever.getBytesData(baseUrl,ip);
            if (data == null){
                logger.warn(String.format("Failed to receive data from %s-%s",baseUrl,ip));
                continue;
            }
            String sha1 = dataCalculator.calculateData(data);
            if (logger.isDebugEnabled()){
                logger.debug(String.format("SHA1 for %s - %s is %s",baseUrl,ip,sha1));
            }
            if (!dataValidator.isValid(String.format("%s_%s",baseUrl,ip),sha1)){
                logger.warn(String.format("SHA1 for %s-%s is not valid",baseUrl,ip));
                messageDeliver.sendMessage("SHA1 Was changed",String.format("Warning!!! the sha1 of  %s with ip %s has changed",baseUrl,ip));
            }
        }
    }
}
