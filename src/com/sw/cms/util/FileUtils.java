package com.sw.cms.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 文件操作类
 * @author liyt
 *
 */
public class FileUtils {

    /**
     * 以字符为单位读取文件
     * @param fileName  文件路径
     * @return  文件内容 String
     */
    public static String readFileByChars(String fileName) {
    	String str = "";
        File file = new File(fileName);
        Reader reader = null;
        try {
            System.out.println("以字符为单位读取文件内容，一次读多个字节：");
            // 一次读多个字符
            char[] tempchars = new char[1024];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length) && (tempchars[tempchars.length - 1] != '\r')) {
                    str += tempchars.toString();
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            str += tempchars[i];
                        }
                    }
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return str;
    }

    /**
     * 以行为单位读取文件<BR>
     * 文件中不能存在空行<BR>
     * 读取结果用“，”代替换行
     * @param fileName   文件路径
     * @return  文件内容 String
     */
    public static String readFileByLines(String fileName) {
    	String str = "";
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
            	if(str.equals("")){
            		str = tempString;
            	}else{
            		str += "," + tempString;
            	}
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return str;
    }

    public static void main(String[] args) {
    	System.out.println(FileUtils.readFileByChars("D:/a.txt"));
    	System.out.println(FileUtils.readFileByLines("D:/a.txt"));
    }

}
