package xlo.util.reflect.annotation;

import lombok.Getter;
import xlo.util.reflect.detail.MethodSimpleDetail;

import java.lang.annotation.Annotation;

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

	public AnnoMethod(Class c, MethodSimpleDetail msd, Annotation... target) {
		super(c, target);
		this.msd = msd;
	}
}
