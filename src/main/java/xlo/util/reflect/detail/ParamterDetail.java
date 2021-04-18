package xlo.util.reflect.detail;

import java.lang.reflect.Parameter;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title
 */

public class ParamterDetail extends AbstractElement<Parameter> {

	public ParamterDetail(Parameter param){
		super.element = param;
		super.name = param.getName();
		super.type = param.getType();
		super.setAnnos(param.getAnnotations());
	}

}
