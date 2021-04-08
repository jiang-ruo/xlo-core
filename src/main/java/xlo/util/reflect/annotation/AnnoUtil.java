package xlo.util.reflect.annotation;

import xlo.util.PackageScan;
import xlo.util.reflect.detail.FieldDetail;
import xlo.util.reflect.detail.MethodSimpleDetail;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author XiaoLOrange
 * @time 2020.11.29
 * @time 2021.04.08
 * @title
 */

public class AnnoUtil {

	/**
	 * 获取指定的包下，具有指定注解的类
	 * 为空则获取所有带有注解的类
	 * @param packagi
	 * @param annos
	 * @return
	 */
	public static LinkedList<AnnoClass> getClasses(String packagi, Class... annos){
		LinkedList container = new LinkedList();
		//获取当前工程下的所有.class文件，不包括架包中的文件
		List<Class> cs = PackageScan.scanPackage(packagi);

		Annotation[] as;
		for (Class c: cs){
			as = getAnno(c, annos);
			if(as.length > 0) container.add(new AnnoClass(c, as));
		}
		return container;
	}

	/**
	 * 获取指定包下所有指定注解的方法
	 * 为空则获取所有带有注解的方法
	 * @param annos
	 * @return
	 */
	public static LinkedList<AnnoMethod> getMethods(String packagi, Class... annos){
		LinkedList container = new LinkedList();
		//获取所有的类。

		//获取当前工程下的所有.class文件，不包括架包中的文件
//		Class[] cs = JFileUtil.getClasses();
		List<Class> cs = PackageScan.scanPackage(packagi);

		Method[] ms;
		Annotation[] as;
		for (Class clazz: cs){
			//获取类中的所有方法
			ms = clazz.getDeclaredMethods();
			for (Method m: ms){
				as = getAnno(m, annos);
				if(as.length > 0) container.add(new AnnoMethod(clazz, new MethodSimpleDetail(m), as));
			}
		}
		return container;
	}

	/**
	 * 获取指定包下带有指定注解的属性
	 * 为空则获取所有带有注解的属性
	 * @param packagi
	 * @param annos
	 * @return
	 */
	public static LinkedList<AnnoField> getFields(String packagi, Class... annos){
		LinkedList container = new LinkedList();
		//获取所有的类。
		//获取当前工程下的所有.class文件，不包括架包中的文件
//		Class[] cs = JFileUtil.getClasses();
		List<Class> cs = PackageScan.scanPackage(packagi);

		Field[] fs;
		Annotation[] as;
		for (Class clazz: cs){
			//获取类中的所有字段
			fs = clazz.getDeclaredFields();
			for(Field f: fs){
				as = getAnno(f, annos);
				if(as.length > 0) container.add(new AnnoField(clazz, new FieldDetail(f), as));
			}
		}
		return container;
	}

	/**
	 * 输入一个注解数组，返回obj具有的输入注解中的所有注解，
	 * 允许获取继承的注解
	 * @param obj
	 * @param annos 没有输入则返回所有注解
	 * @return
	 */
	public static Annotation[] getAnno(AnnotatedElement obj, Class... annos){
		if(annos.length == 0) return getAnno(obj);
		ArrayList<Annotation> as = new ArrayList<>(annos.length);
		Annotation anno;
		for (Class target: annos){
			anno = obj.getAnnotation(target);
			if(anno != null) as.add(anno);
		}
		return as.toArray(new Annotation[0]);
	}

}
