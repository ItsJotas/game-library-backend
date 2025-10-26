package com.example.game_library_backend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getCurrentTime(){
        Date time = new Date(System.currentTimeMillis());
        return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(time);
    }
}