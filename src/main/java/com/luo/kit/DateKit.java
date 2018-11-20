package com.luo.kit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by luoyuanq on 2018/11/20.
 */
public class DateKit {


    public static LocalDate date2LocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }


    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date localDate2Date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }


 /*   public static String format(LocalDate localDate, String pattern) {

    }

    public static String format(LocalDateTime localDateTime, String pattern) {

    }*/

    public static void main(String[] args) {

        System.out.println( date2LocalDate(new Date()).plusDays(7).toString());
    }

}
