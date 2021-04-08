package xlo.util.reflect.annotation;

import lombok.Getter;
import xlo.util.reflect.detail.ClassSimpleDetail;

import java.lang.annotation.Annotation;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title
 */

@Getter
public class AnnoClass {

	/**
	 * 指定需要寻找的注解
	 */
	private Annotation[] target;

	/**
	 * 类信息
	 */
	private ClassSimpleDetail csd;

	public AnnoClass(Class c, Annotation[] target){
		this.target = target;
		this.csd = new ClassSimpleDetail(c);
	}

}
