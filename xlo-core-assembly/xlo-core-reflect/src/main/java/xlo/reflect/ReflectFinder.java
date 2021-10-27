package xlo.reflect;

import xlo.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title
 */

public class ReflectFinder {

	/**
	 * @author XiaoLOrange
	 * @time 2020.12.27
	 * @title
	 */
	public static class FindClassUtil {
		/**
		 * 一直向上查找，
		 * 寻找传入的类clazz是否存在指定的父类，
		 * @param clazz
		 * @return
		 */
		public static boolean hasFather(Class clazz, Class father){
			//获取clazz的父类
			clazz = clazz.getSuperclass();
			while (clazz != null){
				if(clazz == father) return true;
				clazz = clazz.getSuperclass();
			}
			return false;
		}
	}

	/**
	 * @author XiaoLOrange
	 * @time 2021.01.02
	 * @title
	 */
	public static class FindInterfaceUtil {
		/**
		 * 一直向上查找，
		 * 寻找传入的类clazz(该类本身的接口或者其父类是否有该接口)是否存在指定的接口
		 * @param clazz
		 * @param interfase
		 * @return
		 */
		public static boolean hasImpl(Class clazz, Class interfase){
			//传入的不是接口直接返回
			if(interfase.getModifiers() != 1537) return false;

			//获取clazz的父类
			while (clazz != null){
				//沿着传入的类，一直向上遍历其父类是否具有指定接口
				if(hasImpl(clazz.getInterfaces(), interfase)) return true;
				//获取父类
				clazz = clazz.getSuperclass();
			}
			return false;
		}

		/**
		 * 传入接口数组，遍历及向上查找是否存在指定的接口
		 * @param interfaces
		 * @param interfase
		 * @return
		 */
		public static boolean hasImpl(Class[] interfaces, Class interfase){
			//传入的不是接口直接返回
			if(interfase.getModifiers() != 1537) return false;
			for (Class iface: interfaces){
				if(iface == interfase) return true;
				if(hasImpl(iface.getInterfaces(), interfase)) return true;
			}
			return false;
		}
	}

	/**
	 * @author XiaoLOrange
	 * @time 2020.11.03
	 * @title
	 * FIXME - 该类的getGet和getSet在封装时忽略了字段第二个字母大写时，第一个字母不变的情况
	 */
	public static class FindMethodUtil {

		/**
		 * 在不知道参数的情况下
		 * 获取一个指定方法名的方法
		 * @param clazz
		 * @param name
		 * @return
		 */
		public static Method getMethod(Class clazz, String name){
			Method[] ms = clazz.getDeclaredMethods();

			for (int i = 0; i < ms.length; i++){
				if(ms[i].getName().equals(name)) return ms[i];
			}

			return null;
		}

		/**
		 * 获取具有指定参数的方法
		 * @param clazz
		 * @param name
		 * @param pts
		 * @return
		 */
		public static Method getMethod(Class clazz, String name, Class... pts){
			Method m = null;
			try {
				m = clazz.getDeclaredMethod(name, pts);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			return m;
		}

		/**
		 * 在不知道参数的情况下
		 * 获取指定方法名的所有方法
		 * @param clazz
		 * @param name
		 * @return
		 */
		public static Method[] getMethods(Class clazz, String name){
			Method[] ms = clazz.getDeclaredMethods();
			ArrayList<Method> preEcho = new ArrayList<>();

			for (int i = 0; i < ms.length; i++){
				if(ms[i].getName().equals(name)) preEcho.add(ms[i]);
			}
			Method[] echo = new Method[preEcho.size()];
			preEcho.toArray(echo);

			return echo;
		}

		/**
		 * 获取具有指定参数的set方法
		 * @param clazz
		 * @param field
		 * @param pts paramTypes
		 * @return
		 */
		public static Method getSetMethod(Class clazz, Field field, Class... pts){
			String name = "set" + StringUtil.toUpperFirstCase(field.getName());
			return getMethod(clazz, name, pts);
		}

		/**
		 * 获取clazz中field字段的set方法
		 * @param clazz
		 * @param field
		 * @return
		 */
		public static Method[] getSetMethods(Class clazz, Field field){
			String name = "set" + StringUtil.toUpperFirstCase(field.getName());
			Method[] ms = getMethods(clazz, name);
			return ms;
		}

		/**
		 * 获取具有指定参数的get方法
		 * @param clazz
		 * @param field
		 * @param pts
		 * @return
		 */
		public static Method getGetMethod(Class clazz, Field field, Class... pts){
			return getGetMethod(clazz, field.getName(), pts);
		}

		/**
		 * 获取具有指定参数的get方法
		 * @param clazz
		 * @param field
		 * @param pts
		 * @return
		 */
		public static Method getGetMethod(Class clazz, String field, Class... pts){
			String name = "get" + StringUtil.toUpperFirstCase(field);
			return getMethod(clazz, name, pts);
		}

		/**
		 * 获取clazz中field字段的get方法
		 * @param clazz
		 * @param field
		 * @return
		 */
		public static Method[] getGetMethods(Class clazz, Field field){
			String name = "get" + StringUtil.toUpperFirstCase(field.getName());
			Method[] ms = getMethods(clazz, name);
			return ms;
		}

	}
}
