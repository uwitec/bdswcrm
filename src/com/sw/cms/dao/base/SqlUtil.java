package com.sw.cms.dao.base;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlUtil {
	
	/**
	 * �жϽ�������Ƿ��������
	 * @param rs �����
	 * @param columnName ����
	 * @return ���ڷ��� true;�����ڷ��� false;
	 */
	public static boolean columnIsExist(ResultSet rs,String columnName){		
		boolean is = false;
		try{
			int i = rs.findColumn(columnName);
			if(i>0){
				is = true;
			}
		}catch(SQLException e){
			
		}
		return is;
	}

}
