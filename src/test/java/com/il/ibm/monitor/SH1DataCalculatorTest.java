package com.il.ibm.monitor;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cadano on 1/16/17.
 */
public class SH1DataCalculatorTest {
    @Test
    public void testCalculateDataEquals() throws Exception {
        SH1DataCalculator sh1DataCalculator = new SH1DataCalculator();
        String first = sh1DataCalculator.calculateData("This is my data to test");
        String second = sh1DataCalculator.calculateData("This is my data to test");

        Assert.assertEquals("The sh1 values should be equals",first,second);
    }

    @Test
    public void testCalculateDataNotEquals() throws Exception {
        SH1DataCalculator sh1DataCalculator = new SH1DataCalculator();
        String first = sh1DataCalculator.calculateData("This is my data to test1");
        String second = sh1DataCalculator.calculateData("This is my data to test");

        Assert.assertNotEquals("The sh1 values should not be equals",first,second);
    }

}