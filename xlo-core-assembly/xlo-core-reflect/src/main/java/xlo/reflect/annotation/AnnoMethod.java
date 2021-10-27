package xlo.reflect.annotation;

import lombok.Getter;
import xlo.reflect.detail.MethodSimpleDetail;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author XiaoLOrange
 * @time 2020.11.30
 * @time 2020.04.08
 * @title
 */

@Getter
public class AnnoMethod extends AnnoClass{

	/**
	 * 方法的详细信息
	 */
	private MethodSimpleDetail msd;

	public AnnoMethod(Method method, Annotation... target){
		super(target);
		this.msd = new MethodSimpleDetail(method);
	}

	public AnnoMethod(Class c, Method method, Annotation... target) {
		super(c, target);
		this.msd = new MethodSimpleDetail(method);
	}
}
