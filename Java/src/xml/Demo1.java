package xml;


import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @Author Treee
 * @E-mail ss673290035ss@gmail.com
 * @Date -2018/1/3-
 */

public class Demo1 {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        //1. 创建工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //2. 得到dom解析器
        DocumentBuilder builder = factory.newDocumentBuilder();
        //3. 解析xml文档，获得文档的document
        Document document = builder.parse("src/xml/book.xml");

    }
}
