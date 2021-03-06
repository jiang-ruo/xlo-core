package xlo.reflect.detail;

import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title
 */

@Getter
public class MethodDetail extends MethodSimpleDetail {

	/**
	 * 方法中参数的详细信息
	 */
	private ParameterMapper pm;

	public MethodDetail(Method method){
		super(method);
		this.pm = new ParameterMapper(method);
	}

}
