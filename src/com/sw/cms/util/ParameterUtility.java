package com.sw.cms.util;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringFilter;

public class ParameterUtility {

    public static int getIntParameter(HttpServletRequest req, String key) throws Exception {
        if (req.getParameter(key) == null)
            throw new NullPointerException("Then value is NULL when get parameter " + key +
                                           "from request");

        return Integer.parseInt(req.getParameter(key).trim());

    }

    public static int getIntParameter(HttpServletRequest req, String key, int v) {
        try {
            return Integer.parseInt(req.getParameter(key).trim());
        }
        catch (Exception ex) {
            //exc.printStackTrace();
            return v;
        }
    }

    public static long getLongParameter(HttpServletRequest req, String key) throws Exception {
        if (req.getParameter(key) == null)
            throw new NullPointerException("Then value is NULL when get parameter " + key +
                                           "from request");
        return Long.parseLong(req.getParameter(key).trim());

    }

    public static long getLongParameter(HttpServletRequest req, String key, long v) {
        try {
            return Long.parseLong(req.getParameter(key).trim());
        }
        catch (Exception ex) {
            //exc.printStackTrace();
            return v;
        }
    }

    public static float getFloatParameter(HttpServletRequest req, String key) throws Exception {
        if (req.getParameter(key) == null)
            throw new NullPointerException("Then value is NULL when get parameter " + key +
                                           "from request");
        return Float.parseFloat(req.getParameter(key).trim());

    }

    public static float getFloatParameter(HttpServletRequest req, String key, float v) {
        try {
            return Float.parseFloat(req.getParameter(key).trim());
        }
        catch (Exception ex) {
            //exc.printStackTrace();
            return v;
        }
    }

    public static double getDoubleParameter(HttpServletRequest req, String key) throws Exception {
        if (req.getParameter(key) == null)
            throw new NullPointerException("Then value is NULL when get parameter " + key +
                                           "from request");
        return Double.parseDouble(req.getParameter(key).trim());

    }

    public static double getDoubleParameter(HttpServletRequest req, String key, double v) {
        try {
            return Double.parseDouble(req.getParameter(key).trim());
        }
        catch (Exception ex) {
            //exc.printStackTrace();
            return v;
        }
    }

    public static boolean getBooleanParameter(HttpServletRequest req, String key) {
        if (req.getParameter(key) == null)
            throw new NullPointerException("Then value is NULL when get parameter " + key +
                                           "from request");
        return Boolean.valueOf(req.getParameter(key).trim()).booleanValue();
    }

    public static boolean getBooleanParameter(HttpServletRequest req, String key, boolean v) {
        String s = req.getParameter(key);
        if (s == null)
            return v;
        try {
            return Boolean.valueOf(s).booleanValue();
        }
        catch (Exception ex) {
            //exc.printStackTrace();
            return v;
        }
    }

    public static String getStringParameter(HttpServletRequest req, String key) {
        String s = req.getParameter(key);
        if (s == null)
            return null;
        else {
            String Encoding = req.getCharacterEncoding();
            if (Encoding != null && Encoding.trim().equals("ISO8859-1")) {
                return StringFilter.convertToAvoidGarbage(s.trim());
            } else {
                return s.trim();
            }
        }
    }

    public static String getStringParameter(HttpServletRequest req, String key, String v) {
        String s = req.getParameter(key);
        if (s == null)
            return v;
        else {
            String Encoding = req.getCharacterEncoding();

            if (Encoding != null && Encoding.trim().equals("ISO8859-1")) {
                return StringFilter.convertToAvoidGarbage(s.trim());
            } else {
                return s.trim();
            }
        }
    }



    public static Date getDateParameter(HttpServletRequest req, String key) throws Exception {
        if (req.getParameter(key) == null)
            throw new NullPointerException("Then value is NULL when get parameter " + key +
                                           "from request");
        return Date.valueOf(req.getParameter(key).trim());

    }

    public static java.util.Date getUtilDateParameter(HttpServletRequest req, String key,
        String pattern, Date v) {
        try {
            return DateComFunc.strToDate(req.getParameter(key).trim(), pattern);
        }
        catch (Exception ex) {
            //exc.printStackTrace();
            return v;
        }
    }

    public static java.util.Date getUtilDateParameter(HttpServletRequest req, String key,
        String pattern) throws Exception {
        if (req.getParameter(key) == null)
            throw new NullPointerException("Then value is NULL when get parameter " + key +
                                           "from request");
        return DateComFunc.strToDate(req.getParameter(key).trim(), pattern);

    }

    public static Date getDateParameter(HttpServletRequest req, String key, Date v) {
        try {
            return Date.valueOf(req.getParameter(key).trim());
        }
        catch (Exception ex) {
            //exc.printStackTrace();
            return v;
        }
    }

    public static Time getTimeParameter(HttpServletRequest req, String key) throws Exception {
        if (req.getParameter(key) == null)
            throw new NullPointerException("Then value is NULL when get parameter " + key +
                                           "from request");

        return Time.valueOf(req.getParameter(key).trim());

    }

    public static Time getTimeParameter(HttpServletRequest req, String key, Time v) {

        try {
            return Time.valueOf(req.getParameter(key).trim());
        }
        catch (Exception ex) {
            return v;
        }
    }

    public static Timestamp getTimestampParameter(HttpServletRequest req, String key) throws
        Exception {
        if (req.getParameter(key) == null)
            throw new NullPointerException("Then value is NULL when get parameter " + key +
                                           "from request");
        return Timestamp.valueOf(req.getParameter(key).trim());

    }

    public static Timestamp getTimestampParameter(HttpServletRequest req, String key, Timestamp v) {
        try {
            return Timestamp.valueOf(req.getParameter(key).trim());
        }
        catch (Exception ex) {
            //exc.printStackTrace();
            return v;
        }
    }
    /*
     public static String urlDecode(HttpServletRequest req,String key){
      String str1=req.getParameter(key);
      if(str1!=null){
         str1=URLDecoder.decode(str1);
      }
      return str1;
     }
     public static String urlDecode(HttpServletRequest req,String key,String defStr){
      String str1=req.getParameter(key);
      if(str1!=null){
         return URLDecoder.decode(str1);
      }else{
         return defStr;
      }
     }
     */
}