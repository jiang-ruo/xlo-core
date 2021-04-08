package xlo.util.reflect.detail;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title 保存类中的所有字段
 */

public class FieldMapper extends AbstractElementMapper<Class, FieldDetail> {

	public FieldMapper(Class clazz){
		super.owner = clazz;
		Field[] fs = clazz.getDeclaredFields();
		ArrayList<String> names = new ArrayList<>(fs.length);
		String name;
		for (Field f: fs){
			name = f.getName();
			super.parameters.put(name, new FieldDetail(f));
			names.add(name);
		}
		super.names = names.toArray(new String[0]);
	}

}
