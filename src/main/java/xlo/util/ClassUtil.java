package xlo.util;

import xlo.util.adapter.CGLibAdapter;
import xlo.util.adapter.ProxyAdapter;

import java.lang.reflect.Proxy;

/**
 * @author XiaoLOrange
 * @time 2021.06.08
 * @title
 */

public class ClassUtil {

	/**
	 * 从Proxy/CGLib中获取代理类
	 * 获取类的类类型，如果是被代理的类，则获取被代理的类类型
	 */
	public static Class getClass(Object obj){
		return obj instanceof Proxy ? ProxyAdapter.ProxyPrototypeAdapter(obj) : CGLibAdapter.CGlibPrototypeAdapter(obj);
	}

}
