package xlo.util.reflect;

import java.lang.reflect.Field;

/**
 * @author XiaoLOrange
 * @time 2021.01.17
 * @title
 */

public class FieldUtil {

	/**
	 * 获取clazz的所有属性并设为可赋值
	 * @param clazz
	 * @return
	 */
	public static Field[] getAndSetAccessible(Class clazz){
		Field[] fs = clazz.getDeclaredFields();
		setAccessible(fs);
		return fs;
	}

	/**
	 * 将类的属性设为可赋值
	 * @param fs
	 */
	public static void setAccessible(Field... fs){
		for (Field f: fs){
			f.setAccessible(true);
		}
	}

}
