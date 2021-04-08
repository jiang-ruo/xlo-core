package xlo.util.reflect;

import org.junit.Test;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title
 */

public class ClassTest {

	@Test
	public void ct(){
		Class c = ClassTest.class;
		System.out.println(c.getName());
	}

}
