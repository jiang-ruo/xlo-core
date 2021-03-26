package ibatis;

import extend.org.apache.ibatis.reflection.invoker.MethodInvoker;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author XiaoLOrange
 * @time 2021.03.04
 * @title
 */

public class invokeMethodTest {

	@Test
	public void imt(){
		Class c = MethodInvoker.class;
		Method[] ms = c.getDeclaredMethods();
		Class[] cs;
		for (Method m: ms){
			cs = m.getParameterTypes();
			System.out.println(m.getReturnType());
			for (Class cc: cs){
				System.out.println(cc);
			}
		}
	}

}
