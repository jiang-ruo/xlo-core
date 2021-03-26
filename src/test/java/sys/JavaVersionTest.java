package sys;

import org.junit.Test;
import xlo.database.dbc.ConnectDb;
import xlo.util.math.RandomUtil;

import java.sql.Connection;

/**
 * @author XiaoLOrange
 * @time 2020.12.11
 * @title
 */

public class JavaVersionTest {

	@Test
	public void jvt() {
		System.out.println(System.getProperty("java.version"));
	}

}
