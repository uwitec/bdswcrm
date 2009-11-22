package com.sw.cms.dao.base;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSetMetaData;

public class BeanRowMapper implements RowMapper {
	private Class rowObjectClass;
	
	public BeanRowMapper(Class rowObjectClass){
		this.rowObjectClass = rowObjectClass;
	}
	
    public Object mapRow(ResultSet rs, int index) throws SQLException {
        Object object;
		try {
			object = rowObjectClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		ResultSetWrappingSqlRowSetMetaData wapping = new ResultSetWrappingSqlRowSetMetaData(rs.getMetaData());
		for(int i=1;i<=wapping.getColumnCount();i++){
			String name = wapping.getColumnName(i);
			if("rownum".equalsIgnoreCase(name)||"rownum_".equalsIgnoreCase(name))
				continue;
			Object value = null;
			try {
				value = outPortData(rs,i,name,rowObjectClass);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			ReflectionUtils.setFieldVlaue(object, name, value);
		}

        return object;
    }
    
    
    public static Object outPortData(ResultSet rs, int index,String columnName, Class voClass) throws SQLException,
			InstantiationException, NoSuchMethodException,
			InvocationTargetException, IllegalAccessException {
		Object fieldValue = null;

		if (columnName != null) {
			if ("rownum".equalsIgnoreCase(columnName)) {
				fieldValue = new Long(rs.getLong(index));
			} else if ("rownum_".equalsIgnoreCase(columnName)) {
				fieldValue = new Long(rs.getLong(index));
			} else if ("count_".equalsIgnoreCase(columnName)) {
				fieldValue = new Long(rs.getLong(index));
			}else if ("R".equalsIgnoreCase(columnName)) {
				fieldValue = new Long(rs.getLong(index));
			} else {
				if (rs.getObject(index) != null) {
					Class propertyClass = PropertyUtil.getPropertyType(voClass
							.newInstance(), columnName);
					if (propertyClass == null) {
						throw new IllegalAccessException("Property not found "
								+ columnName);
					}
					String classType = propertyClass.getName();

					if (classType.equals("java.lang.Integer")
							|| classType.equals("int")) {
						fieldValue = new Integer(rs.getInt(index));
					} else if (classType.equals("java.lang.Long")
							|| classType.equals("long")) {
						fieldValue = new Long(rs.getLong(index));
					} else if (classType.equals("java.math.BigDecimal")) {
						fieldValue = rs.getBigDecimal(index);
					} else if (classType.equals("java.lang.String")) {

						fieldValue = rs.getString(index);
					} else if (classType.equals("java.util.Date")) {
						java.sql.Date date = rs.getDate(index);
						fieldValue = new java.util.Date(date.getTime());

					}else if (classType.equals("java.sql.Date")) {
						fieldValue = rs.getDate(index);
					} else if (classType.equals("java.sql.Time")) {
						java.sql.Time date = rs.getTime(index);
						fieldValue = new java.sql.Time(date.getTime());
					} else if (classType.equals("java.sql.Timestamp")) {
						fieldValue = rs.getTimestamp(index);

					} else if (classType.equals("java.lang.Double")
							|| classType.equals("double")) {
						fieldValue = new Double(rs.getDouble(index));
					} else if (classType.equals("java.lang.Float")
							|| classType.equals("float")) {
						fieldValue = new Float(rs.getFloat(index));
					} else if (classType.equals("java.lang.Byte")
							|| classType.equals("byte")) {
						fieldValue = new Byte(rs.getByte(index));
					} else if (classType.equals("java.lang.Boolean")
							|| classType.equals("boolean")) {
						fieldValue = new Boolean(rs.getBoolean(index));
					} else if (classType.equals("java.lang.Char")
							|| classType.equals("char")) {
						fieldValue = new java.lang.Character(rs
								.getString(index).charAt(0));
					}
				}
			}
		}
		return fieldValue;

	}
}
