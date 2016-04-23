package com.austry.mobilization.utils;


import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TracksStringUtilsTest {
    @Test
    public void zero() {
        assertEquals("0 песен", StringUtils.getTracks(0));
    }

    @Test
    public void negative() {
        assertEquals("0 песен", StringUtils.getTracks(-17));
    }

    @Test
    public void one() {
        assertEquals("1 песня", StringUtils.getTracks(1));
        assertEquals("241 песня", StringUtils.getTracks(241));
    }

    @Test
    public void exceptionalCases() {
        assertEquals("12 песен", StringUtils.getTracks(12));
        assertEquals("13 песен", StringUtils.getTracks(13));
        assertEquals("14 песен", StringUtils.getTracks(14));

        assertEquals("412 песен", StringUtils.getTracks(412));
        assertEquals("413 песен", StringUtils.getTracks(413));
        assertEquals("414 песен", StringUtils.getTracks(414));

        assertEquals("11 песен", StringUtils.getTracks(11));
    }

    @Test
    public void regularCases() {
        assertEquals("22 песни", StringUtils.getTracks(22));
        assertEquals("435 песен", StringUtils.getTracks(435));
        assertEquals("548 песен", StringUtils.getTracks(548));
    }
}
