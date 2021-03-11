/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Samrit
 */
public class ColorUtil {
      public static void main(String args[]) {
//        Set<String> set = getUniqueRandomColor(5);
//        for (String s : set) {
//            System.out.println(s);
//        }

        System.out.println(getUniqueRandomColorInChartFormat(20));
    }

    public static Set<String> getUniqueRandomColor(int num) {
        Set<String> colors = new HashSet<String>();
        while (colors.size() < num) {
            Random rand = new Random();
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            colors.add(toHexCode(r, g, b));
        }
        return colors;
    }

    public static String getUniqueRandomColorInChartFormat(int num) {
        String color = "";
        Set<String> colors = getUniqueRandomColor(num);
        int count = 0;
        for (String co : colors) {
            count += 1;
            color += co;
            if (count < colors.size()) {
                color += ",";
            }
        }
        return color;
    }

    /**
     * Returns a web browser-friendly HEX value representing the colour in the
     * default sRGB ColorModel.
     *
     * @param r red
     * @param g green
     * @param b blue
     * @return a browser-friendly HEX value
     */
    public static String toHexCSS(int r, int g, int b) {
        return "#" + toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b);
    }

    public static String toHexCode(int r, int g, int b) {
        return toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b);
    }

    private static String toBrowserHexValue(int number) {
        StringBuilder builder = new StringBuilder(Integer.toHexString(number & 0xff));
        while (builder.length() < 2) {
            builder.append("0");
        }
        return builder.toString().toUpperCase();
    }
}
