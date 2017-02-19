package com.il.ibm.monitor.common;

import org.junit.Test;

import java.net.UnknownHostException;

import static org.junit.Assert.*;

/**
 * Created by cadano on 1/16/17.
 */
public class AppUtilsTest {
    @Test
    public void testIpReplaceHttps() throws UnknownHostException {
        String ip = "127.0.0.1";
        String orgIrl = "https://www.tutorialspoint.com/java/java_sending_email.htm";
        orgIrl = AppUtils.replaceHostWithIp(orgIrl,ip);
        assertEquals("https://127.0.0.1/java/java_sending_email.htm",orgIrl);
    }

    @Test
    public void testIpReplaceHttp() throws UnknownHostException {
        String ip = "127.0.0.1";
        String orgIrl = "http://www.tutorialspoint.com/java/java_sending_email.htm";
        orgIrl = AppUtils.replaceHostWithIp(orgIrl,ip);
        assertEquals("http://127.0.0.1/java/java_sending_email.htm",orgIrl);
    }


    @Test
    public void testIpReplaceHttpWithPort() throws UnknownHostException {
        String ip = "127.0.0.1";
        String orgIrl = "http://www.tutorialspoint.com:8080/java/java_sending_email.htm";
        orgIrl = AppUtils.replaceHostWithIp(orgIrl,ip);
        assertEquals("http://127.0.0.1:8080/java/java_sending_email.htm",orgIrl);
    }
}