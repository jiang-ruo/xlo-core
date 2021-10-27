package xlo.reflect.reflect;

import org.junit.Test;
import xlo.reflect.ReflectFinder;
import xlo.reflect.detail.AbstractElement;
import xlo.reflect.detail.ClassDetail;

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
