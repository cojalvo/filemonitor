package com.il.ibm.monitor.common;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

/**
 * Created by cadano on 1/15/17.
 */
public class DateRetriever {

    final static Logger logger = LogManager.getLogger(DateRetriever.class);
    public static final int BUFFER_SIZE = 4096;

    public byte[] getBytesData(String url, String ip) {
        try {

            logger.info(String.format("Start fetching data from %s - %s",url,ip));

            url = buildUrl(url, ip);
            URL u = new URL(url);
            URLConnection connection = u.openConnection();
            if (connection instanceof HttpsURLConnection){
                setHostnameVerifier(url, (HttpsURLConnection) connection);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (InputStream is = connection.getInputStream()) {
                byte[] byteChunk = new byte[BUFFER_SIZE];
                int n;

                while ((n = is.read(byteChunk)) > 0) {
                    baos.write(byteChunk, 0, n);
                }

                return  baos.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }catch (Exception e){
            logger.error(e);
        }
        return null;
    }


    private void setHostnameVerifier(String hostNameToVerify,HttpsURLConnection httpsURLConnection){
        HostnameVerifier hostnameVerifier = (hostname, session) -> hostNameToVerify.contains(hostname);

        httpsURLConnection.setHostnameVerifier(hostnameVerifier);
    }

    private String buildUrl(String url, String ip) throws UnknownHostException {

        if (logger.isDebugEnabled()){
            logger.debug(String.format("Start building url %s-%s ",url,ip));
        }
        url = AppUtils.replaceHostWithIp(url,ip);
        return url;
    }
}
