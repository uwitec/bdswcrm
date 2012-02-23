package com.sw.cms.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * �ļ�������
 * @author liyt
 *
 */
public class FileUtils {

    /**
     * ���ַ�Ϊ��λ��ȡ�ļ�
     * @param fileName  �ļ�·��
     * @return  �ļ����� String
     */
    public static String readFileByChars(String fileName) {
    	String str = "";
        File file = new File(fileName);
        Reader reader = null;
        try {
            System.out.println("���ַ�Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�����ֽڣ�");
            // һ�ζ�����ַ�
            char[] tempchars = new char[1024];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            // �������ַ����ַ������У�charreadΪһ�ζ�ȡ�ַ���
            while ((charread = reader.read(tempchars)) != -1) {
                // ͬ�����ε�\r����ʾ
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
     * ����Ϊ��λ��ȡ�ļ�<BR>
     * �ļ��в��ܴ��ڿ���<BR>
     * ��ȡ����á��������滻��
     * @param fileName   �ļ�·��
     * @return  �ļ����� String
     */
    public static String readFileByLines(String fileName) {
    	String str = "";
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = reader.readLine()) != null) {
                // ��ʾ�к�
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
