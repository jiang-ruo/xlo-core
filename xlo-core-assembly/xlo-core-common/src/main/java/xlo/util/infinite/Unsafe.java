package xlo.util.infinite;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author XiaoLOrange
 * @title 释放内存，不大清楚怎么用，(传入的long参数好像是指针？)
 * @time 2020年11月2日
 */
public class Unsafe {

	/**
	 * Unsafe对象
	 */
	private static Object unsafe;

	/**
	 * 新建对象方法
	 */
	public static Method allocateInstance;
	/**
	 * 释放内存，不大清楚怎么用，(传入的long参数好像是指针？)
	 */
	public static Method freeMemory;

	static {
		try {
			Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
			Field f = unsafeClass.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			unsafe = f.get((Object)null);
			//
			allocateInstance = unsafeClass.getMethod("allocateInstance", Class.class);
			//
			freeMemory = unsafeClass.getMethod("freeMemory", long.class);
		} catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 同allocateInstance
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> T newInstance(Class<T> clazz) throws Exception {
		return allocateInstance(clazz);
	}

	/**
	 * 在内存中声明/申请？一块区域放clazz的实例化对象
	 * 不可被java GC回收
	 * @param <T>
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> T allocateInstance(Class<T> clazz) throws Exception {
		return (T) allocateInstance.invoke(unsafe, clazz);
	}

	/**
	 *
	 * @param address
	 * @throws Exception
	 */
	public static void freeMemory(long address) throws Exception {
		freeMemory.invoke(unsafe, address);
	}
	
}
