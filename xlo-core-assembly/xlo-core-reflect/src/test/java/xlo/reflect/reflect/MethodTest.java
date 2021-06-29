package xlo.reflect.reflect;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title
 */

public class MethodTest {

	public class MtBean<T>{
		public T test(){
			return null;
		}
	}

	@Test
	public void mt(){
		Class clazz = MtBean.class;
		Method m = clazz.getDeclaredMethods()[0];
		System.out.println(m.getReturnType());
		System.out.println(m.getGenericReturnType());
		System.out.println(m.getDeclaringClass());
	}

	@Test
	public void mt2(){
		Class clazz = MtBean.class;
		Method m = clazz.getDeclaredMethods()[0];
		Method m2 = clazz.getDeclaredMethods()[0];
		System.out.println(m.toString());
		System.out.println(m.toGenericString());
		System.out.println(m2.toString());
		System.out.println(m2.toGenericString());
		System.out.println(m.equals(m2));
		System.out.println(m.toString().equals(m2.toString()));
	}

	@Test
	public void mt3(){
		Class clazz = MtBean.class;
		Method m = clazz.getDeclaredMethods()[0];
		Method m2 = clazz.getDeclaredMethods()[0];
		Map<Method, Boolean> map = new HashMap<>();
		map.put(m, true);
		System.out.println(map.get(m2));
		System.out.println(m.hashCode());
		System.out.println(m2.hashCode());
	}

}
