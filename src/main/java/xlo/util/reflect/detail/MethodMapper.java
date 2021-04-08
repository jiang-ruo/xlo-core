package xlo.util.reflect.detail;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title 保存方法中的所有类
 */

public class MethodMapper extends AbstractElementMapper<Class, MethodDetail> {

	public MethodMapper(Class clazz){
		super.owner = clazz;
		Method[] ms = clazz.getDeclaredMethods();
		ArrayList<String> names = new ArrayList<>(ms.length);
		String name;
		for (Method m: ms){
			name = m.toGenericString();
			super.parameters.put(name, new MethodDetail(m));
			names.add(name);
		}
		super.names = names.toArray(new String[0]);
	}

}
