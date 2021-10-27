package xlo.bitmap;

/**
 * @author XiaoLOrange
 * @time 2021.06.23
 * @title https://vlambda.com/wz_wYZQQdGc7F.html
 */

public interface BitMapIface<T extends Number> extends Iterable<Boolean> {


	/**
	 * Returns the number of elements in this collection.  If this collection
	 * contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
	 * <tt>Integer.MAX_VALUE</tt>.
	 *
	 * @return the number of elements in this collection
	 */
	T size();

	/**
	 * Returns <tt>true</tt> if this collection contains no elements.
	 *
	 * @return <tt>true</tt> if this collection contains no elements
	 */
	boolean isEmpty();

	/**
	 *
	 * @param position 位置，下标，从0开始
	 * @param value
	 * @return true - 新增成功， false - 新增失败
	 */
	boolean set(T position, boolean value);

	/**
	 * positionBegin <= x <= positionEnd
	 * @param positionBegin 从positionBegin
	 * @param positionEnd 到positionEnd
	 * @param value 将值设置为value
	 * @return
	 */
	boolean set(T positionBegin, T positionEnd, boolean value);

	/**
	 * 重置offsets指定的位置的值
	 * @param positions
	 * @return
	 */
	boolean reset(T... positions);

	/**
	 * 将指定位置的值设为true
	 * @param positions
	 * @return
	 */
	boolean setTrue(T... positions);

	/**
	 * 将指定位置的值设为false
	 * @param positions
	 * @return
	 */
	boolean setFalse(T... positions);

	/**
	 * 获取对应位置的boolean值
	 * @param position
	 * @return
	 */
	boolean get(T position);

	/**
	 * 将所有值取反
	 * @return
	 */
	boolean reverse();

	/**
	 * 将指定位置的值取反
	 * @param position
	 * @return
	 */
	boolean reverse(T position);

	/**
	 * positionsBegin <= x <= positionsEnd
	 * 将指定范围的值取反
	 * @param positionsBegin
	 * @param positionsEnd
	 * @return
	 */
	boolean reverse(T positionsBegin, T positionsEnd);

	/**
	 * Returns an array containing all of the elements in this collection.
	 * If this collection makes any guarantees as to what order its elements
	 * are returned by its iterator, this method must return the elements in
	 * the same order.
	 *
	 * <p>The returned array will be "safe" in that no references to it are
	 * maintained by this collection.  (In other words, this method must
	 * allocate a new array even if this collection is backed by an array).
	 * The caller is thus free to modify the returned array.
	 *
	 * <p>This method acts as bridge between array-based and collection-based
	 * APIs.
	 *
	 * @return an array containing all of the elements in this collection
	 */
	boolean[] toArray();

}
