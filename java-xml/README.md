# Java XML Tutorials

In this serial of Java XML tutorials, we will show how to use the XML parser like DOM, SAX, StAX, and JDOM to read and write XML document; Also, JAXB to convert XML to/from objects.

https://mkyong.com/tutorials/java-xml-tutorials/


# Override the default TransformerFactoryImpl provided by the JDK
**AI responses may include mistakes**

To override the default TransformerFactoryImpl provided by the JDK (often linked to Sun's internal Xalan implementation) and use Apache Xalan's TransformerFactoryImpl (or any other implementation like Saxon), you can employ one of the following methods: 

## System Property
Set the javax.xml.transform.TransformerFactory system property to the fully qualified class name of the Apache Xalan TransformerFactoryImpl before calling TransformerFactory.newInstance(). 
```
    System.setProperty("javax.xml.transform.TransformerFactory", "org.apache.xalan.processor.TransformerFactoryImpl");
    TransformerFactory factory = TransformerFactory.newInstance();
    // Use the factory to create Transformers or Templates
```

## JAXP Properties File. 
Create a file named jaxp.properties in your JRE's lib directory (e.g., $JAVA_HOME/jre/lib/jaxp.properties) and add the following line: 
```
  javax.xml.transform.TransformerFactory=org.apache.xalan.processor.TransformerFactoryImpl`
```

## Explicit Instantiation (if applicable and desired). 
If you specifically want to use Apache Xalan and are not relying on the JAXP lookup mechanism for other XML factories, you can directly instantiate the Apache Xalan implementation: 
```
    TransformerFactory factory = new org.apache.xalan.processor.TransformerFactoryImpl();
    // Use the factory ...
```

Note that this approach bypasses the JAXP lookup mechanism entirely for TransformerFactory. 

## Important Considerations: 

* Ensure that the Apache Xalan (or your chosen XSLT processor) JARs are present on your application's classpath. 
* If you are experiencing issues with class loading or conflicts, consider the order of JARs on the classpath or investigate potential JAXP conflicts if other XML libraries are also present. 
* For more advanced scenarios or to avoid potential conflicts with other JAXP implementations, using the system property is generally the most robust and flexible method. 


