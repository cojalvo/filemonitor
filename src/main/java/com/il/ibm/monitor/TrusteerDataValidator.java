package com.il.ibm.monitor;

import com.il.ibm.monitor.common.IDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

/**
 * Created by cadano on 1/15/17.
 */
public class TrusteerDataValidator implements IDataValidator {

    final static Logger logger = LogManager.getLogger(TrusteerDataValidator.class);

    private HashMap<String,String> dataHash = new HashMap<>();
    @Override
    public boolean isValid(String key, String data) {
        if (dataHash.containsKey(key)){
            String prev = dataHash.get(key);
            //update to the latest version
            if (logger.isDebugEnabled()){
                logger.debug(String.format("validating data , current %s prev %s",data,prev));
            }
            dataHash.put(key,data);
            //Valid data is where the prev equals to the current.
            return prev.equals(data);
        }
        dataHash.put(key,data);
        return true;
    }
}
