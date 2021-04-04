package com;

import java.util.ResourceBundle;

public class MyUtil {
    private static final ResourceBundle resourceBundle;

    static {
        resourceBundle = ResourceBundle.getBundle("config");
    }

    public static String getVal(String key){
        return resourceBundle.getString(key);
    }
}
