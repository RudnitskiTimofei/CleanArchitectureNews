package by.it.andersen.cleanarchitecturenews.mitch.presentation.util;

import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String getDate(){
        Date date = new Date();
        return date.toString();
    }

    public static String getLanguage(){
        Locale myLocale = new Locale("blr", "BLR");
        return myLocale.getLanguage();
    }

    public static String getCountry(){
        Locale myLocale = new Locale("blr", "BLR");
        return myLocale.getCountry();
    }
}
