package com.austry.mobilization.utils;


import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;

public class GenresStringUtilsTest {

    @Test
    public void genresBasic(){
        List<String> mockGenres = asList("rock","heavy metal");
        String expected = "rock, heavy metal";
        assertEquals(expected, StringUtils.getGenres(mockGenres));
    }

    @Test
    public void genresNull(){
        String expected = "";
        assertEquals(expected, StringUtils.getGenres(null));
    }

    @Test
    public void genresEmptyList(){
        String expected = "";
        assertEquals(expected,StringUtils.getGenres(Collections.<String>emptyList()));
    }



}
