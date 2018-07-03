package com.phonglongapp.xk.phuclongserverapp.Common;

import java.text.NumberFormat;
import java.util.Locale;

public class Common {
    public static boolean checkChooseImageFromAdapter = false;
    public static String ConvertIntToMoney(String money){
        return NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(money)) + " VNĐ";
    }
}
