package xlo.util.reflect.detail;

import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title 保存类中元素的信息，名称，本体，类型，注解
 */

@Getter
public abstract class AbstractElement<T extends AnnotatedElement> {

	/**
	 * 方法参数名
	 * 字段名
	 * 方法名
	 * 类全名
	 */
	protected String name;

	/**
	 * 元素本身
	 * 方法参数
	 * 字段
	 * 方法
	 * 类
	 */
	protected T element;

	/**
	 * 方法参数类型
	 * 字段类型
	 * 方法返回值类型 - 实际返回类型，声明T返回Object
	 * 类类型 = element
	 */
	protected Class type;

	/**
	 * 具有的注解
	 */
	protected Annotation[] annos;

	/**
	 * 注解map
	 * Class - Annotation
	 */
	private Map<Class, Annotation> annoMap = new HashMap<>();

	protected void setAnnos(Annotation[] annos){
		this.annos = annos;
		for (Annotation anno: annos){
			annoMap.put(anno.getClass(), anno);
		}
	}

	public <E extends Annotation> E getAnnotation(Class<E> clazz){
		return (E) annoMap.get(clazz);
	}

}
