package xlo.tools.verify.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author XiaoLOrange
 * @time 2020.12.30
 * @title
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Verify {

	/*值限定， 为""表示不限定取值*/
	String value() default "";

	/*只要启用该注解，则默认不能为空*/
	boolean notNull() default true;

	/*字符串最小长度*/
	int minlen() default 0;

	/*字符串最大长度*/
	int maxlen() default 0;

	/*字符串长度*/
	int len() default 0;

	/*数字最小值*/
	int min() default -2147483648;

	/*数字最大值*/
	int max() default 2147483647;

	/*正则表达*/
	String reg() default "";


}
