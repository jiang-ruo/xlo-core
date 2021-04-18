package xlo.util.reflect.detail;

import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title 方法具有的参数map/类具有的字段map/类具有的方法map
 *
 */

public abstract class AbstractElementMapper<T extends AnnotatedElement, V extends AbstractElement> implements Iterable<V> {

	/**
	 * 拥有该参数的方法
	 * 拥有该字段的类
	 * 拥有该方法的类
	 */
	@Getter
	protected T owner;

	/**
	 * 方法参数列表 - 按照顺序排列
	 * 类字段列表
	 * method.toGenericString()列表
	 */
	@Getter
	protected String[] names;

	/**
	 * string - T
	 */
	protected Map<String, V> parameters = new HashMap<>();

	/**
	 * 获取方法中指定的参数，
	 * 类中指定的字段
	 * @param name
	 * @return
	 */
	public abstract V getElement(String name);

	/**
	 * 获取方法的参数类型，类的字段类型
	 * @param name
	 * @return
	 */
	public Class getType(String name){
		return this.parameters.get(name).getType();
	}

	/**
	 * 获取指定的注解
	 * @param name
	 * @return
	 */
	public Annotation[] getAnnotations(String name){
		return this.parameters.get(name).getAnnos();
	}

	public int size(){
		return names == null ? 0 : this.names.length;
	}

	/**
	 * 迭代器
	 */
	private class AemIterator implements Iterator<V> {

		/**
		 * mapper中元素的数量
		 */
		private int size = 0;

		/**
		 * 当前位置
		 */
		private int cursor = 0;

		public AemIterator(){
			if(names != null) size = names.length;
		}

		/**
		 * Returns {@code true} if the iteration has more elements.
		 * (In other words, returns {@code true} if {@link #next} would
		 * return an element rather than throwing an exception.)
		 *
		 * @return {@code true} if the iteration has more elements
		 */
		@Override
		public boolean hasNext() {
			return size - 1 >= cursor;
		}

		/**
		 * Returns the next element in the iteration.
		 *
		 * @return the next element in the iteration
		 * @throws NoSuchElementException if the iteration has no more elements
		 */
		@Override
		public V next() {
			return parameters.get(names[cursor++]);
		}
	}

	/**
	 * 实现该方法就可支持foreach循环
	 * @return
	 */
	@Override
	public Iterator<V> iterator() {
		return new AemIterator();
	}

}
