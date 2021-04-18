package xlo.util;

import xlo.util.reflect.ReflectFinder;

import java.lang.reflect.Field;

/**
 * @author XiaoLOrange
 * @time 2020.10.29
 * @title 获取类中指定的属性
 */

public class GetAttrUtil {

	/**
	 * 对于obj对象，向上寻找clazz，并获取clazz类中的field字段并取出值
	 * @param clazz
	 * @param obj
	 * @param field
	 * @return
	 */
	public static Object findClassAndGet(Class clazz, Object obj, String field){
		if(!ReflectFinder.FindClassUtil.hasFather(obj.getClass(), clazz)) return null;
		Field f = null;
		try {
			f = clazz.getDeclaredField(field);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return f == null ? null : get(obj, f);
	}

	/**
	 * 获取类中指定字段的属性值
	 * @param obj
	 * @param field
	 * @return
	 */
	public static Object get(Object obj, String field) {
		Field fild = null;
		try{
			fild = obj.getClass().getDeclaredField(field);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return fild == null ? null : get(obj, fild);
	}

	/**
	 * 获取类中指定字段的属性值
	 * @param obj
	 * @param field
	 * @return
	 */
	public static Object get(Object obj, Field field){
		if(obj == null || field == null) throw new NullPointerException();
		Object echo = null;
		try{
			field.setAccessible(true);
			echo = field.get(obj);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return echo;
	}

}
