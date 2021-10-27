package xlo.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XiaoLOrange
 * @time 2020.11.28
 * @title 数字工具类
 * formatNumber方法可以优化为使用DecimalFormat进行格式化
 */

public class NumUtil {

	/**
	 * 因为基本数据类型没有构造，需要将其转换为对应的包装类
	 */
	private final static Map<Class, Class> NumTypeMap = new HashMap<>();

	static{
		NumTypeMap.put(byte.class, Byte.class);
		NumTypeMap.put(short.class, Short.class);
		NumTypeMap.put(int.class, Integer.class);
		NumTypeMap.put(long.class, Long.class);
		NumTypeMap.put(float.class, Float.class);
		NumTypeMap.put(double.class, Double.class);
	}

	/**
	 * 判断数字是否是整型
	 * @return
	 */
	public static boolean isInteger(Class clazz){
		return (clazz == byte.class || clazz == Byte.class
				|| clazz == short.class || clazz == Short.class
				|| clazz == int.class || clazz == Integer.class
				|| clazz == long.class || clazz == Long.class);
	}

	/**
	 * 判断数字是否是浮点数
	 * @return
	 */
	public static boolean isFloat(Class clazz){
		return (clazz == float.class || clazz == Float.class
				|| clazz == double.class || clazz == Double.class);
	}

	/**
	 * 判断传入的数字类型是否是数字的基本数字类型
	 * @param clazz
	 * @return
	 */
	public static boolean isBaseNum(Class clazz){
		return (clazz == byte.class
				|| clazz == short.class
				|| clazz == int.class
				|| clazz == long.class
				|| clazz == float.class
				|| clazz == double.class);
	}

	/**
	 * 判断传入的类型是否是数字类型的包装类
	 * @param clazz
	 * @return
	 */
	public static boolean isPackingNum(Class clazz){
		return (clazz == Byte.class
				|| clazz == Short.class
				|| clazz == Integer.class
				|| clazz == Long .class
				|| clazz == Float.class
				|| clazz == Double.class);
	}

	/**
	 * 判断传入的类型是否是数字
	 * @param clazz
	 * @return
	 */
	public static boolean isNum(Class clazz){
		return (isPackingNum(clazz)
				|| isBaseNum(clazz));
	}

	/**
	 * 判断传入的对象是否是数字
	 * @param obj
	 * @return
	 */
	public static boolean isNumber(Object obj){
		return obj == null ? false : isNum(obj.getClass());
	}

	/**
	 * 将对象转换为数字，
	 * @param value
	 * @return
	 */
	public static Byte toByte(Object value){
		Long rs = toLong(value);
		return rs == null ? null : rs.byteValue();
	}

	/**
	 * 将对象转换为数字，
	 * @param value
	 * @return
	 */
	public static Short toShort(Object value){
		Long rs = toLong(value);
		return rs == null ? null : rs.shortValue();
	}

	/**
	 * 将对象转换为数字，
	 * @param value
	 * @return
	 */
	public static Integer toInt(Object value){
		Long rs = toLong(value);
		return rs == null ? null : rs.intValue();
	}

	/**
	 * 将对象转换为数字，
	 * 直接转换为长整型，这样子对于长度比long小的数据类型，强转的时候直接截取低位
	 * @param value
	 * @return
	 */
	public static Long toLong(Object value){
		if(value == null) return null;

		Long num = null;
		try {
			num = Long.parseLong(value.toString());
		}catch (NumberFormatException e){
//			e.printStackTrace();
			Double d = toDouble(value);
			if(d != null) num = d.longValue();
		}
		return num;
	}

	public static Float toFloat(Object value){
		Double rs = toDouble(value);
		return rs == null ? null : rs.floatValue();
	}

	/**
	 * 将对象转换为数字
	 * @param value
	 * @return
	 */
	public static Double toDouble(Object value){
		if(value == null) return null;
		Double num = null;
		try {
			num = Double.parseDouble(value.toString());
		}catch (NumberFormatException e){
//			e.printStackTrace();
		}
		return num;
	}

	/**
	 * 将传入的值转换为数字
	 * @param value
	 * @return
	 */
	public static int toNumber(Object value){
		Long rs = toLong(value);
		return rs == null ? 0 : rs.intValue();
	}

	/**
	 * 将传入的值转换为数字
	 * @param value
	 * @return
	 */
	public static float toDecimal(Object value){
		Double rs = toDouble(value);
		return rs == null ? 0 : rs.floatValue();
	}

	/**
	 * 返回指定类型的数字
	 * @param clazz
	 * @param str
	 * @param <T>
	 * @return
	 */
	public static <T extends Number> T toNumber(Class<T> clazz, String str){
		if(NumUtil.isBaseNum(clazz)){
			Number num = toNumber(NumTypeMap.get(clazz), str);
			if(num == null) num = 0;
			return (T) num;
		}

		T t = null;
		try {
			if(isInteger(clazz)) {
				Long l = toLong(str);
				if(l != null) str = l.toString();
			}
			t = clazz.getDeclaredConstructor(String.class).newInstance(str);
		} catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | NumberFormatException e) {
//			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 数字前补充指定字符到指定长度
	 * @param num 数字
	 * @param length 长度
	 * @param charachter 字符
	 * @return
	 */
	public String formatNumber(int num, int length, char charachter){
		StringBuilder format = new StringBuilder(num + "");

		int numLength = format.length();
		if(numLength < length){
			int gap = length - numLength;
			for (int i = 0; i < gap; i++){
				format.insert(0, charachter);
			}
		}

		return format.toString();
	}
}
