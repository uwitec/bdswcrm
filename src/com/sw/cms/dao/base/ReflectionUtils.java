package com.sw.cms.dao.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import java.util.List;




public class ReflectionUtils {
	private static String[] requireClass = new String[]{
		"int","float","double","long","java.lang.String",
		"java.lang.Integer","java.lang.Long","java.lang.Float",
		"java.lang.Double","java.util.Date","java.sql.Date","java.sql.Timestamp"
	};
	
	/*
	 * 注意field的访问问题
	 */
	public static void setFieldVlaue(Object obj, String fieldName, Object value) {
		try {
			PropertyUtil.setProperty(obj, fieldName, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public static Object getFieldVlaue(Object obj, String fieldName,
			Object value) {
		Class c = obj.getClass();
		try {
			Field field;
			try {
				field = c.getDeclaredField(fieldName);
				field.setAccessible(true);
			} catch (NoSuchFieldException e) {
				return null;
			}
			return field.get(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
private static boolean isRequire(String clazzName){
	for (int i = 0; i < requireClass.length; i++) {
		if(clazzName.equalsIgnoreCase(requireClass[i]))
			return true;
	}
	return false;
}
	public static List findNotNullField(Object obj) {
		Class c = obj.getClass();
		List list = new ArrayList(10);
		try {
			Field[] fields = c.getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				fields[i].setAccessible(true);
				if(!isRequire(fields[i].getType().getName())){
					continue;
				}
//				System.out.println(fields[i].getType().getName());
				if(fields[i].get(obj)!=null){
					
					list.add(fields[i]);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return list;
	}
	
}
