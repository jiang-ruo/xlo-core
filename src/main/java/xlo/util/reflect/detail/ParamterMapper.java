package xlo.util.reflect.detail;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title 保存方法中的所有参数
 */

public class ParamterMapper extends AbstractElementMapper<Method, ParamterDetail> {

	public ParamterMapper(Method m){
		super.owner = m;
		Parameter[] ps = m.getParameters();
		ArrayList<String> names = new ArrayList<>(ps.length);
		String name;
		for (Parameter p: ps){
			name = p.getName();
			super.parameters.put(name, new ParamterDetail(p));
			names.add(name);
		}
		super.names = names.toArray(new String[0]);
	}

	@Override
	public ParamterDetail getElement(String name) {
		return super.parameters.get(name);
	}
}
