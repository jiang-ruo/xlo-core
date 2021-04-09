package xlo.util.reflect;

import org.junit.Test;
import xlo.util.reflect.annotation.AnnoUtil;
import xlo.util.reflect.detail.ClassDetail;
import xlo.util.reflect.detail.MethodMapper;

/**
 * @author XiaoLOrange
 * @time 2021.04.08
 * @title
 */

public class DetailTest {

	@Test
	public void dt(){
		Class clazz = AnnoUtil.class;
		ClassDetail cd = new ClassDetail(clazz);
		MethodMapper mm = cd.getMm();

		System.out.println(mm.getElement("getClasses"));
//		String[] names = mm.getNames();
//		for (String name: names){
//			System.out.println(mm.getElement(name));
//		}
	}

}
