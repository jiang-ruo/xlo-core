package xlo.reflect.detail;

import java.lang.reflect.Field;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title
 */

public class FieldDetail extends AbstractElement<Field> {

	public FieldDetail(Field field){
		super.element = field;
		super.name = field.getName();
		super.type = field.getType();
		super.setAnnos(field.getAnnotations());
	}

}
