package com.il.ibm.monitor.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cadano on 1/15/17.
 */
@Configuration
@PropertySources({
        @PropertySource(value = "file:${config.path}")

})
public class ConfigReader {
    final static Logger logger = LogManager.getLogger(ConfigReader.class);

    @Autowired
    Environment env;


    private String configFilePath;
    private String smtpServer;
    private String[] to;
    private String from;
    private String  smtpUserName;
    private String  smtpPassword;

    public String getSmtpUserName() {
        return smtpUserName;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    /**
     * Initialize all config data and validate the content..
     */
    public void init() {

        if (logger.isTraceEnabled()){
            logger.trace("Init Config reader stated");
        }
        configFilePath = env.getProperty("configuration.file");
        validateData(configFilePath,"configuration.file");
        from = env.getProperty("mail.from");
        validateData(from,"mail.from");
        smtpServer = env.getProperty("mail.server");
        validateData(smtpServer,"mail.server");
        smtpUserName = env.getProperty("mail.user");
        validateData(smtpUserName,"mail.user");
        smtpPassword = env.getProperty("mail.password");
        validateData(smtpPassword,"mail.password");
        String toString = env.getProperty("mail.to");
        if (toString != null){
            to = toString.split(";");
        }

        if (logger.isTraceEnabled()){
            logger.trace("Init Config reader fnished");
        }
    }

    private void validateData(String data,String propName){
        if (data == null || data.equals("")){
            throw new IllegalArgumentException(String.format("Property %s is missing",propName));
        }
    }

    public String getConfigFilePath(){
        return configFilePath;
    }

    public void setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    public String getSmtpServer(){
        return smtpServer;
    }

    public Environment getEnv() {
        return env;
    }

    public String[] getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    /**
     * Read the configuration file from the disk.
     * Enable the user to change the configuration at runtime.
     * @return
     */
    public HashMap<String,List<String>> getConfigurations() {
        try {
            if (logger.isDebugEnabled()){
                logger.debug(String.format("Reading configuration file %s",configFilePath));
            }
            HashMap<String,List<String>> ret = new HashMap<>();
            List<String> lines = Files.readAllLines(Paths.get(configFilePath));
            lines.stream().forEach(s->{
                if (s != null) {
                    String[] tokens = s.trim().split("\\s+");
                    String host = tokens[0].trim();
                    List<String> ips =  ret.containsKey(host)? ret.get(host):new ArrayList<>();
                    ips.add(tokens[tokens.length -1].trim());
                    ret.put(host,ips);
                }
            });

            return ret;

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null;
    }
}
