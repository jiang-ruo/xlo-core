package sys;

import org.junit.Test;

/**
 * @author XiaoLOrange
 * @time 2021.01.18
 * @title
 */

public class OtherTest {

	/**
	 * 获取系统详细信息
	 */
	@Test
	public void ot1(){
		System.out.println(System.getProperties());
	}

	/**
	 * 获取环境变量
	 */
	public void ot2(){
		System.out.println(System.getenv());
	}
}
