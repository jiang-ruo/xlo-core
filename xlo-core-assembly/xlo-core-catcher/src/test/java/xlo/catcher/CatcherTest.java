package xlo.catcher;

import lombok.SneakyThrows;
import org.junit.Test;
import xlo.util.FileUtil;

import javax.management.modelmbean.XMLParseException;
import java.io.File;

/**
 * @author XiaoLOrange
 * @time 2021.06.28
 * @title
 */

public class CatcherTest {

	@Test
	@SneakyThrows
	public void ct(){
		File root = FileUtil.getRoot();
		File file = new File(root.getAbsolutePath() + "/xlo/catcher/catch.xml");
		try {
			Catcher c = new Catcher(file);
			System.out.println(c.catcher("pass", "/backstage/js/sdf"));
		} catch (XMLParseException e) {
			e.printStackTrace();
		}
	}

}
