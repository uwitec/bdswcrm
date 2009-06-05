package com.sw.cms.dao.base;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlUtil {
	
	/**
	 * 判断结果集中是否存在列名
	 * @param rs 结果集
	 * @param columnName 列名
	 * @return 存在返回 true;不存在返回 false;
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
