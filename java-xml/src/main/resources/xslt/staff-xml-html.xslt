<?xml version="1.0" encoding="UTF-8"?>
<html xsl:version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:java="http://xml.apache.org/xalan/java"
    xmlns:map="xalan://java.util.HashMap"
    xmlns:jmap="http://www.oracle.com/XSL/Transform/java/java.util.HashMap"
    xmlns:mymap="http://www.oracle.com/XSL/Transform/java/com.mkyong.xml.dom.xslt.XsltUsingListParameter"
    extension-element-prefixes="map"
>
<!-- This version of the .xslt (XSL) file works with the builtin
     xalan TransformerFactory (com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl)
     but does not work when using FOP/org.apache.xalan.
 -->

    <body style="font-size:12pt;color:#FFFFFF;background-color:#0A0A0A">
        <h1 style="font-size:20pt;color:#FF0000">Hello World DOM + XML + XSLT</h1>
        <xsl:for-each select="company/staff">
            <ul>
                <li>
                    <xsl:value-of select="@id"/> -
                    <xsl:value-of select="name"/> -
                    <xsl:value-of select="role"/>
                </li>
            </ul>
        </xsl:for-each>

    <xsl:template match="/">
        <xsl:for-each select="company/staff">
            <ul>
                <li>
                    <xsl:value-of select="@id"/> -
                    <xsl:value-of select="name"/> -
                    <xsl:value-of select="role"/>
                </li>
            </ul>
        </xsl:for-each>
    </xsl:template>

    <xsl:param name="topobject" />

    <h3>H3 $topobject</h3>
    <!-- 
    <xsl:value-of select="$topobject"/>
     -->

    <h3>H3 substring-before()</h3>
    <xsl:value-of select="substring-before($topobject, 'strand')"/>

    <h3>H3 map</h3>
    <xsl:value-of select="map:get($topobject, 'strand')"/>

    <h3>H3 template</h3>
    <xsl:template match="/">
          $> <xsl:value-of select="."/> 
    </xsl:template>

    </body>
</html>