package com.atomgraph.client.android.model;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.net.URI;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class Document
{

    public static final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final String DCT_NS = "http://purl.org/dc/terms/";

    private final URI uri;
    private final Element description;

    public static final Element lookupDescription(URI uri, org.w3c.dom.Document rdfXml)
    {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        try
        {
            XPathExpression expr = xpath.compile("/rdf:RDF/rdf:Description[@rdf:about = '" + uri.toString() + "']");
            NodeList nodeList = (NodeList)expr.evaluate(rdfXml, XPathConstants.NODESET);
            if (nodeList.getLength() > 0) return (Element)nodeList.item(0);
        }
        catch (XPathExpressionException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Document(URI uri, org.w3c.dom.Document rdfXml)
    {
        this(lookupDescription(uri, rdfXml));
    }

    public Document(Element description)
    {
        this.uri = URI.create(description.getAttributeNS(RDF_NS, "about"));
        this.description = description;
    }

    public URI getURI()
    {
        return uri;
    }

    public Element getDescription()
    {
        return description;
    }

    public String getTitle()
    {
        NodeList titles =  description.getElementsByTagNameNS(DCT_NS, "title");
        if (titles.getLength() > 0) return titles.item(0).getTextContent();

        return null;
    }

}
