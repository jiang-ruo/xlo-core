package xlo.adapter;

import xlo.util.GetAttrUtil;

import java.lang.reflect.Proxy;

/**
 * @author XiaoLOrange
 * @time 2021.04.22
 * @title
 */

public class ProxyAdapter {

	public final static Class<Proxy> PROXY_CLASS = Proxy.class;

	/**
	 * 获取该代理类代理的原型，
	 * @param proxy
	 * @return
	 */
	public static Class ProxyPrototypeAdapter(Object proxy){
		if(proxy instanceof Proxy){
			Object h = GetAttrUtil.findClassAndGet(PROXY_CLASS, proxy, "h");
			if(h != null) return (Class) GetAttrUtil.get(h, "type");
		}
		return proxy.getClass();
	}

}
