package xlo.reflect.detail;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title 保存方法中的所有类
 */

public class MethodMapper extends AbstractElementMapper<Class, MethodSimpleDetail> {

	/**
	 * 方法名 - 方法全名
	 */
	private Map<String, String> simpleNameMap =new HashMap<>();

	public MethodMapper(Class clazz){
		super.owner = clazz;
		Method[] ms = clazz.getDeclaredMethods();
		ArrayList<String> names = new ArrayList<>(ms.length);
		String name;
		for (Method m: ms){
			name = parseMethodName(m);
			super.parameters.put(name, new MethodSimpleDetail(m));
			names.add(name);
		}
		super.names = names.toArray(new String[0]);
	}

	/**
	 * 返回方法全名
	 * 同时建立方法名 - 方法全名对应关系
	 * @param m
	 * @return
	 */
	private String parseMethodName(Method m){
		String simpleName = m.getName();
		String name = m.toGenericString();
		if(simpleNameMap.get(simpleName) == null) simpleNameMap.put(simpleName, name);
		return name;
	}

	/**
	 * 从方法名 - 方法全名键值对中检索是否有对应的值对
	 * 有则返回值，没有直接返回键，即用户输入
	 * @param name
	 * @return
	 */
	private String nameMap(String name){
		String methodName = simpleNameMap.get(name);
		return methodName == null ? name : methodName;
	}

	@Override
	public Class getType(String name) {
		name = nameMap(name);
		return super.getType(name);
	}

	@Override
	public Annotation[] getAnnotations(String name) {
		name = nameMap(name);
		return super.getAnnotations(name);
	}

	@Override
	public MethodSimpleDetail getElement(String name) {
		name = nameMap(name);
		return super.parameters.get(name);
	}
}
