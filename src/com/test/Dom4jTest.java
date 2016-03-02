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
//			File file= makeDocument();
			readDom4jByElementName(new File("D://dom4j1.xml"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
	}
	
	/**
	 * ����dom4j����xml�ļ�
	 * @return
	 * @throws 
	 */

	public static File makeDocument() throws Exception{
		File file=new File("D://dom4j1.xml");
		Document document= DocumentHelper.createDocument(); 
		document.addComment("�ڲ��ĵ�������");
		Element rootElement= document.addElement("root");
		rootElement.addAttribute("language", "java");
		for (int i = 1; i < 10000; i++) {
			Element parentElement= rootElement.addElement("user");
			parentElement.addAttribute("id", ""+i+"");
			Element element= parentElement.addElement("age");
			element.addText("1");
			Element element1= parentElement.addElement("sex");
			element1.addText("��");
			Element element2= parentElement.addElement("username");
			element2.addText("����"+i);
		}
		
		 //���ȫ��ԭʼ���ݣ��ڱ���������ʾ  
        OutputFormat format = OutputFormat.createPrettyPrint();  
        format.setEncoding("UTF-8");//������Ҫ���ñ���  
        XMLWriter writer = new XMLWriter(System.out, format);  
        document.normalize();  
        writer.write(document);    
        writer.close();  
        // ���ȫ��ԭʼ���ݣ������������µ�������Ҫ��XML�ļ�  
        XMLWriter writer2 = new XMLWriter(new FileWriter(file), format);  
        writer2.write(document); //������ļ�  
        writer2.close();  
		return file;
	}
	
	public String getApplcationConfigFromXMLTest(File file){  
	    String value = "";  
	    try {  
	        SAXReader sax = new SAXReader();  
	        Document xmlDoc = sax.read(file);  
	        Element root = xmlDoc.getRootElement();//���ڵ�  
	        Iterator it = root.elementIterator();  
	        while(it.hasNext()){  
	            Element ele = (Element)it.next();  
	            Attribute attribute = ele.attribute("type");  
	            if(attribute.getStringValue().equals("Pending")){  
	                attribute.setValue("sendread2");//�޸����Խڵ��ֵ  
	            }  
	  
	            Attribute flowType = ele.attribute("flowType");  
	            flowType.detach();//ɾ��ĳ������  
	              
	            ele.addAttribute("type", "Pending");//���һ�����Խڵ�  
	        }  
	        Element new_cdata = root.addElement("new_cdata");//���һ��Ԫ��  
	        new_cdata.addCDATA("tst&ree");  
	          
	        Element new_ele = root.addElement("new_ele");//���һ��Ԫ��  
	        new_ele.addText("33434343");  
	  
	        Element obj = (Element)root.selectObject("//pro[@type='att']");//����XPath����Ԫ��  
	        obj.setText("����dddddd");//�޸�Ԫ�ص�ֵ ��text�ڵ�  
	             //���ȫ��ԭʼ���ݣ��ڱ���������ʾ  
	           OutputFormat format = OutputFormat.createPrettyPrint();  
	           format.setEncoding("GBK");  
	           XMLWriter writer = new XMLWriter(System.out, format);  
	           writer.write(xmlDoc);    
	           writer.close();  
	           // ���ȫ��ԭʼ���ݣ������������µ�������Ҫ��XML�ļ�  
	           XMLWriter writer2 = new XMLWriter(new FileWriter(new File(  
	             "test.xml")), format);  
	           writer2.write(xmlDoc); //������ļ�  
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
		SAXReader sax = new SAXReader();// ����һ��SAXReader����
//		File xmlFile = new File(file);// ����ָ����·������file����
		Document document = sax.read(file);// ��ȡdocument����,����ĵ��޽ڵ㣬����׳�Exception��ǰ����
		Element root = document.getRootElement();// ��ȡ���ڵ�
		getNodes1(root);// �Ӹ��ڵ㿪ʼ�������нڵ�	
	}
	
	/**
	 *  //1.�ҵ��ڵ�user����idΪ5000�Ľڵ�
	 *2.�޸�idΪ5000��usernameΪ ����java����ʨ
	 * @param file
	 * @return
	 * @throws DocumentException
	 */
	public static void readDom4jByElementName(File file) throws Exception{
		SAXReader sax = new SAXReader();// ����һ��SAXReader����
//		File xmlFile = new File(file);// ����ָ����·������file����
		Document document = sax.read(file);// ��ȡdocument����,����ĵ��޽ڵ㣬����׳�Exception��ǰ����
		Element root = document.getRootElement();// ��ȡ���ڵ�
	   getNodes1(root);// �Ӹ��ڵ㿪ʼ�������нڵ�
	   
	   
	   //���ȫ��ԭʼ���ݣ��ڱ���������ʾ  
       OutputFormat format = OutputFormat.createPrettyPrint();  
       format.setEncoding("utf-8");  
       XMLWriter writer = new XMLWriter(System.out, format);  
       writer.write(document);    
       writer.close();  
       // ���ȫ��ԭʼ���ݣ������������µ�������Ҫ��XML�ļ�  
       XMLWriter writer2 = new XMLWriter(new FileWriter(file), format);  
       writer2.write(document); //������ļ�  
       writer2.close();  
       
	}
	
	
	/**
	 * ��ָ���ڵ㿪ʼ,�ݹ���������ӽڵ�
	 * 
	 * @author chenleixing
	 */
	public static void getNodes1(Element node) {
		
		
		// ��ǰ�ڵ�����ơ��ı����ݺ�����
		if(node.getName().equals("username") && node.getTextTrim().equals("����3") ){
			node.setText("java����ʨ");
		}
		System.out.println("��ǰ�ڵ����ƣ�" + node.getName());// ��ǰ�ڵ�����
		System.out.println("��ǰ�ڵ�����ݣ�" + node.getTextTrim());// ��ǰ�ڵ�����
		List<Attribute> listAttr = node.attributes();// ��ǰ�ڵ���������Ե�list
		for (Attribute attr : listAttr) {// ������ǰ�ڵ����������
			String name = attr.getName();// ��������
			String value = attr.getValue();// ���Ե�ֵ
			System.out.println("�������ƣ�" + name + "����ֵ��" + value);
		}

		// �ݹ������ǰ�ڵ����е��ӽڵ�
		List<Element> listElement = node.elements();// ����һ���ӽڵ��list
		for (Element e : listElement) {// ��������һ���ӽڵ�
			getNodes1(e);// �ݹ�
		}
	}
	
	
	
	/**
	 * ��ָ���ڵ㿪ʼ,�ݹ���������ӽڵ�
	 * 
	 * @author chenleixing
	 */
	public static void getNodes(Element node) {
		
		
		// ��ǰ�ڵ�����ơ��ı����ݺ�����
		System.out.println("��ǰ�ڵ����ƣ�" + node.getName());// ��ǰ�ڵ�����
		System.out.println("��ǰ�ڵ�����ݣ�" + node.getTextTrim());// ��ǰ�ڵ�����
		List<Attribute> listAttr = node.attributes();// ��ǰ�ڵ���������Ե�list
		for (Attribute attr : listAttr) {// ������ǰ�ڵ����������
			String name = attr.getName();// ��������
			String value = attr.getValue();// ���Ե�ֵ
			System.out.println("�������ƣ�" + name + "����ֵ��" + value);
		}

		// �ݹ������ǰ�ڵ����е��ӽڵ�
		List<Element> listElement = node.elements();// ����һ���ӽڵ��list
		for (Element e : listElement) {// ��������һ���ӽڵ�
			getNodes1(e);// �ݹ�
		}
	}
}
