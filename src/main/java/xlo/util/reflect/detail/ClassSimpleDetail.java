package xlo.util.reflect.detail;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title
 */

public class ClassSimpleDetail extends AbstractElement<Class> {

	public ClassSimpleDetail(Class clazz){
		super.name = clazz.getName();
		super.element = clazz;
		super.type = clazz;
		super.setAnnos(clazz.getAnnotations());
	}

}
