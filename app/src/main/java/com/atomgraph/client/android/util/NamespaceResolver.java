package com.atomgraph.client.android.util;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

public class NamespaceResolver implements NamespaceContext
{

    private final org.w3c.dom.Document document;

    public NamespaceResolver(org.w3c.dom.Document document)
    {
        this.document = document;
    }

    @Override
    public String getNamespaceURI(String prefix)
    {
        if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX))
            return document.lookupNamespaceURI(null);
        else
            return document.lookupNamespaceURI(prefix);
    }

    @Override
    public String getPrefix(String namespaceURI)
    {
        return document.lookupPrefix(namespaceURI);
    }

    @Override
    public Iterator getPrefixes(String namespaceURI)
    {
        throw new UnsupportedOperationException();
    }

}
