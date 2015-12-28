package com.updg.daemon.utils;

/**
 * Created by Alex
 * Date: 09.11.13  19:42
 */
public class ArrayUtils {
    public static String[] join(String[]... arrays) {
        // calculate size of target array
        int size = 0;
        for (String[] array : arrays) {
            size += array.length;
        }

        // create list of appropriate size
        java.util.List list = new java.util.ArrayList(size);

        // add arrays
        for (String[] array : arrays) {
            list.addAll(java.util.Arrays.asList(array));
        }

        // create and return final array
        return (String[]) list.toArray(new String[size]);
    }
}
