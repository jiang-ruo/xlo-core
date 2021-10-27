package xlo.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author XiaoLOrange
 * @time 2021.01.26
 * @title 包扫描器
 * 来源：https://www.cnblogs.com/stdpain/p/11240690.html
 */

public class PackageScan {

	public static List<Class> scanPackage(String packageName){
		try {
			return scanByPackage(packageName);
		} catch (IOException | URISyntaxException e) {
//			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 包扫描
	 * @param packageName
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static List<Class> scanByPackage(String packageName) throws IOException, URISyntaxException {
		//我们传入的包名都是 "com.stdpain.xx" 这种的需要替换成 "com/stdpain/xx"
		String packagePath = packageName.replace(".", "/");
		//获取当前类加载器
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		List<Class> classes = new ArrayList<>();
		Enumeration<URL> resources = classLoader.getResources(packagePath);
		while (resources.hasMoreElements()) {
			//获取 url
			URL url = resources.nextElement();
			//判断url是不是一个 jar:// 类型的url 不同的url需要有不同的处理方式，我们默认只有文件系统和jar
			if (url.getProtocol().equals("jar")) {
				//handle jar Package
//				System.out.println(url);
				List<Class> scanedClasses = packageJarScan(url);
				classes.addAll(scanedClasses);
			} else {
				File file = new File(url.toURI());
				if (!file.exists()) {
//					System.out.println("file not exists = " + file.getAbsolutePath());
					continue;
				}
//				System.out.println("scan:" + file.getAbsolutePath());
				//handle file package
				List<Class> scanedClasses = packageScan(file, packageName);
				classes.addAll(scanedClasses);
			}
		}
		return classes;
	}

	public static List<Class> packageJarScan(URL url) throws IOException {
		//强制类型转换
		JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
		JarFile jarFile = jarURLConnection.getJarFile();
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		List<Class> classes = new ArrayList<>();
		//遍历判断
		while (jarEntries.hasMoreElements()) {
			JarEntry jarEntry = jarEntries.nextElement();
			String jarName = jarEntry.getName();
			if (jarEntry.isDirectory() || !jarName.endsWith(".class")) {
				continue;
			}
			String className = jarName.replace(".class", "").replace("/", ".");
			try {
//				System.out.println(className);
				Class clazz = Class.forName(className);
				classes.add(clazz);
//				System.out.println(className + "->load:success");
			} catch (ClassNotFoundException e) {
//                e.printStackTrace();
//				System.out.println("e.getMessage() = " + e.getMessage());
			}
		}
		return classes;
	}

	public static List<Class> packageScan(File curFile, String packageName) {
		ArrayList<Class> classes = new ArrayList<>();
		if (!curFile.isDirectory()) {
			return classes;
		}
		File[] files = curFile.listFiles();
		for (File file : files) {
			if (file.isFile() && file.getName().endsWith(".class")) {
				String fileName = file.getName().replace(".class", "");
				String className = packageName + "." + fileName;
				try {
					Class clazz = Class.forName(className);
					classes.add(clazz);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} else if (file.isDirectory()) {
				//如果是个目录那么就递归处理目录
				classes.addAll(packageScan(file, packageName + "." + file.getName()));
			}
		}
		return classes;
	}

}
