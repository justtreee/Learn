package xml;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author Treee
 * @E-mail ss673290035ss@gmail.com
 * @Date -2018/1/3-
 */

//使用dom方法对xml进行crud（增删改查）
public class Demo2 {
    //读取xml文档中的书名节点中的值
    @Test
    public void read1() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("src/xml/book.xml");

        NodeList list = document.getElementsByTagName("书名");
        Node node = list.item(1); //0指第一个标签的内容，1是第二个

        String content = node.getTextContent();
        System.out.println(content);
        //shuming222
    }

    //得到xml中的所有标签
    @Test
    public void read2() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("src/xml/book.xml");

        //得到根节点
        Node root = document.getElementsByTagName("书架").item(0);

        //得到根节点的孩子
        list(root);
//        书架
//        书
//        书名
//        作者
//        售价
//        书
//        书名
//        作者
//        售价
    }
    private void list(Node node){
        if(node instanceof Element) //若不进行判断，那么会输出#text，这个不仅是指内容，还指标签之后的回车字符
            System.out.println(node.getNodeName());
        NodeList list =  node.getChildNodes();
        //递归遍历
        for(int i=0; i<list.getLength(); i++)
        {
            Node child = list.item(i);
            list(child);
        }
    }

    //得到xml标签属性的值
    @Test
    public void read3() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("src/xml/book.xml");

        //在获取标签的值的时候，要把node强转成为Element,
        Element bookname = (Element) document.getElementsByTagName("书名").item(0);
        String value = bookname.getAttribute("name");
        System.out.println(value);
    }

    //向xml添加节点 第一本书 售价 59
    @Test
    public void add() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("src/xml/book.xml");

        //创建节点
        Element price = document.createElement("售价");
        price.setTextContent("59.00元");

        //把创建的节点挂到第一本书上
        Element book  =(Element) document.getElementsByTagName("书").item(0);
        book.appendChild(price);
        //这样子的话只是把内存中的更新了，并没有重新写回去

        // 把更新后的内存写入xml文档
        TransformerFactory tffactory = TransformerFactory.newInstance();
        Transformer tf = tffactory.newTransformer();
        tf.transform(new DOMSource(document), new StreamResult(new FileOutputStream("src/xml/book.xml")));
        //succeed
    }

}
