package com.austry.mobilization.utils;


import org.junit.Test;

import static junit.framework.Assert.assertEquals;

//Тесты утилиты на предмет правильного склонения существительных
public class AlbumsStringUtilsTest {
    @Test
    public void zero() {
        assertEquals("0 альбомов", StringUtils.getAlbums(0));
    }

    @Test
    public void negative() {
        assertEquals("0 альбомов", StringUtils.getAlbums(-15));
    }

    @Test
    public void one() {

        assertEquals("1 альбом", StringUtils.getAlbums(1));
        assertEquals("131 альбом", StringUtils.getAlbums(131));
    }

    @Test
    public void exceptionalCases() {
        assertEquals("12 альбомов", StringUtils.getAlbums(12));
        assertEquals("13 альбомов", StringUtils.getAlbums(13));
        assertEquals("14 альбомов", StringUtils.getAlbums(14));

        assertEquals("212 альбомов", StringUtils.getAlbums(212));
        assertEquals("213 альбомов", StringUtils.getAlbums(213));
        assertEquals("214 альбомов", StringUtils.getAlbums(214));

        assertEquals("11 альбомов", StringUtils.getAlbums(11));
    }

    @Test
    public void regularCases(){
        assertEquals("22 альбома", StringUtils.getAlbums(22));
        assertEquals("435 альбомов", StringUtils.getAlbums(435));
    }
}
