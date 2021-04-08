package xlo.util.reflect.detail;

import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

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
	 * 方法参数
	 * 字段
	 * 方法
	 * 类
	 */
	protected T paramter;

	/**
	 * 方法参数类型
	 * 字段类型
	 * 方法返回值类型 - 实际返回类型，声明T返回Object
	 * 类类型 = paramter
	 */
	protected Class type;

	/**
	 * 具有的注解
	 */
	protected Annotation[] annos;

}
