package xlo.util.reflect.detail;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title 保存方法中的所有参数
 */

public class ParameterMapper extends AbstractElementMapper<Method, ParameterDetail> {

	public ParameterMapper(Method m){
		super.owner = m;
		Parameter[] ps = m.getParameters();
		ArrayList<String> names = new ArrayList<>(ps.length);
		String name;
		for (Parameter p: ps){
			name = p.getName();
			super.parameters.put(name, new ParameterDetail(p));
			names.add(name);
		}
		super.names = names.toArray(new String[0]);
	}

	@Override
	public ParameterDetail getElement(String name) {
		return super.parameters.get(name);
	}
}
