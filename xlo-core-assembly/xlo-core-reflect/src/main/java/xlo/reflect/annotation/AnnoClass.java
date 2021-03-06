package xlo.reflect.annotation;

import lombok.Getter;
import xlo.reflect.detail.ClassSimpleDetail;

import java.lang.annotation.Annotation;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title 查找类上是否存在
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

	public AnnoClass(){}

	public AnnoClass(Annotation... target){
		this.target = target;
	}

	public AnnoClass(Class clazz, Annotation... target){
		this.target = target;
		this.csd = new ClassSimpleDetail(clazz);
	}

	public void setClazz(Class clazz){
		this.csd = new ClassSimpleDetail(clazz);
	}

}
