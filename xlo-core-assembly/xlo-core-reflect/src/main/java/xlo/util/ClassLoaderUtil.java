package xlo.util;

import java.util.Vector;

/**
 * @author XiaoLOrange
 * @time 2021.05.24
 * @title
 */

public class ClassLoaderUtil {

	/**
	 * 获取java默认类加载器
	 * @return
	 */
	public static ClassLoader getSystemClassLoader(){
		return ClassLoader.getSystemClassLoader();
	}

	/**
	 * 获取当前线程的类加载器
	 * @return
	 */
	public static ClassLoader getCurrentClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * 获取指定类加载器加载的类，如果为null，则为获取当前线程的类加载器
	 * @param cl
	 * @return
	 */
	public static Vector<Class> getContext(ClassLoader cl){
		//类加载器中有个字段为classes为其加载的类
		return (Vector) GetAttrUtil.findClassAndGet(ClassLoader.class, cl, "classes");
	}

}
