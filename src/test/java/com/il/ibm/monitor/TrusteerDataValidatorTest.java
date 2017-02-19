package com.il.ibm.monitor;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cadano on 1/16/17.
 */
public class TrusteerDataValidatorTest {
    @Test
    public void isValid() throws Exception {
        TrusteerDataValidator trusteerDataValidator = new TrusteerDataValidator();
        Assert.assertTrue(trusteerDataValidator.isValid("key","myData"));
        Assert.assertTrue(trusteerDataValidator.isValid("key","myData"));
        Assert.assertFalse(trusteerDataValidator.isValid("key","myData2"));
        Assert.assertTrue(trusteerDataValidator.isValid("key2","myData2"));
    }
}