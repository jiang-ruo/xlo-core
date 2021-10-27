package xlo.catcher;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xlo.util.XMLParseUtil;

import javax.management.modelmbean.XMLParseException;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XiaoLOrange
 * @time 2021.01.20
 * @title
 */

public class Catcher {

	/**
	 * document文档
	 */
	private Document doc;
	//
	private Map<String, List<CatchTarget>> catchMap = new HashMap<>();

	public Catcher(String path) throws XMLParseException {
		this(new File(path));
	}

	public Catcher(File file) throws XMLParseException {
		doc = XMLParseUtil.getDom(file);
		init();
	}

	public Catcher(InputStream in) throws XMLParseException {
		doc = XMLParseUtil.getDom(in);
		init();
	}

	/**
	 * 解析传入的XML文件
	 * @throws XMLParseException
	 */
	public void init() throws XMLParseException {
		buildMap();
	}

	/**
	 * 建立Map
	 */
	private void buildMap() throws XMLParseException {
		Element root = doc.getDocumentElement();
		if(!root.getTagName().equals("catcher")) throw new XMLParseException("XML文件解析错误");

		boolean regular = getRegular(root);
		buildMap(root.getElementsByTagName("catch"), regular);
	}

	/**
	 * 默认不开启正则表达
	 * @param root
	 * @return
	 */
	private boolean getRegular(Element root){
		boolean regular = false;

		//全局是否开启正则表达
		NodeList nl = root.getElementsByTagName("regular");
		Node n;
		if(nl.getLength() != 0){
			n = nl.item(0);
			regular = XMLParseUtil.getText(n).equals("true");
		}
		return regular;
	}

	/**
	 * 二级build
	 * @param nl 所有catch节点
	 * @param regular 全局正则表达规则
	 */
	private void buildMap(NodeList nl, boolean regular){
		//如果catch没有设置regular属性,则使用全局regular属性
		boolean cregular;
		//catch节点数量
		int nums = nl.getLength();
		Node node;
		String attrReg, id;
		List<CatchTarget> list;
		for (int i = 0; i < nums; i++){
			//重置该节点的正则表达启用情况，默认继承父类
			cregular = regular;

			node = nl.item(i);
			//判断该节点是否启用正则表达
			attrReg = XMLParseUtil.getAttr(node, "regular");
			if(attrReg != null) cregular = attrReg.equals("true");

			id = XMLParseUtil.getAttr(node, "id");
			//直接使用node.getChildNodes()会取得不知道是哪部分的文本节点
			list = buildTarget(((Element) node).getElementsByTagName("target"), cregular);
			catchMap.put(id, list);
		}
	}

	/**
	 * 获取对应catch下的target,
	 * @param nl
	 * @param regular 传入当前catch的正则表达模式启用情况
	 * @return
	 */
	private ArrayList<CatchTarget> buildTarget(NodeList nl, boolean regular){
		int nums = nl.getLength();
		ArrayList<CatchTarget> list = new ArrayList<>(nums);
		Node node;
		boolean cregular;
		String attrReg, val;
		for (int i = 0; i < nums; i++){
			//重置该节点的正则表达启用情况，默认继承父类
			cregular = regular;
			node = nl.item(i);
			//判断该节点是否启用正则表达
			attrReg = XMLParseUtil.getAttr(node, "regular");
			if(attrReg != null) cregular = attrReg.equals("true");

			val = XMLParseUtil.getText(node);
			val = val.trim();
			list.add(new CatchTarget(val, cregular));
		}
		return list;
	}

	/**
	 * 判断是否在catch的捕捉范围
	 * @param id id为xml中catch的id
	 * @param val 为传入的值
	 * @return
	 */
	public boolean catcher(String id, String val){
		List<CatchTarget> list = catchMap.get(id);
		if(list == null) return false;

		for (CatchTarget ct: list){
			if(ct.equals(val)) return true;
		}
		return false;
	}

}
