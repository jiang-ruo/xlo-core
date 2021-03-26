package xlo.util.math;

import org.junit.Test;
import xlo.infinite.Unsafe;
import xlo.util.math.NumUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XiaoLOrange
 * @time 2021.01.16
 * @title
 */

public class NumTest {

	@Test
	public void nt(){
		float a = NumUtil.toDecimal("3.s2");
		System.out.println(a);
	}

}
