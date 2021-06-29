package xlo.reflect.detail;

import lombok.Getter;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title
 */

@Getter
public class ClassDetail extends ClassSimpleDetail {

	/**
	 * 字段的详细信息
	 */
	private FieldMapper fm;

	/**
	 * method.toString() - MethodDetail
	 * 方法详情
	 */
	private MethodMapper mm;

	public ClassDetail(Object obj){
		super(obj);
	}

	public ClassDetail(Class clazz){
		super(clazz);
		this.fm = new FieldMapper(clazz);
		this.mm = new MethodMapper(clazz);
	}

}
