package com.mkyong.xml.dom.xslt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
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
import org.xml.sax.SAXException;

// XML -> XSLT -> Other formats
public class XsltXmlToHtmlDomParser {

    private static final String XML_FILENAME = "src/main/resources/staff-simple.xml";
    private static final String XSLT_FILENAME = "src/main/resources/xslt/staff-xml-html.xslt";
    private static final String HTML_FILENAME = "c:\\dev\\test\\staff.html";

    public static void main(String[] args) {


        try {
            Path path = Paths.get("c:\\dev\\test");
            Files.createDirectories(path);
            System.out.println("Directory created successfully: " + path.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to create directory: " + e.getMessage());
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try (InputStream is = new FileInputStream(XML_FILENAME)) {

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(is);

            // transform xml to html via a xslt file
            try (FileOutputStream output = new FileOutputStream(HTML_FILENAME)) {
                transform(doc, output);
            }

        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

    }

    private static void transform(Document doc, OutputStream output)
            throws TransformerException {

        // org.apache.xalan.processor.TransformerFactoryImpl 
        // ...\.m2\repository\xalan\xalan\2.6.0\xalan-2.6.0.jar\META-INF\services\
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        System.out.println("TransformerFactoryImpl :: " + transformerFactory.getClass().getCanonicalName());

        // add XSLT 
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(new File(XSLT_FILENAME)));

        transformer.transform(new DOMSource(doc), new StreamResult(output));

    }

    @SuppressWarnings("unused")
    private static Map<String,String> createMap(String string) {
        Map<String,String> m = new HashMap<>();
        m.put("strand", string+"-strand");
        m.put("other", string+"-other");
        return m;
    }

}

