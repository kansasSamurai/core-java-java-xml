<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:java="http://xml.apache.org/xalan/java"
    xmlns:map="xalan://java.util.HashMap"
    xmlns:jmap="http://www.oracle.com/XSL/Transform/java/java.util.HashMap"
    xmlns:mymap="http://www.oracle.com/XSL/Transform/java/com.mkyong.xml.dom.xslt.XsltUsingListParameter"
    extension-element-prefixes="map"
>
<!-- UNDER DEVELOPMENT
This supercedes -html.xslt and attempts to use stylesheet and map
 -->

    <xsl:param name="topobject" />

    <xsl:template match="/">
    <html>
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

    <h3>H3 - $topobject is a map</h3>
    <xsl:value-of select="$topobject"/>

    <h3>H3 - map::get()</h3>
    <xsl:value-of select="map:get($topobject, 'strand')"/>

    <h3>H3 - substring-before()</h3>
    <xsl:value-of select="substring-before($topobject, 'strand')"/>

    <h3>H3 - select="."</h3>
    $> <xsl:value-of select="."/> 

    </body>
    </html>
    </xsl:template>

    <xsl:template match="some">
          $> <xsl:value-of select="."/> 
    </xsl:template>

</xsl:stylesheet>