package com.il.ibm.monitor;

import com.il.ibm.monitor.common.IDataCalculator;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by cadano on 1/15/17.
 *
 * Implementation of @{@link IDataCalculator} which calculate the SHA1 of the data.
 */
public class SH1DataCalculator  implements IDataCalculator{
    @Override
    public String calculateData(String rawData) {
        return DigestUtils.sha1Hex(rawData);
    }

    @Override
    public String calculateData(byte[] rawData) {
        return DigestUtils.sha1Hex(rawData);
    }
}
