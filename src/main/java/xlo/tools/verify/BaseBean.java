package xlo.tools.verify;

import xlo.tools.verify.annotation.Verify;
import xlo.util.GetAttrUtil;
import xlo.util.ValueVerifyUtil;
import xlo.util.math.NumUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * @author XiaoLOrange
 * @time 2020.12.01
 * @title
 */

public class BaseBean {
	//数据校验注解
	private static Class<Verify> verify = Verify.class;

	public BaseBean verify(){
		//获取类的字段
		Class clazz = this.getClass();
		return verify(clazz);
	}

	private BaseBean verify(Class clazz){
		Field[] fs = clazz.getDeclaredFields();
		//字段值
		Object value;
		//非空注解
		Annotation anno;
		for (Field f: fs){
			value = GetAttrUtil.get(this, f);
			//校验
			if(!DataVerifyAnno(f, value)) return null;
		}

		//判断完当前类的字段，判断其父类(其父类间接或直接继承与BaseBean)
		Class father = clazz.getSuperclass();
		return father == BaseBean.class ? this : verify(father);
	}

	/**
	 * 数据校验注解
	 * @return
	 */
	private boolean DataVerifyAnno(Field field, Object value){
		Verify v = field.getDeclaredAnnotation(verify);
		if(v == null) return true;

		//判断value是否是数字
		boolean isNum = NumUtil.isNumber(value);

		//进行值限定校验
		String preVal = v.value();
		if(!("".equals(preVal))){
			Object val = isNum ? NumUtil.toNumber(preVal) : preVal;
			if(val.equals(value)) return false;
		}

		//进行非空校验
		boolean notNull = v.notNull();
		if(notNull){
			//判断变量不能是初始值
			if (ValueVerifyUtil.isInitial(field.getType(), value)) return false;
			//字符串不能为""
			if(value.equals("")) return false;
		}

		//进行数字取值校验
		if(isNum){
			int val = NumUtil.toNumber(value);
			int min = v.min();
			int max = v.max();
			if(val < min || val > max) return false;
		}

		//进行字符串长度校验
		int minlen = v.minlen();
		int maxlen = v.maxlen();
		int len = v.len();
		int strlen = value.toString().length();
		if(minlen != 0 && strlen < minlen) return false;
		if(maxlen != 0 && strlen > maxlen) return false;
		if(len != 0 && len != strlen) return false;

		//正则表达判断
		String reg = v.reg();
		if(!("".equals(reg))){
			if(!Pattern.matches(reg, value.toString())) return false;
		}

		return true;

	}

}
