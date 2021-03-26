package xlo.util;

import org.junit.Test;
import xlo.util.PackageScan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author XiaoLOrange
 * @time 2021.01.26
 * @title
 */

public class PackageScanTest {

	@Test
	public void pst() throws IOException, URISyntaxException {
		List<Class> list = PackageScan.scanByPackage("xlo.util");
		for (Class l: list){
			System.out.println(l);
		}
	}

}
