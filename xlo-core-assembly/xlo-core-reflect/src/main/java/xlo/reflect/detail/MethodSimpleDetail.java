package xlo.reflect.detail;

import lombok.Getter;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title
 */

@Getter
public class MethodSimpleDetail extends AbstractElement<Method> {
	/**
	 * 声明时的返回值类型，声明T就返回T
	 */
	protected Type declaredType;

	public MethodSimpleDetail(Method method){
		super.element = method;
		super.name = method.getName();
		super.type = method.getReturnType();
		this.declaredType = method.getGenericReturnType();
		super.setAnnos(method.getAnnotations());
	}
}
