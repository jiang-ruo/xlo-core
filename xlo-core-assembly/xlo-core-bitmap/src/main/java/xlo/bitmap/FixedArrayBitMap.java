package xlo.bitmap;

import java.util.Iterator;

/**
 * @author XiaoLOrange
 * @time 2021.06.23
 * @title
 */

public class FixedArrayBitMap implements BitMapIface<Integer>{

	public final static byte FREE = 0;
	public final static byte FULL = -1;
	/**
	 * 1	: 0, 0, 0, 0, 0, 0, 0, 1 :	对应求余 = 0
	 * 2	: 0, 0, 0, 0, 0, 0, 1, 0 :	对应求余 = 1
	 * 4	: 0, 0, 0, 0, 0, 1, 0, 0 :	对应求余 = 2
	 * 8	: 0, 0, 0, 0, 1, 0, 0, 0 :	对应求余 = 3
	 * 16	: 0, 0, 0, 1, 0, 0, 0, 0 :	对应求余 = 4
	 * 32	: 0, 0, 1, 0, 0, 0, 0, 0 :	对应求余 = 5
	 * 64	: 0, 1, 0, 0, 0, 0, 0, 0 :	对应求余 = 6
	 * -128	: 1, 0, 0, 0, 0, 0, 0, 0 :	对应求余 = 7
	 */
	private final static byte[] VALUES = {1, 2, 4, 8, 16, 32, 64, -128};
	//
	private final static int UNIT = 8;

	private final int length;

	private boolean defaultValue;
	//
	private int size = 0;
	// 数组中的每个byte称为1个group
	private final byte[] list;

	public FixedArrayBitMap(int length){
		this.length = length;
		int remainder = length % FixedArrayBitMap.UNIT;
		this.list = new byte[(length / FixedArrayBitMap.UNIT) + (remainder == 0 ? 0 : 1)];
	}

	@Override
	public Integer size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public boolean set(Integer position, boolean value) {
		if(position >= this.length || position < 0) throw new ArrayIndexOutOfBoundsException();

		//计算要改变的值在list中的位置
		int group = position / FixedArrayBitMap.UNIT;
		int offset = position % FixedArrayBitMap.UNIT;

		//对应offset标准的byte值
		byte standard = FixedArrayBitMap.VALUES[offset];
		//list中对应group的byte值
		byte listValue = this.list[group];
		//获取listvalue中对应offset的值。
		byte realValue = (byte) (listValue & standard);

		if(value == this.defaultValue){
			//将指定位置的值设为0

			//判断指定位置的值是否为0;
			if(realValue == 0) return true;
			//不是0
			listValue = (byte) (listValue ^ standard);
		}else{
			//将指定位置的值设为1
			//将指定位置的值设为1

			//判断指定位置的值是否为1;
			if(realValue != 0) return true;

			//不是1
			listValue = (byte) (listValue | standard);
		}
		this.list[group] = listValue;

		return true;
	}

	@Override
	public boolean set(Integer positionBegin, Integer positionEnd, boolean value) {
		for (int i = positionBegin; i <= positionEnd; i++){
			if(!set(i, value)) return false;
		}
		return true;
	}

	/**
	 * 充值指定位置的值
	 * @param positions
	 * @return
	 */
	@Override
	public boolean reset(Integer... positions) {
		for (Integer pos: positions){
			if(!set(pos, this.defaultValue)) return false;
		}
		return true;
	}

	@Override
	public boolean setTrue(Integer... positions) {
		for (Integer pos: positions){
			if(!set(pos, true)) return false;
		}
		return true;
	}

	@Override
	public boolean setFalse(Integer... positions) {
		for (Integer pos: positions){
			if(!set(pos, false)) return false;
		}
		return true;
	}

	@Override
	public boolean get(Integer position) {
		if(position >= this.length || position < 0) throw new ArrayIndexOutOfBoundsException();

		//计算要改变的值在list中的位置
		int group = position / FixedArrayBitMap.UNIT;
		int offset = position % FixedArrayBitMap.UNIT;

		//对应offset标准的byte值
		byte standard = FixedArrayBitMap.VALUES[offset];
		//list中对应group的byte值
		byte listValue = this.list[group];
		//获取listvalue中对应offset的值。
		byte realValue = (byte) (listValue & standard);

		//判断指定位置的值是否为0
		return realValue == 0 ? defaultValue : !defaultValue;
	}

	@Override
	public boolean reverse() {
		this.defaultValue = !this.defaultValue;
		return true;
	}

	@Override
	public boolean reverse(Integer position) {
		boolean value = get(position);
		return set(position, !value);
	}

	@Override
	public boolean reverse(Integer positionsBegin, Integer positionsEnd) {
		for(int i = positionsBegin; i <= positionsEnd; i++){
			if(!reverse(i)) return false;
		}
		return true;
	}

	@Override
	public boolean[] toArray() {
		boolean[] bools = new boolean[this.length];
		for (int i = 0; i < this.length; i++){
			bools[i] = get(i);
		}
		return bools;
	}

	@Override
	public Iterator<Boolean> iterator() {
		return new FabmIterator();
	}

	public class FabmIterator implements Iterator<Boolean>{

		int pointer = 0;

		@Override
		public boolean hasNext() {
			return this.pointer < length;
		}

		@Override
		public Boolean next() {
			return get(this.pointer++);
		}
	}

}
