package xlo.infinite;

import xlo.database.dao.Pojo;
import org.junit.Test;
import xlo.infinite.Unsafe;

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
