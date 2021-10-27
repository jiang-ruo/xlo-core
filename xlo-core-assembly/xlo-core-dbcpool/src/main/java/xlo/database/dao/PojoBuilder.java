package xlo.database.dao;

import xlo.database.annotation.Obj;
import xlo.database.annotation.Orm;
import xlo.reflect.FieldUtil;
import xlo.util.NumUtil;
import xlo.util.TypeUtil;
import xlo.util.infinite.Unsafe;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XiaoLOrange
 * @time 2020.10.17
 * @title 处理数据库的结果集
 * 		因为用反射调用创建对象，因此需要判断类中是否有嵌套的实体类，数组及非实体类直接抛弃
 *
 * 		优化，采用栈，深度遍历的方式处理，目前采用递归的方式处理，因为采用栈太麻烦了，
 */

public class PojoBuilder {

	/**
	 * 用于标记Field，因为私有属性必须先设为可赋值才可以赋值，
	 * 将标记为可赋值的Class放入到Map中，
	 * 不需要每次都将其设为可赋值
	 */
	private static Map<Class, Field[]> classMap = new HashMap<>();
	/**
	 * Object Relation Map
	 * 对象关系映射
	 */
	private static Map<Field, String> orm = new HashMap<>();
	/**
	 * 需要特殊处理的类
	 */
	private static Map<Class, Boolean> entity = new HashMap<>();
	static{
		entity.put(String.class, true);
	}

	/**
	 * 将本次创建已经创建的类放入到created数组中
	 * 防止环状创建，出不来
	 */
	private Map<Class, Object> created = new HashMap<>();

	/**
	 * 初始化Builder
	 * 可以不用每次新建对象都new一个Builder，节约内存
	 */
	public void init(){
		created.clear();
	}

	/**
	 * 创建类clazz，并将数据库中的值赋给clazz的属性及其子字段。
	 * @param clazz
	 * @param rs
	 * @return
	 */
	public Object create(Class clazz, ResultSet rs){
		Object obj = created.get(clazz);
		//已将创建过该对象，直接返回该对象
		if(obj != null) return obj;

		//创建clazz对象
		obj = create(clazz);
		if(obj == null) return null;
		created.put(clazz, obj);

		//获取可赋值的Field
		Field[] fs = getField(clazz);

		//从数据库中取出的值
		Object value = null;
		//
		Class type;
		//
		for (Field f: fs){
			//获取数据库中的值
			type = f.getType();
			value = getValue(f, type, rs);
			if(value == null) continue;
			setFieldValue(obj, f, type, value);
		}

		return obj;
	}

	/**
	 * 创建Clazz对象
	 * @param clazz
	 * @return
	 */
	private Object create(Class clazz){
		Object obj = null;
		try {
			obj = Unsafe.newInstance(clazz);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 获取指定类的属性，
	 * 先去Map中查找，没有的话就用反射获取，有的话就直接返回
	 * @param clazz
	 * @return
	 */
	private Field[] getField(Class clazz){
		Field[] fs = classMap.get(clazz);
		if(fs == null){
			fs = FieldUtil.getAndSetAccessible(clazz);
			classMap.put(clazz, fs);
		}
		return fs;
	}

	/**
	 * 获取数据库中对应的值
	 * @param field
	 * @param type
	 * @param rs
	 * @return
	 */
	private Object getValue(Field field, Class type, ResultSet rs){
		//从数据库中获取对应的值
		Object value = null;
		//判断该字段是否是一个Pojo对象
		Obj o = field.getDeclaredAnnotation(Obj.class);
		if(o == null){
			//获取属性在数据库中对应的字段名
			String column = getColumn(field);

			try {
				value = rs.getObject(column);
			} catch (SQLException throwables) {
//			throwables.printStackTrace();
			}
		}else{
			value = create(type, rs);
		}

		return value;
	}

	/**
	 * 获取field在数据库中映射的字段
	 * @param field
	 * @return
	 */
	private String getColumn(Field field){
		String column = orm.get(field);
		if(column == null){
			Orm o = field.getDeclaredAnnotation(Orm.class);
			column = o == null ? field.getName() : o.value();
			if(column.equals("")) field.getName();
			orm.put(field, column);
		}
		return column;
	}


	/**
	 * 赋值
	 * @param obj
	 * @param field
	 * @param type
	 * @param value
	 */
	private void setFieldValue(Object obj, Field field, Class type, Object value)  {
		if(type.isPrimitive() || TypeUtil.isBasePacking(type)) {
			//是基本数据类型或者其包装类
			setValue(obj, field, type, value);
		}else if(entity.get(type) != null){
			//一些需要特殊处理后才赋值的类型
			analiseFieldType(obj, field, type, value);
		}else{
			//直接赋值
			setValue(obj, field, value);
		}
	}

	/**
	 * 赋值，赋的是基本数据类型
	 * @param obj
	 * @param field
	 * @param value
	 */
	private void setValue(Object obj, Field field, Class type, Object value){
		if(type == boolean.class || type == Boolean.class){
			System.err.println("boolean类型未完善");
			return;
		}else if(type == char.class || type == Character.class){
			System.err.println("char类型未完善");
			return;
		}else{
			value = NumUtil.toNumber(type, value.toString());
		}

		setValue(obj, field, value);
	}

	/**
	 * 给特殊的类型赋值
	 * @param obj
	 * @param field
	 * @param type
	 * @param value
	 */
	private void analiseFieldType(Object obj, Field field, Class type, Object value){
		if(type == String.class) setValue(obj, field, value.toString());
	}

	/**
	 * 直接赋值
	 * @param obj
	 * @param field
	 * @param value
	 */
	private void setValue(Object obj, Field field, Object value){
		try {
			field.set(obj, value);
		} catch (IllegalAccessException | IllegalArgumentException e) {
//			e.printStackTrace();
		}
	}

}
