package com.il.ibm.monitor.common;

/**
 * Created by cadano on 1/15/17.
 */
public interface IDataCalculator {
    String calculateData(String rawData);

    String calculateData(byte[] rawData);
}
