package com.rayhahah.dialoglib.utils;

import android.graphics.Color;

/**
 * Created by ares on 2017/2/9.
 */

public class ColorUtil {

    /**
     * 计算从startColor过度到endColor过程中百分比为fraction时的颜色值
     * @param startColor 起始颜色 int类型
     * @param endColor 结束颜色 int类型
     * @param fraction franch 百分比0.5
     * @return 返回int格式的color
     */
    public static int calculateColor(int startColor, int endColor, float fraction){
        String strStartColor = "#" + Integer.toHexString(startColor);
        String strEndColor = "#" + Integer.toHexString(endColor);
        return Color.parseColor(calculateColor(strStartColor, strEndColor, fraction));
    }

    /**
     * 计算从startColor过度到endColor过程中百分比为fraction时的颜色值
     * @param startColor 起始颜色 （格式#FFFFFFFF）
     * @param endColor 结束颜色 （格式#FFFFFFFF）
     * @param fraction 百分比0.5
     * @return 返回String格式的color（格式#FFFFFFFF）
     */
    public static String calculateColor(String startColor, String endColor, float fraction){

        int startAlpha = Integer.parseInt(startColor.substring(1, 3), 16);
        int startRed = Integer.parseInt(startColor.substring(3, 5), 16);
        int startGreen = Integer.parseInt(startColor.substring(5, 7), 16);
        int startBlue = Integer.parseInt(startColor.substring(7), 16);

        int endAlpha = Integer.parseInt(endColor.substring(1, 3), 16);
        int endRed = Integer.parseInt(endColor.substring(3, 5), 16);
        int endGreen = Integer.parseInt(endColor.substring(5, 7), 16);
        int endBlue = Integer.parseInt(endColor.substring(7), 16);

        int currentAlpha = (int) ((endAlpha - startAlpha) * fraction + startAlpha);
        int currentRed = (int) ((endRed - startRed) * fraction + startRed);
        int currentGreen = (int) ((endGreen - startGreen) * fraction + startGreen);
        int currentBlue = (int) ((endBlue - startBlue) * fraction + startBlue);

        return "#" + getHexString(currentAlpha) + getHexString(currentRed)
                + getHexString(currentGreen) + getHexString(currentBlue);

    }

    /**
     * 将10进制颜色值转换成16进制。
     */
    public static String getHexString(int value) {
        String hexString = Integer.toHexString(value);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }


    /**
     * 根据fraction值来计算当前的颜色。
     * @param startColor 起始颜色
     * @param endColor 结束颜色
     * @param fraction 进度百分比
     * @return
     */
    public static int getCurrentColor(int startColor, int endColor,float fraction) {
        int redCurrent;
        int blueCurrent;
        int greenCurrent;
        int alphaCurrent;

        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int alphaStart = Color.alpha(startColor);

        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);
        int alphaEnd = Color.alpha(endColor);

        int redDifference = redEnd - redStart;
        int blueDifference = blueEnd - blueStart;
        int greenDifference = greenEnd - greenStart;
        int alphaDifference = alphaEnd - alphaStart;

        redCurrent = (int) (redStart + fraction * redDifference);
        blueCurrent = (int) (blueStart + fraction * blueDifference);
        greenCurrent = (int) (greenStart + fraction * greenDifference);
        alphaCurrent = (int) (alphaStart + fraction * alphaDifference);

        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent);
    }
}
