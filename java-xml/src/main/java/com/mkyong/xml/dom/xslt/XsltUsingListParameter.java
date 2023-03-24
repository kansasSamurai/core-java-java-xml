package com.mkyong.xml.dom.xslt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// XML -> XSLT -> Other formats
/**
 * The JRE default TransformerFactory does not support the following in XSL:
 * xmlns:map="xalan://java.util.HashMap"
 * 
 * and will result in:
   ERROR:  'Cannot find external method 'java.util.HashMap.get' (must be public).'
   FATAL ERROR:  'Cannot find external method 'java.util.HashMap.get' (must be public).'
    javax.xml.transform.TransformerConfigurationException: Cannot find external method 'java.util.HashMap.get' (must be public).
        at com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl.newTemplates(TransformerFactoryImpl.java:990)

 * Solution requires the use of apache specific TransformerFactory :: org.apache.xalan.processor.TransformerFactoryImpl@239963d8
> there may be other ways but seemingly the use of Apache FOP in the dependencies solves this issue.
> https://stackoverflow.com/questions/29450535/what-is-the-default-transformerfactory

This solved it (version 2.0):
        <dependency>
            <groupId>org.apache.xmlgraphics</groupId>
            <artifactId>fop</artifactId>
            <version>${fop.version}</version>
        </dependency>

 * @author Rick
 *
 */
public class XsltUsingListParameter {

    private static final String XML_FILENAME = "src/main/resources/staff-simple.xml";
    private static final String XSLT_FILENAME = "src/main/resources/xslt/staff-xml-template.xslt";
    // The following is the old/original version that uses 1.0/<html>
    // private static final String XSLT_FILENAME = "src/main/resources/xslt/staff-xml-html.xslt";

    public static void main(String[] args) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try (InputStream is = new FileInputStream(XML_FILENAME)) {

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(is);

            // transform xml to html via a xslt file
            try (FileOutputStream output =
                         new FileOutputStream("c:\\dev\\test\\staff.html")) {
                transform(doc, output);
            }

        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

    }

    private static void transform(Document doc, OutputStream output)
            throws TransformerException {

        Container a = new Container();
        Container b = new Container();
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> mapa = createMap("A");
        list.add(mapa);
        list.add(createMap("B"));

        a.property = b;
        b.property = list;

        String p = System.getProperty( "javax.xml.transform.TransformerFactory");
        System.out.println(p);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        // add XSLT
        Transformer transformer = transformerFactory.newTransformer( new StreamSource(new File(XSLT_FILENAME) ));
        transformer.setParameter("topobject", mapa);
//        transformer.setParameter("topobject", list); // this works generally but we want to test the map

        transformer.transform(new DOMSource(doc), new StreamResult(output));

    }

    public static String get(HashMap<String,String> m, String key) {
        return (String) m.get(key);
    }

    @SuppressWarnings("rawtypes")
    public static String get(NodeList list, String key) {
        HashMap m = (HashMap) list.item(0);
        return (String) m.get(key);
    }

    private static Map<String,String> createMap(String string) {
        Map<String,String> m = new HashMap<>();
        m.put("strand", string+"-strand");
        m.put("other", string+"-other");
        return m;
    }

}

class Container {
    Object property;
    
    public Object getProperty() { return property; }
}