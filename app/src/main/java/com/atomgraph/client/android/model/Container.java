package com.atomgraph.client.android.model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Container extends Document
{

    private final static String SIOC_NS = "http://rdfs.org/sioc/ns#";

    private final List<Document> children = new ArrayList<>();

    public Container(URI uri, org.w3c.dom.Document rdfXml)
    {
        super(uri, rdfXml);

        NodeList descriptions = rdfXml.getElementsByTagNameNS("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "Description");
        for (int i = 0; i < descriptions.getLength(); i++) {
            Node node = descriptions.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element description = (Element)node;
                if (description.hasAttributeNS(RDF_NS, "about") &&
                        (description.getElementsByTagNameNS(SIOC_NS, "has_container").getLength() > 0 ||
                                description.getElementsByTagNameNS(SIOC_NS, "has_parent").getLength() > 0))
                {
                    Document doc = new Document(description);
                    children.add(doc);
                }
            }
        }
    }

    public List<Document> getChildren()
    {
        return children;
    }

}
