package com.austry.mobilization.utils;

import java.util.List;
import java.util.Locale;

public class StringUtils {

    private static final String[] albumsVariants = {"альбом", "альбома", "альбомов"};
    private static final String[] tracksVariants = {"песня", "песни", "песен"};

    public static String getGenres(List<String> genres) {
        if (genres == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String genre : genres) {
            sb.append(genre);
            sb.append(", ");
        }
        if (sb.length() >= 2) {
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.toString();
    }

    public static String getAlbums(int albums) {
        return pickRightsVariant(albums, albumsVariants);
    }

    public static String getTracks(int tracks) {
        return pickRightsVariant(tracks, tracksVariants);
    }

    private static String pickRightsVariant(int number, String[] variants){
        if(number < 0){
            number = 0;
        }
        int modulo = number % 10;
        String result;
        switch (modulo) {
            case 1:
                if (endsOnEleven(number)) {
                    result = variants[2];
                } else {
                    result = variants[0];
                }
                break;
            case 2:
            case 3:
            case 4:
                if (isExceptionCase(number)) {
                    result = variants[2];
                } else {
                    result = variants[1];
                }
                break;
            default:
                result = variants[2];
                break;
        }
        return String.format(Locale.getDefault(), "%d %s", number, result);
    }


    private static boolean isExceptionCase(int albums) {
        String stringValue = String.valueOf(albums);
        return stringValue.endsWith("12") ||
                stringValue.endsWith("13") ||
                stringValue.endsWith("14");
    }

    private static boolean endsOnEleven(int albums) {
        return String.valueOf(albums).endsWith("11");
    }


}
