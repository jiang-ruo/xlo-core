package xlo.catcher;

import java.util.regex.Pattern;

/**
 * @author XiaoLOrange
 * @time 2021.01.20
 * @title
 */

public class CatchTarget {

	//target的值
	private String value;
	//是否使用正则表达式,默认不启用
	private boolean regular = false;

	public CatchTarget(String value){
		this.value = value;
	}

	public CatchTarget(String value, boolean regular){
		this.value = value;
		this.regular = regular;
	}

	public String getValue() {
		return value;
	}

	public boolean isRegular() {
		return regular;
	}

	@Override
	public String toString() {
		return "regular:" + regular + ", target:" + value;
	}

	@Override
	public boolean equals(Object obj) {
		return regular ? Pattern.matches(value, obj.toString()) : value.equals(obj);
	}
}
