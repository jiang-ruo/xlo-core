package xlo.util;

/**
 * @author XiaoLOrange
 * @time 2020.12.02
 * @title
 */

public class TypeUtil {

	/**
	 * 判断传入的数字类型是否是数字的基本数字类型
	 * @param clazz
	 * @return
	 */
	public static boolean isBaseNum(Class clazz){
		return NumUtil.isBaseNum(clazz);
	}

	/**
	 * 判断传入的类型是否是数字类型的包装类
	 * @param clazz
	 * @return
	 */
	public static boolean isPackingNum(Class clazz){
		return NumUtil.isPackingNum(clazz);
	}

	/**
	 * 判断传入的类型是否是数字
	 * @param clazz
	 * @return
	 */
	public static boolean isNum(Class clazz){
		return NumUtil.isNum(clazz);
	}

	/**
	 * 判断是否是基本数据类型的包装类
	 * @param clazz
	 * @return
	 */
	public static boolean isBasePacking(Class clazz){
		return (isPackingNum(clazz)
		|| clazz == Character.class
		|| clazz == Boolean.class);
	}

	/**
	 * 判断是否是包装类
	 * @param clazz
	 * @return
	 */
	public static boolean isPacking(Class clazz){
		return (isBasePacking(clazz) || clazz == String.class);
	}

}
