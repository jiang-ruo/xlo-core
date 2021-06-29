package xlo.bitmap;

import org.junit.Test;

/**
 * @author XiaoLOrange
 * @time 2021.06.25
 * @title
 */

public class FabmTest {

	@Test
	public void ft(){
		FixedArrayBitMap fabm = new FixedArrayBitMap(10);
		fabm.set(4, true);
		fabm.reverse();
		int i = 0;
		for (boolean b: fabm){
			System.out.println(i++ + ": " + b);
		}
	}

}
