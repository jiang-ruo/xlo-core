package xlo.util.adapter;

/**
 * @author XiaoLOrange
 * @time 2021.04.28
 * @title
 */

public class CGLibAdapter {

	/**
	 * 获取CGLib代理对象的类类型
	 * @param instance
	 * @return
	 */
	public static Class CGlibPrototypeAdapter(Object instance){
		return CGlibPrototypeAdapter(instance.getClass());
	}

	/**
	 * 获取CGLib代理对象的类类型
	 * @param clazz
	 * @return
	 */
	public static Class CGlibPrototypeAdapter(Class clazz){
		if (clazz.getName().contains("$$")) {
			Class<?> superclass = clazz.getSuperclass();
			if (superclass != null && superclass != Object.class) {
				return superclass;
			}
		}

		return clazz;
	}

}
