/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.util;

import java.util.List;

/**
 *
 * @author Samrit
 */
public class StringUtil {

    public static String getStringFixedForRegx(String text) {
        String line = text;
        line = line.replace("\\", "\\\\");
        line = line.replace("*", "\\*");
        line = line.replace(".", "\\.");
        line = line.replace("(", "\\(");
        line = line.replace(")", "\\)");
        line = line.replace("+", "\\+");
        line = line.replace("^", "\\^");
        line = line.replace("?", "\\?");
        line = line.replace("}", "\\}");
        line = line.replace("{", "\\{");
        line = line.replace("$", "\\$");
        //  a = a.replaceAll("\\", "\\\\\\");
        return line;
    }

    public static StringBuilder getCommaSeperatedString(List<String> list) {
        String delim = "";
        StringBuilder sb = new StringBuilder();
        for (String i : list) {
            sb.append(delim).append(i);
            delim = ",";
        }
        return sb;
    }

    public static StringBuilder getCommaSeperatedInteger(List<Integer> list) {
        String delim = "";
        StringBuilder sb = new StringBuilder();
        for (Integer i : list) {
            sb.append(delim).append(i);
            delim = ",";
        }
        return sb;
    }

    public static StringBuilder getCommaSeperatedQuotedString(List<String> list) {
        String delim = "";
        StringBuilder sb = new StringBuilder();
        for (String i : list) {
            sb.append(delim).append("\"").append(i).append("\"");
            delim = ",";
        }
        return sb;
    }

}
