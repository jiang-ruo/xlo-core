package xlo.util;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;

/**
 * @author XiaoLOrange
 * @time 2020.12.03
 * @title
 */

public class XMLParseUtil {

	/**
	 * 元素节点
	 */
	public final static int NODETYPE_ELEMENT = 1;

	/**
	 * 属性节点
	 */
	public final static int NODETYPE_ATTRIBUTE = 2;

	/**
	 * 文本节点
	 */
	public final static int NODETYPE_TEXT = 3;

	/**
	 * 获取节点指定的属性
	 * @param n
	 * @param attr
	 */
	public static String getAttr(Node n, String attr){
		NamedNodeMap nnm = n.getAttributes();
		Node attribute = null;
		if(nnm != null) attribute = nnm.getNamedItem(attr);
		return attribute == null ? null : attribute.getNodeValue();
	}

	/**
	 * 获取所有节点的内容并放入数组中
	 * @param nl
	 * @return
	 */
	public static String[] getTexts(NodeList nl){
		String[] rs = new String[nl.getLength()];
		for (int i = 0; i < nl.getLength(); i++){
			rs[i] = getText(nl.item(i));
		}
		return rs;
	}

	/**
	 * 获取所有节点的内容并放入数组中
	 * @param nodes
	 * @return
	 */
	public static String[] getTexts(Node... nodes){
		String[] rs = new String[nodes.length];
		for (int i = 0; i < nodes.length; i++){
			rs[i] = getText(nodes[i]);
		}
		return rs;
	}

	/**
	 * 获取节点中的文本内容
	 * @return
	 */
	public static String getText(Node node){
		Node n = node;
		if(node.getNodeType() != NODETYPE_TEXT) n = node.getFirstChild();
		String text = n == null ? null : n.getNodeValue();
		return text;
	}

	/**
	 * 获取指定XML文件的根节点
	 * @return
	 */
	public static Document getDom(File f){
		if (!f.isFile()) try{
			throw new NoSuchFileException(f.getAbsolutePath());
		}catch (NoSuchFileException e){
			e.printStackTrace();
			return null;
		}

		//读取配置文件
		Document doc= null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
		} catch (SAXException | ParserConfigurationException | IOException e) {
			e.printStackTrace();
			return null;
		}
		return doc;
	}

	/**
	 * 获取指定XML文件的根节点
	 * @return
	 */
	public static Document getDom(InputStream in){
		//读取配置文件
		Document doc= null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
		} catch (SAXException | ParserConfigurationException | IOException e) {
			e.printStackTrace();
			return null;
		}
		return doc;
	}

}
