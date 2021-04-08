package xlo.util.reflect.annotation;

import lombok.Getter;
import xlo.util.reflect.detail.FieldDetail;

import java.lang.annotation.Annotation;

/**
 * @author XiaoLOrange
 * @time 2020.11.30
 * @time 2020.04.08
 * @title
 */

@Getter
public class AnnoField extends AnnoClass{

	/**
	 * 字段的详细信息
	 */
	private FieldDetail fd;

	public AnnoField(Class c, FieldDetail fd, Annotation[] target) {
		super(c, target);
		this.fd = fd;
	}

}
