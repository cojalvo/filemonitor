package com.il.ibm.monitor.common;

import com.il.ibm.monitor.SH1DataCalculator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by cadano on 1/15/17.
 */
public class DateRetrieverTest {
    @Test
    public void testGetBytesData() throws Exception {
        DateRetriever dateRetriever = new DateRetriever();
        byte [] d = dateRetriever.getBytesData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSitUx0EF8YeFp_A7fhESMutjN78j060B_YdW3qJOYD1PyLWGCM","*");
        byte [] d2 = dateRetriever.getBytesData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSitUx0EF8YeFp_A7fhESMutjN78j060B_YdW3qJOYD1PyLWGCM","*");
        SH1DataCalculator sh1DataCalculator = new SH1DataCalculator();
        String sh1d = sh1DataCalculator.calculateData(d);
        String sh1d2 = sh1DataCalculator.calculateData(d2);
        assertEquals(sh1d,sh1d2);
    }
}