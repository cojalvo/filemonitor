package com.il.ibm.monitor.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cadano on 1/16/17.
 */
public class AppUtils {

    /**
     * Replace the hostname with ip.
     * In case the ip is * get the ip of the host from the DNS and replace it.
     * @param orgUrl
     * @param ip
     * @return
     * @throws UnknownHostException
     */
    public static String replaceHostWithIp(String orgUrl,String ip) throws UnknownHostException {
        Pattern pattern = Pattern.compile("(https?://)([^:^/]*)(:\\d*)?(.*)?");
        Matcher matcher = pattern.matcher(orgUrl);
        matcher.find();
        String domain = matcher.group(2);
        if (ip != null && ip.equals("*")) {
            InetAddress address = InetAddress.getByName(domain);
            ip = address.getHostAddress();
        }
        return orgUrl.replace(domain, ip);
    }
}
