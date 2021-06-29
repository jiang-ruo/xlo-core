package xlo.infinite;

import org.junit.Test;
import xlo.util.infinite.Unsafe;

/**
 * @author XiaoLOrange
 * @time 2021.01.15
 * @title
 */

public class UnsafeTest {

	@Test
	public void ut(){
		try {
			System.out.println(new Unsafe().newInstance(Pojo.class).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
