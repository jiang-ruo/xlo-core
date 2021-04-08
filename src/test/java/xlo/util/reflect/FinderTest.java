package xlo.util.reflect;

import org.junit.Test;
import xlo.util.reflect.detail.AbstractElement;
import xlo.util.reflect.detail.ClassDetail;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title
 */

public class FinderTest {

	@Test
	public void findTest(){
		System.out.println(ReflectFinder.FindClassUtil.hasFather(ClassDetail.class, AbstractElement.class));
	}

}
