package com.sw.cms.dao.base;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.BeanInfo;
import java.util.Hashtable;
import java.beans.Introspector;

/**
 * <p>Title: SpeedFrameworkWork持久层框架</p>
 *
 * <p>Description: 值对象拷贝类</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: SpeedFrameworkWork team</p>
 * @version 1.0
 */
public class PropertyUtil {
  private static Hashtable descriptorsCache = new Hashtable();

  /**
   * Copy property values from the "origin" bean to the "destination" bean
   * for all cases where the property names are the same (even though the
   * actual getter and setter methods might have been customized via
   * <code>BeanInfo</code> classes).  No conversions are performed on the
   * actual property values -- it is assumed that the values retrieved from
   * the origin bean are assignment-compatible with the types expected by
   * the destination bean.
   *
   * @param dest Destination bean whose properties are modified
   * @param orig Origin bean whose properties are retrieved
   *
   * @exception IllegalAccessException if the caller does not have
   *  access to the property accessor method
   * @exception InvocationTargetException if the property accessor method
   *  throws an exception
   * @exception NoSuchMethodException if an accessor method for this
   *  propety cannot be found
   */
  public static void copyProperties(Object dest, Object orig) throws
      IllegalAccessException, InvocationTargetException,
      NoSuchMethodException {

    PropertyDescriptor origDescriptors[] = getPropertyDescriptors(orig);
    for (int i = 0; i < origDescriptors.length; i++) {
      String name = origDescriptors[i].getName();
      if (name.toLowerCase().equals("class") ||
          name.toLowerCase().equals("serialversionuid")) {
        continue;
      }
      if (getPropertyDescriptor(dest, name) != null) {
        Object value = getSimpleProperty(orig, name);
        setSimpleProperty(dest, name, value);
      }
    }

  }

  /**
   * Return the value of the specified indexed property of the specified
   * bean, with no type conversions.  The zero-relative index of the
   * required value must be included (in square brackets) as a suffix to
   * the property name, or <code>IllegalArgumentException</code> will be
   * thrown.
   *
   * @param bean Bean whose property is to be extracted
   * @param name <code>propertyname[index]</code> of the property value
   *  to be extracted
   *
   * @exception IllegalAccessException if the caller does not have
   *  access to the property accessor method
   * @exception InvocationTargetException if the property accessor method
   *  throws an exception
   * @exception NoSuchMethodException if an accessor method for this
   *  propety cannot be found
   */
  public static Object getIndexedProperty(Object bean, String name) throws
      IllegalAccessException, InvocationTargetException,
      NoSuchMethodException {

    // Identify the index of the requested individual property
    int left = name.indexOf("[");
    int right = name.indexOf("]");
    if ( (left < 0) || (right <= left)) {
      throw new IllegalArgumentException("Invalid indexed property '" +
                                         name + "'");
    }
    if (right > (name.length() - 1)) {
      throw new IllegalArgumentException("Invalid indexed property '" +
                                         name + "'");
    }
    int index = -1;
    try {
      String subscript = name.substring(left + 1, right);
      index = Integer.parseInt(subscript);
    }
    catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid indexed property '" +
                                         name + "'");
    }
    name = name.substring(0, left);

    // Request the specified indexed property value
    return (getIndexedProperty(bean, name, index));

  }

  /**
   * Return the value of the specified indexed property of the specified
   * bean, with no type conversions.
   *
   * @param bean Bean whose property is to be extracted
   * @param name Simple property name of the property value to be extracted
   * @param index Index of the property value to be extracted
   *
   * @exception IllegalAccessException if the caller does not have
   *  access to the property accessor method
   * @exception InvocationTargetException if the property accessor method
   *  throws an exception
   * @exception NoSuchMethodException if an accessor method for this
   *  propety cannot be found
   */
  public static Object getIndexedProperty(Object bean,
                                          String name, int index) throws
      IllegalAccessException, InvocationTargetException,
      NoSuchMethodException {

    // Retrieve the property descriptor for the specified property
	
    PropertyDescriptor descriptor =
        getPropertyDescriptor(bean, name);
    if (descriptor == null) {
      throw new NoSuchMethodException("Unknown property '" +
                                      name + "'");
    }

    // Call the indexed getter method if there is one
    if (descriptor instanceof IndexedPropertyDescriptor) {
      Method readMethod = ( (IndexedPropertyDescriptor) descriptor).
          getIndexedReadMethod();
      if (readMethod != null) {
        Object subscript[] = new Object[1];
        subscript[0] = new Integer(index);
        return (readMethod.invoke(bean, subscript));
      }
    }

    // Otherwise, the underlying property must be an array
    Method readMethod = descriptor.getReadMethod();
    if (readMethod == null) {
      throw new NoSuchMethodException("Property '" + name +
                                      "' has no getter method");
    }

    // Call the property getter and return the value
    Object value = readMethod.invoke(bean, new Object[0]);
    if (!value.getClass().isArray()) {
      throw new IllegalArgumentException("Property '" + name +
                                         "' is not indexed");
    }
    
    return (Array.get(value, index));

  }

  /**
   * Return the value of the (possibly nested) property of the specified
   * name, for the specified bean, with no type conversions.
   *
   * @param bean Bean whose property is to be extracted
   * @param name Possibly nested name of the property to be extracted
   *
   * @exception IllegalAccessException if the caller does not have
   *  access to the property accessor method
   * @exception IllegalArgumentException if a nested reference to a
   *  property returns null
   * @exception InvocationTargetException if the property accessor method
   *  throws an exception
   * @exception NoSuchMethodException if an accessor method for this
   *  propety cannot be found
   */
  public static Object getNestedProperty(Object bean, String name) throws
      IllegalAccessException, InvocationTargetException,
      NoSuchMethodException {

    while (true) {
      int period = name.indexOf(".");
      if (period < 0) {
        break;
      }
      String next = name.substring(0, period);
      if (next.indexOf("[") >= 0) {
        bean = getIndexedProperty(bean, next);
      }
      else {
        bean = getSimpleProperty(bean, next);
      }
      if (bean == null) {
        throw new IllegalArgumentException
            ("Null property value for '" +
             name.substring(0, period) + "'");
      }
      name = name.substring(period + 1);
    }

    if (name.indexOf("[") >= 0) {
      return (getIndexedProperty(bean, name));
    }
    else {
      return (getSimpleProperty(bean, name));
    }

  }

  /**
   * Return the value of the specified property of the specified bean,
   * no matter which property reference format is used, with no
   * type conversions.
   *
   * @param bean Bean whose property is to be extracted
   * @param name Possibly indexed and/or nested name of the property
   *  to be extracted
   *
   * @exception IllegalAccessException if the caller does not have
   *  access to the property accessor method
   * @exception InvocationTargetException if the property accessor method
   *  throws an exception
   * @exception NoSuchMethodException if an accessor method for this
   *  propety cannot be found
   */
  public static Object getProperty(Object bean, String name) throws
      IllegalAccessException, InvocationTargetException,
      NoSuchMethodException {

    return (getNestedProperty(bean, name));

  }

  /**
   * Retrieve the property descriptor for the specified property of the
   * specified bean, or return <code>null</code> if there is no such
   * descriptor.  This method resolves indexed and nested property
   * references in the same manner as other methods in this class, except
   * that if the last (or only) name element is indexed, the descriptor
   * for the property itself is returned.
   *
   * @param bean Bean for which a property descriptor is requested
   * @param name Possibly indexed and/or nested name of the property for
   *  which a property descriptor is requested
   *
   * @exception IllegalAccessException if the caller does not have
   *  access to the property accessor method
   * @exception IllegalArgumentException if a nested reference to a
   *  property returns null
   * @exception InvocationTargetException if the property accessor method
   *  throws an exception
   * @exception NoSuchMethodException if an accessor method for this
   *  propety cannot be found
   */
  public static PropertyDescriptor getPropertyDescriptor(Object bean,
      String name) throws IllegalAccessException, InvocationTargetException,
      NoSuchMethodException {

    // Resolve nested references
    while (true) {
      int period = name.indexOf(".");
      if (period < 0) {
        break;
      }
      String next = name.substring(0, period);
      if (next.indexOf("[") >= 0) {
        bean = getIndexedProperty(bean, next);
      }
      else {
        bean = getSimpleProperty(bean, next);
      }
      if (bean == null) {
        throw new IllegalArgumentException
            ("Null property value for '" +
             name.substring(0, period) + "'");
      }
      name = name.substring(period + 1);
    }

    // Remove any subscript from the final name value
    int left = name.indexOf("[");
    if (left >= 0) {
      name = name.substring(0, left);

      // Look up and return this property from our cache
    }
    if ( (bean == null) || (name == null)) {
      return (null);
    }
    PropertyDescriptor descriptors[] = getPropertyDescriptors(bean);
    if (descriptors == null) {
      return (null);
    }
    for (int i = 0; i < descriptors.length; i++) {
      if (name.equalsIgnoreCase(descriptors[i].getName())) {
        return (descriptors[i]);
      }
    }
    return (null);

  }

  /**
   * Retrieve the property descriptors for the specified bean, introspecting
   * and caching them the first time a particular bean class is encountered.
   *
   * @param bean Bean for which property descriptors are requested
   */
  public static PropertyDescriptor[] getPropertyDescriptors(Object bean) {

    if (bean == null) {
      return (new PropertyDescriptor[0]);
    }

    // Look up any cached descriptors for this bean class
    String beanClassName = bean.getClass().getName();
    PropertyDescriptor descriptors[] =
        (PropertyDescriptor[]) descriptorsCache.get(beanClassName);
    if (descriptors != null) {
      return (descriptors);
    }

    // Introspect the bean and cache the generated descriptors
    BeanInfo beanInfo = null;
    try {
      beanInfo = Introspector.getBeanInfo(bean.getClass());
    }
    catch (IntrospectionException e) {
      return (new PropertyDescriptor[0]);
    }
    descriptors = beanInfo.getPropertyDescriptors();
    if (descriptors == null) {
      descriptors = new PropertyDescriptor[0];
    }
    descriptorsCache.put(beanClassName, descriptors);
    return (descriptors);

  }

  /**
   * Return the Java Class repesenting the property editor class that has
   * been registered for this property (if any).  This method follows the
   * same name resolution rules used by <code>getPropertyDescriptor()</code>,
   * so if the last element of a name reference is indexed, the property
   * editor for the underlying property's class is returned.
   * <p>
   * Note that <code>null</code> will be returned if there is no property,
   * or if there is no registered property editor class.  Because this
   * return value is ambiguous, you should determine the existence of the
   * property itself by other means.
   *
   * @param bean Bean for which a property descriptor is requested
   * @param name Possibly indexed and/or nested name of the property for
   *  which a property descriptor is requested
   *
   * @exception IllegalAccessException if the caller does not have
   *  access to the property accessor method
   * @exception IllegalArgumentException if a nested reference to a
   *  property returns null
   * @exception InvocationTargetException if the property accessor method
   *  throws an exception
   * @exception NoSuchMethodException if an accessor method for this
   *  propety cannot be found
   */
  public static Class getPropertyEditorClass(Object bean, String name) throws
      IllegalAccessException, InvocationTargetException,
      NoSuchMethodException {

    PropertyDescriptor descriptor =
        getPropertyDescriptor(bean, name);
    if (descriptor != null) {
      return (descriptor.getPropertyEditorClass());
    }
    else {
      return (null);
    }

  }

  /**
   * Return the Java Class repesenting the property type of the specified
   * property, or <code>null</code> if there is no such property for the
   * specified bean.  This method follows the same name resolution rules
   * used by <code>getPropertyDescriptor()</code>, so if the last element
   * of a name reference is indexed, the type of the property itself will
   * be returned.  If the last (or only) element has no property with the
   * specified name, <code>null</code> is returned.
   *
   * @param bean Bean for which a property descriptor is requested
   * @param name Possibly indexed and/or nested name of the property for
   *  which a property descriptor is requested
   *
   * @exception IllegalAccessExceptionif the caller does not have
   *  access to the property accessor method
   * @exception IllegalArgumentException if a nested reference to a
   *  property returns null
   * @exception InvocationTargetException if the property accessor method
   *  throws an exception
   * @exception NoSuchMethodException if an accessor method for this
   *  propety cannot be found
   */
  public static Class getPropertyType(Object bean, String name) throws
      IllegalAccessException, InvocationTargetException,
      NoSuchMethodException {

    PropertyDescriptor descriptor =
        getPropertyDescriptor(bean, name);
    if (descriptor != null) {
      return (descriptor.getPropertyType());
    }
    else {
      return (null);
    }

  }

  /**
   * Return the value of the specified simple property of the specified
   * bean, with no type conversions.
   *
   * @param bean Bean whose property is to be extracted
   * @param name Name of the property to be extracted
   *
   * @exception IllegalAccessException if the caller does not have
   *  access to the property accessor method
   * @exception InvocationTargetException if the property accessor method
   *  throws an exception
   * @exception NoSuchMethodException if an accessor method for this
   *  propety cannot be found
   */
  public static Object getSimpleProperty(Object bean, String name) throws
      IllegalAccessException, InvocationTargetException,
      NoSuchMethodException {

    // Retrieve the property getter method for the specified property
    PropertyDescriptor descriptor =
        getPropertyDescriptor(bean, name);
    if (descriptor == null) {
      throw new NoSuchMethodException("Unknown property '" +
                                      name + "'");
    }
    Method readMethod = descriptor.getReadMethod();
    if (readMethod == null) {
      throw new NoSuchMethodException("Property '" + name +
                                      "' has no getter method");
    }

    // Call the property getter and return the value
    Object value = readMethod.invoke(bean, new Object[0]);
    return (value);

  }

  /**
   * Set the value of the (possibly nested) property of the specified
   * name, for the specified bean, with no type conversions.
   *
   * @param bean Bean whose property is to be modified
   * @param name Possibly nested name of the property to be modified
   * @param value Value to which the property is to be set
   *
   * @exception IllegalAccessException if the caller does not have
   *  access to the property accessor method
   * @exception IllegalArgumentException if a nested reference to a
   *  property returns null
   * @exception InvocationTargetException if the property accessor method
   *  throws an exception
   * @exception NoSuchMethodException if an accessor method for this
   *  propety cannot be found
   */
  public static void setNestedProperty(Object bean,
                                       String name, Object value) throws
      IllegalAccessException, InvocationTargetException,
      NoSuchMethodException {

    while (true) {
      int period = name.indexOf(".");
      if (period < 0) {
        break;
      }
      String next = name.substring(0, period);
      if (next.indexOf("[") >= 0) {
        bean = getIndexedProperty(bean, next);
      }
      else {
        bean = getSimpleProperty(bean, next);
      }
      if (bean == null) {
        throw new IllegalArgumentException
            ("Null property value for '" +
             name.substring(0, period) + "'");
      }
      name = name.substring(period + 1);
    }

    if (name.indexOf("[") >= 0) {
      setIndexedProperty(bean, name, value);
    }
    else {
      setSimpleProperty(bean, name, value);

    }
  }

  /**
   * Set the value of the specified indexed property of the specified
   * bean, with no type conversions.  The zero-relative index of the
   * required value must be included (in square brackets) as a suffix to
   * the property name, or <code>IllegalArgumentException</code> will be
   * thrown.
   *
   * @param bean Bean whose property is to be modified
   * @param name <code>propertyname[index]</code> of the property value
   *  to be modified
   * @param value Value to which the specified property element
   *  should be set
   *
   * @exception IllegalAccessException if the caller does not have
   *  access to the property accessor method
   * @exception InvocationTargetException if the property accessor method
   *  throws an exception
   * @exception NoSuchMethodException if an accessor method for this
   *  propety cannot be found
   */
  public static void setIndexedProperty(Object bean, String name,
                                        Object value) throws
      IllegalAccessException, InvocationTargetException,
      NoSuchMethodException {

    // Identify the index of the requested individual property
    int left = name.indexOf("[");
    int right = name.indexOf("]");
    if ( (left < 0) || (right <= left)) {
      throw new IllegalArgumentException("Invalid indexed property '" +
                                         name + "'");
    }
    if (right > (name.length() - 1)) {
      throw new IllegalArgumentException("Invalid indexed property '" +
                                         name + "'");
    }
    int index = -1;
    try {
      String subscript = name.substring(left + 1, right);
      index = Integer.parseInt(subscript);
    }
    catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid indexed property '" +
                                         name + "'");
    }
    name = name.substring(0, left);

    // Request the specified indexed property value
    setIndexedProperty(bean, name, index, value);

  }

  /**
   * Set the value of the specified indexed property of the specified
   * bean, with no type conversions.
   *
   * @param bean Bean whose property is to be set
   * @param name Simple property name of the property value to be set
   * @param index Index of the property value to be set
   * @param value Value to which the indexed property element is to be set
   *
   * @exception IllegalAccessException if the caller does not have
   *  access to the property accessor method
   * @exception InvocationTargetException if the property accessor method
   *  throws an exception
   * @exception NoSuchMethodException if an accessor method for this
   *  propety cannot be found
   */
  public static void setIndexedProperty(Object bean, String name,
                                        int index, Object value) throws
      IllegalAccessException, InvocationTargetException,
      NoSuchMethodException {

    // Retrieve the property descriptor for the specified property
    PropertyDescriptor descriptor =
        getPropertyDescriptor(bean, name);
    if (descriptor == null) {
      throw new NoSuchMethodException("Unknown property '" +
                                      name + "'");
    }

    // Call the indexed setter method if there is one
    if (descriptor instanceof IndexedPropertyDescriptor) {
      Method writeMethod = ( (IndexedPropertyDescriptor) descriptor).
          getIndexedWriteMethod();
      if (writeMethod != null) {
        Object subscript[] = new Object[2];
        subscript[0] = new Integer(index);
        subscript[1] = value;
        writeMethod.invoke(bean, subscript);
      }
    }

    // Otherwise, the underlying property must be an array
    Method readMethod = descriptor.getReadMethod();
    if (readMethod == null) {
      throw new NoSuchMethodException("Property '" + name +
                                      "' has no getter method");
    }

    // Call the property getter to get the array
    Object array = readMethod.invoke(bean, new Object[0]);
    if (!array.getClass().isArray()) {
      throw new IllegalArgumentException("Property '" + name +
                                         "' is not indexed");
    }

    // Modify the specified value
    Array.set(array, index, value);

  }

  /**
   * Set the value of the specified property of the specified bean,
   * no matter which property reference format is used, with no
   * type conversions.
   *
   * @param bean Bean whose property is to be modified
   * @param name Possibly indexed and/or nested name of the property
   *  to be modified
   * @param value Value to which this property is to be set
   *
   * @exception IllegalAccessException if the caller does not have
   *  access to the property accessor method
   * @exception InvocationTargetException if the property accessor method
   *  throws an exception
   * @exception NoSuchMethodException if an accessor method for this
   *  propety cannot be found
   */
  public static void setProperty(Object bean, String name, Object value) throws
      IllegalAccessException, InvocationTargetException,
      NoSuchMethodException {

    setNestedProperty(bean, name, value);

  }

  /**
   * Set the value of the specified simple property of the specified bean,
   * with no type conversions.
   *
   * @param bean Bean whose property is to be modified
   * @param name Name of the property to be modified
   * @param value Value to which the property should be set
   *
   * @exception IllegalAccessException if the caller does not have
   *  access to the property accessor method
   * @exception InvocationTargetException if the property accessor method
   *  throws an exception
   * @exception NoSuchMethodException if an accessor method for this
   *  propety cannot be found
   */
  public static void setSimpleProperty(Object bean,
                                       String name, Object value) throws
      IllegalAccessException, InvocationTargetException,
      NoSuchMethodException {

    // Retrieve the property setter method for the specified property
    PropertyDescriptor descriptor =
        getPropertyDescriptor(bean, name);
    if (descriptor == null) {
      throw new NoSuchMethodException("Unknown property '" +
                                      name + "'");
    }
    
//    System.out.println(descriptor.getDisplayName()+"--"+descriptor.getPropertyType());
    
    
    Method writeMethod = descriptor.getWriteMethod();
    if (writeMethod == null) {
      throw new NoSuchMethodException("Property '" + name +
                                      "' has no setter method");
    }

    // Call the property setter method
    Object values[] = new Object[1];
    values[0] = value;
    writeMethod.invoke(bean, values);

  }

}
