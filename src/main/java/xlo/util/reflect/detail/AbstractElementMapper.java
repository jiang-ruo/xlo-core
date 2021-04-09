package xlo.util.reflect.detail;

import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title 方法具有的参数map/类具有的字段map/类具有的方法map
 *
 */

public abstract class AbstractElementMapper<T extends AnnotatedElement, V extends AbstractElement> {

	/**
	 * 拥有该参数的方法
	 * 拥有该字段的类
	 * 拥有该方法的类
	 */
	@Getter
	protected T owner;

	/**
	 * 方法参数列表 - 按照顺序排列
	 * 类字段列表
	 * method.toGenericString()列表
	 */
	@Getter
	protected String[] names;

	/**
	 * string - T
	 */
	protected Map<String, V> parameters = new HashMap<>();

	public abstract <E extends AnnotatedElement> E getElement(String name);

	public Class getType(String name){
		return this.parameters.get(name).getType();
	}

	public Annotation[] getAnnotations(String name){
		return this.parameters.get(name).getAnnos();
	}

}
