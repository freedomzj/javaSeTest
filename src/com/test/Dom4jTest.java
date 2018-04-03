package com.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jTest {
	public static void main(String[] args) throws DocumentException {
		try {
			File file= makeDocument();
//			readDom4jByElementName(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
	}
	
	/**
	 * 根据dom4j操作xml文件
	 * @return
	 * @throws 
	 */

	public static File makeDocument() throws Exception{
		File file=new File(" /Users/zengjie/Desktop/dom4j1.xml");
		Document document= DocumentHelper.createDocument(); 
		document.addComment("内部文档请勿传阅");
		Element rootElement= document.addElement("root");
		rootElement.addAttribute("language", "java");
		for (int i = 1; i < 10000; i++) {
			Element parentElement= rootElement.addElement("user");
			parentElement.addAttribute("id", ""+i+"");
			Element element= parentElement.addElement("age");
			element.addText("1");
			Element element1= parentElement.addElement("sex");
			element1.addText("男");
			Element element2= parentElement.addElement("username");
			element2.addText("曾杰"+i);
		}
		
		 //输出全部原始数据，在编译器中显示  
        OutputFormat format = OutputFormat.createPrettyPrint();  
        format.setEncoding("UTF-8");//根据需要设置编码  
        XMLWriter writer = new XMLWriter(System.out, format);  
        document.normalize();  
        writer.write(document);    
        writer.close();  
        // 输出全部原始数据，并用它生成新的我们需要的XML文件  
        XMLWriter writer2 = new XMLWriter(new FileWriter(file), format);  
        writer2.write(document); //输出到文件  
        writer2.close();  
		return file;
	}
	
	public String getApplcationConfigFromXMLTest(File file){  
	    String value = "";  
	    try {  
	        SAXReader sax = new SAXReader();  
	        Document xmlDoc = sax.read(file);  
	        Element root = xmlDoc.getRootElement();//根节点  
	        Iterator it = root.elementIterator();  
	        while(it.hasNext()){  
	            Element ele = (Element)it.next();  
	            Attribute attribute = ele.attribute("type");  
	            if(attribute.getStringValue().equals("Pending")){  
	                attribute.setValue("sendread2");//修改属性节点的值  
	            }  
	  
	            Attribute flowType = ele.attribute("flowType");  
	            flowType.detach();//删除某个属性  
	              
	            ele.addAttribute("type", "Pending");//添加一个属性节点  
	        }  
	        Element new_cdata = root.addElement("new_cdata");//添加一个元素  
	        new_cdata.addCDATA("tst&ree");  
	          
	        Element new_ele = root.addElement("new_ele");//添加一个元素  
	        new_ele.addText("33434343");  
	  
	        Element obj = (Element)root.selectObject("//pro[@type='att']");//根据XPath查找元素  
	        obj.setText("测试dddddd");//修改元素的值 即text节点  
	             //输出全部原始数据，在编译器中显示  
	           OutputFormat format = OutputFormat.createPrettyPrint();  
	           format.setEncoding("GBK");  
	           XMLWriter writer = new XMLWriter(System.out, format);  
	           writer.write(xmlDoc);    
	           writer.close();  
	           // 输出全部原始数据，并用它生成新的我们需要的XML文件  
	           XMLWriter writer2 = new XMLWriter(new FileWriter(new File(  
	             "test.xml")), format);  
	           writer2.write(xmlDoc); //输出到文件  
	           writer2.close();  
	    } catch (DocumentException e) {  
	        System.out.println(e.getMessage());  
	        e.printStackTrace();  
	    }catch(IOException e){  
	        e.printStackTrace();  
	    }  
	    return value ;  
	} 
	
	
	public static void readDom4j(File file) throws DocumentException{
		SAXReader sax = new SAXReader();// 创建一个SAXReader对象
//		File xmlFile = new File(file);// 根据指定的路径创建file对象
		Document document = sax.read(file);// 获取document对象,如果文档无节点，则会抛出Exception提前结束
		Element root = document.getRootElement();// 获取根节点
		getNodes1(root);// 从根节点开始遍历所有节点	
	}
	
	/**
	 *  //1.找到节点user上面id为5000的节点
	 *2.修改id为5000的username为 曾杰java攻城狮
	 * @param file
	 * @return
	 * @throws DocumentException
	 */
	public static void readDom4jByElementName(File file) throws Exception{
		SAXReader sax = new SAXReader();// 创建一个SAXReader对象
//		File xmlFile = new File(file);// 根据指定的路径创建file对象
		Document document = sax.read(file);// 获取document对象,如果文档无节点，则会抛出Exception提前结束
		Element root = document.getRootElement();// 获取根节点
	   getNodes1(root);// 从根节点开始遍历所有节点
	   
	   
	   //输出全部原始数据，在编译器中显示  
       OutputFormat format = OutputFormat.createPrettyPrint();  
       format.setEncoding("utf-8");  
       XMLWriter writer = new XMLWriter(System.out, format);  
       writer.write(document);    
       writer.close();  
       // 输出全部原始数据，并用它生成新的我们需要的XML文件  
       XMLWriter writer2 = new XMLWriter(new FileWriter(file), format);  
       writer2.write(document); //输出到文件  
       writer2.close();  
       
	}
	
	
	/**
	 * 从指定节点开始,递归遍历所有子节点
	 * 
	 * @author chenleixing
	 */
	public static void getNodes1(Element node) {
		
		
		// 当前节点的名称、文本内容和属性
		if(node.getName().equals("username") && node.getTextTrim().equals("曾杰3") ){
			node.setText("java攻城狮");
		}
		System.out.println("当前节点名称：" + node.getName());// 当前节点名称
		System.out.println("当前节点的内容：" + node.getTextTrim());// 当前节点名称
		List<Attribute> listAttr = node.attributes();// 当前节点的所有属性的list
		for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
			String name = attr.getName();// 属性名称
			String value = attr.getValue();// 属性的值
			System.out.println("属性名称：" + name + "属性值：" + value);
		}

		// 递归遍历当前节点所有的子节点
		List<Element> listElement = node.elements();// 所有一级子节点的list
		for (Element e : listElement) {// 遍历所有一级子节点
			getNodes1(e);// 递归
		}
	}
	
	
	
	/**
	 * 从指定节点开始,递归遍历所有子节点
	 * 
	 * @author chenleixing
	 */
	public static void getNodes(Element node) {
		
		
		// 当前节点的名称、文本内容和属性
		System.out.println("当前节点名称：" + node.getName());// 当前节点名称
		System.out.println("当前节点的内容：" + node.getTextTrim());// 当前节点名称
		List<Attribute> listAttr = node.attributes();// 当前节点的所有属性的list
		for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
			String name = attr.getName();// 属性名称
			String value = attr.getValue();// 属性的值
			System.out.println("属性名称：" + name + "属性值：" + value);
		}

		// 递归遍历当前节点所有的子节点
		List<Element> listElement = node.elements();// 所有一级子节点的list
		for (Element e : listElement) {// 遍历所有一级子节点
			getNodes1(e);// 递归
		}
	}
}