package xlo.util;

import org.junit.Test;
import xlo.tools.catcher.Catcher;
import xlo.util.file.FileUtil;

import javax.management.modelmbean.XMLParseException;
import java.io.File;
import java.io.InputStream;

/**
 * @author XiaoLOrange
 * @time 2021.01.20
 * @title
 */

public class XmlTest {

	@Test
	public void xt(){
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
